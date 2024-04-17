package com.hounshell.kotlin_sonnet.java;

import static com.google.common.truth.Truth.assertThat;

import com.hounshell.kotlin_sonnet.TypeReference;

import org.junit.Test;

public class TypeReferenceTests {
    @Test
    public void typeReferenceByName() {
        assertThat(new TypeReference("foo.Bar").getFullyQualifiedName()).isEqualTo("foo.Bar");
    }

    @Test
    public void TypeReferenceByJavaClass() {
        assertThat(new TypeReference(String.class).getFullyQualifiedName()).isEqualTo(String.class.getCanonicalName());
    }
}
