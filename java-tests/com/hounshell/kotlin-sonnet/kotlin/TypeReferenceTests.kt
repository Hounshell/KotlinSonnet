package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.TypeReference

import org.junit.Test

class TypeReferenceTests {
    @Test
    fun `TypeReference by name`() {
        assertThat(TypeReference("foo.Bar").fullyQualifiedName).isEqualTo("foo.Bar")
    }

    @Test
    fun `TypeReference by java class`() {
        assertThat(TypeReference(String::class.java).fullyQualifiedName).isEqualTo(String::class.java.canonicalName)
    }
}
