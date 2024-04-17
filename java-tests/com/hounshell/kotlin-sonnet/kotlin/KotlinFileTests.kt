package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.KotlinFiles
import com.hounshell.kotlin_sonnet.TypeReference

import org.junit.Test
import java.io.StringWriter

class KotlinFileTests {
    @Test
    fun `endFile returns parent object`() {
        val parent = KotlinFiles()
        assertThat(parent
            .addKotlinFile("foo.kt")
            .endFile() as Any).isSameInstanceAs(parent)
    }

    @Test
    fun `addImport`() {
        val parent = KotlinFiles()
        parent
            .addKotlinFile("foo.kt")
            .addImport(TypeReference(String::class.java))
            .endFile()
    }

    @Test
    fun `generated code with package`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt")
            .packageName("foo.bar")
            .endFile()
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("package foo.bar\n\n")
    }

    @Test
    fun `generated code with no package`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt")
            .endFile()
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("")
    }
}
