package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

open class KotlinConstructorExpression(
    private val type: KotlinTypeReference,
    private vararg val params: KotlinFunctionArgument
) : KotlinExpression
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write("${type.asName()}(")
        var first = true;
        for (param in params) {
            if (first)
            {
                first = false;
            } else {
                writer.write(", ")
            }
            param.writeTo(writer, "    $indent")
        }
        writer.write(")")
    }
}

fun constructor(type: KotlinTypeReference, vararg params: KotlinFunctionArgument) = KotlinConstructorExpression(type, *params)

fun new(type: KotlinTypeReference, vararg params: KotlinFunctionArgument) = KotlinConstructorExpression(type, *params)
