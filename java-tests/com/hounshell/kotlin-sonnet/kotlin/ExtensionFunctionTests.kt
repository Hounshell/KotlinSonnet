package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.files.KotlinFiles
import com.hounshell.kotlin_sonnet.types.type
import com.hounshell.kotlin_sonnet.expressions.literal
import com.hounshell.kotlin_sonnet.types.nullableType
import com.hounshell.kotlin_sonnet.types.parameter
import com.hounshell.kotlin_sonnet.types.variable

import org.junit.Test
import java.io.StringWriter

class ExtensionFunctionTests {
    @Test
    fun `addExtensionFunction basic operation`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt").define {
                addExtensionFunction(type(String::class.java), "takeUntil").define {
                    val paramSeparator = variable("separator")
                    val paramMaxLength = variable("maxLength")

                    addParameter(parameter(paramSeparator, type(String::class.java)))
                    addParameter(parameter(paramMaxLength, nullableType(Int::class.java)))

                    addStatement(literal(false))
                    addStatement(literal("7"))
                }

                addExtensionFunction(type(String::class.java), "printAndReturn", type(String::class.java))
                    .addStatement(literal(true))
                    .addStatement(literal(7F))
                    .endFunction()
            }
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(writer)

        assertThat(writer.toString()).isEqualTo("")
    }
}
