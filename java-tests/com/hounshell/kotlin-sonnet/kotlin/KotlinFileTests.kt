package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.KotlinFiles
import com.hounshell.kotlin_sonnet.types.type

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
            .addImport(type(String::class.java))
            .endFile()
    }

    @Test
    fun `generated code with package and import`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt")
            .packageName("foo.bar")
            .addImport(type(ArrayList::class.java))
            .addImport(type(HashMap::class.java))
            .endFile()
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("package foo.bar\n\nimport java.util.ArrayList\nimport java.util.HashMap\n")
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
