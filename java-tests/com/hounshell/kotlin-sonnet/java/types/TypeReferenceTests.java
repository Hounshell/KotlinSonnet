package com.hounshell.kotlin_sonnet.java.types;

import static com.google.common.truth.Truth.assertThat;

import com.hounshell.kotlin_sonnet.types.RealTypeReference;
import com.hounshell.kotlin_sonnet.types.TypeReference;

import org.junit.Test;

public class TypeReferenceTests {
    @Test
    public void typeReferenceByName() {
        assertThat(new RealTypeReference("foo", "Bar").asDeclaration()).isEqualTo("foo.Bar");
    }

    @Test
    public void TypeReferenceByJavaClass() {
        assertThat(new RealTypeReference( String.class).asDeclaration()).isEqualTo( "String");
    }
}
