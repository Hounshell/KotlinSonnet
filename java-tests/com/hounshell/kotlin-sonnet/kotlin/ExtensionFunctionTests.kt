package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.KotlinFiles
import com.hounshell.kotlin_sonnet.types.type
import com.hounshell.kotlin_sonnet.expressions.literal

import org.junit.Test
import java.io.StringWriter

class ExtensionFunctionTests {
    @Test
    fun `addExtensionFunction basic operation`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt").define {
                addExtensionFunction(type(String::class.java), "print").define {
                    addStatement(literal(false))
                    addStatement(literal("7"))
                }
                .addExtensionFunction(type(String::class.java), "printAndReturn", type(String::class.java)).define {
                    addStatement(literal(true))
                    addStatement(literal(7F))
                }
            }
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("")
    }
}
