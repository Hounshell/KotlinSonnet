package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.KotlinFiles
import com.hounshell.kotlin_sonnet.types.RealTypeReference
import com.hounshell.kotlin_sonnet.expressions.literal

import org.junit.Test
import java.io.StringWriter

class ExtensionFunctionTests {
    @Test
    fun `addExtensionFunction basic operation`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt")
            .addExtensionFunction(RealTypeReference(String::class.java), "print")
            .addStatement(literal(false))
            .addStatement(literal("7"))
            .endFunction()
            .addExtensionFunction(RealTypeReference(String::class.java), "printAndReturn", RealTypeReference(String::class.java))
            .addStatement(literal(true))
            .addStatement(literal(7F))
            .endFunction()
            .endFile()
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("")
    }
}
