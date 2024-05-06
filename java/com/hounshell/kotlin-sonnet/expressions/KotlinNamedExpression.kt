package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter

open class KotlinNamedArgument(
    private val name: String,
    private val expression: KotlinExpression
) : KotlinFunctionArgument
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write("$name = ")
        expression.writeTo(writer, indent)
    }
}

fun named(name: String, expression: KotlinExpression): KotlinNamedArgument = KotlinNamedArgument(name, expression)
