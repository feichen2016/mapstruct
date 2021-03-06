/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisfaov
 *
 */
@WithClasses({ NoSetterMapper.class, NoSetterSource.class, NoSetterTarget.class })
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class NoSetterStreamMappingTest {

    @Test
    public void compilesAndMapsCorrectly() {
        NoSetterSource source = new NoSetterSource();
        source.setListValues( Arrays.asList( "foo", "bar" ).stream() );

        NoSetterTarget target = NoSetterMapper.INSTANCE.toTarget( source );

        assertThat( target.getListValues() ).containsExactly( "foo", "bar" );

        // now test existing instances

        NoSetterSource source2 = new NoSetterSource();
        source2.setListValues( Arrays.asList( "baz" ).stream() );
        List<String> originalCollectionInstance = target.getListValues();

        NoSetterTarget target2 = NoSetterMapper.INSTANCE.toTargetWithExistingTarget( source2, target );

        assertThat( target2.getListValues() ).isSameAs( originalCollectionInstance );
        assertThat( target2.getListValues() ).containsExactly( "baz" );
    }
}
