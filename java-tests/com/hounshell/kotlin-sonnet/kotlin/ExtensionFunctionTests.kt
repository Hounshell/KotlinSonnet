package com.hounshell.kotlin_sonnet.kotlin

import com.google.common.truth.Truth.assertThat
import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.add
import com.hounshell.kotlin_sonnet.expressions.and
import com.hounshell.kotlin_sonnet.expressions.callForValue
import com.hounshell.kotlin_sonnet.expressions.literal
import com.hounshell.kotlin_sonnet.expressions.multiply
import com.hounshell.kotlin_sonnet.expressions.named
import com.hounshell.kotlin_sonnet.expressions.negative
import com.hounshell.kotlin_sonnet.expressions.new
import com.hounshell.kotlin_sonnet.expressions.not
import com.hounshell.kotlin_sonnet.expressions.or
import com.hounshell.kotlin_sonnet.expressions.spread
import com.hounshell.kotlin_sonnet.files.KotlinFiles
import com.hounshell.kotlin_sonnet.types.nullableType
import com.hounshell.kotlin_sonnet.types.parameter
import com.hounshell.kotlin_sonnet.types.type
import com.hounshell.kotlin_sonnet.types.variable
import org.junit.Test
import java.io.StringWriter

class ExtensionFunctionTests {
    @Test
    fun `addExtensionFunction basic operation`() {
        val parent = KotlinFiles()
        val file = parent
            .addKotlinFile("foo.kt").define {
                addFinalClass("Test")
                    .addFunction("doSomething")
                    .endFunction()
                    .endClass()

                addFunction(type(String::class.java), "takeUntil", tailRecursion = true).define {
                    val paramSeparator = variable("separator")
                    val paramMaxLength = variable("maxLength")

                    addParameter(parameter(paramMaxLength, nullableType(Int::class.java), literal(7)))
                    addParameter(parameter(paramSeparator, type(String::class.java)))

                    call(paramSeparator, "clear")
                    call( "doNothing",
                        literal(1),
                        callForValue( "doNothing", literal(2)))

                    _if_(literal(true)).define {
                        expression(literal(1))
                    }.elseIf(not(literal(false))) {
                        expression(literal(2))
                    }.elseIf(not(not(literal(7)))) {
                        expression(literal(3))
                        expression(literal(4))
                        _return_()
                        expression(literal(5))
                    }._else_ {
                        expression(literal(42))
                    }
                        .expression((literal(4)))

                    expression(negative(negative(literal(7))))

                    expression(
                        multiply(
                            literal(1),
                            add(
                                literal(2),
                                literal(3),
                                literal(4),
                                literal(5)
                            ),
                            literal(6)
                        )
                    )
                }

                addFunction("printAndReturn", type(String::class.java))
                    .expression(literal(true))
                    .expression(literal(7F))
                    .ifBlock(
                        or(
                            literal(false),
                            and(
                                literal(false),
                                not(literal(true)),
                                literal(true)
                            )
                        )
                    ).define {
                        expression(literal(7))
                        doReturn(new(
                            type(String::class.java),
                            add(literal(1), literal(7)),
                            spread(literal(18)),
                            named("maxLength", literal(42))))
                    }.endIf()

                    .doReturn(literal("42"))
            }
            .getFileByPath("foo.kt")!!

        val writer = StringWriter();
        file.writeTo(CodeWriter(writer))

        assertThat(writer.toString()).isEqualTo("")
    }
}
