package com.hounshell.kotlin_sonnet.java.types;

import static com.google.common.truth.Truth.assertThat;

import com.hounshell.kotlin_sonnet.types.KotlinRealTypeReference;

import org.junit.Test;

public class TypeReferenceTests {
    @Test
    public void typeReferenceByName() {
        assertThat(new KotlinRealTypeReference("foo.Bar").asDeclaration()).isEqualTo("foo.Bar");
    }

    @Test
    public void TypeReferenceByJavaClass() {
        assertThat(new KotlinRealTypeReference( String.class).asDeclaration()).isEqualTo( "String");
    }
}
