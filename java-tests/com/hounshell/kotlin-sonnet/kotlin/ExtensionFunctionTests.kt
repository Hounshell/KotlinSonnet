package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.files.KotlinFiles
import com.hounshell.kotlin_sonnet.types.type
import com.hounshell.kotlin_sonnet.expressions.add
import com.hounshell.kotlin_sonnet.expressions.and
import com.hounshell.kotlin_sonnet.expressions.multiply
import com.hounshell.kotlin_sonnet.expressions.literal
import com.hounshell.kotlin_sonnet.expressions.negative
import com.hounshell.kotlin_sonnet.expressions.not
import com.hounshell.kotlin_sonnet.expressions.or
import com.hounshell.kotlin_sonnet.statements.doReturn
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

                    expression(negative(negative(literal(7))))
                    expression(or(
                        literal(false),
                        and(
                            literal(false),
                            not(literal(true)),
                            literal(true))))

                    doReturn(multiply(
                        literal(1),
                        add(
                            literal(2),
                            literal(3),
                            literal(4),
                            literal(5)),
                        literal(6)))
                }

                addExtensionFunction(type(String::class.java), "printAndReturn", type(String::class.java))
                    .expression(literal(true))
                    .expression(literal(7F))
                    .doReturn(literal("42"))
            }
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(CodeWriter(writer))

        assertThat(writer.toString()).isEqualTo("")
    }
}
