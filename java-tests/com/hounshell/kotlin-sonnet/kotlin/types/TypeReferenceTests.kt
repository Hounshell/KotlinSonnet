package com.hounshell.kotlin_sonnet.kotlin.types

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.types.type

import org.junit.Test

class TypeReferenceTests {
    @Test
    fun `TypeReference by name`() {
        assertThat(type("foo.Bar").asDeclaration()).isEqualTo("foo.Bar")
    }

    @Test
    fun `TypeReference by java class`() {
        assertThat(type(String::class.java).asDeclaration()).isEqualTo("String")
    }
}
