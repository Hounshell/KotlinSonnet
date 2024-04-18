package com.hounshell.kotlin_sonnet.kotlin.types

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.types.RealTypeReference
import com.hounshell.kotlin_sonnet.types.TypeReference

import org.junit.Test

class TypeReferenceTests {
    @Test
    fun `TypeReference by name`() {
        assertThat(RealTypeReference("foo", "Bar").asDeclaration()).isEqualTo("foo.Bar")
    }

    @Test
    fun `TypeReference by java class`() {
        assertThat(RealTypeReference(String::class.java).asDeclaration()).isEqualTo("String")
    }
}
