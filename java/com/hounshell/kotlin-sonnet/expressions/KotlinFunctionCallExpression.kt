package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

open class KotlinFunctionCallExpression(
    private val target: KotlinExpression? = null,
    private val functionName: String,
    private vararg val params: KotlinFunctionArgument
) : KotlinExpression
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        if (target != null)
        {
            target.writeTo(writer, indent)
            writer.write(".")
        }

        writer.write(functionName)

        if (params.isEmpty()) {
            writer.write("()")
        } else {
            writer.write("(")
            params.first().writeTo(writer, indent)
            for (param in params.drop(1)) {
                writer.write(", ")
                param.writeTo(writer, indent)
            }
            writer.write(")")
        }
    }
}

fun callForValue(functionName: String, vararg params: KotlinFunctionArgument) = KotlinFunctionCallExpression(null, functionName, *params)

fun callForValue(target: KotlinExpression? = null, functionName: String, vararg params: KotlinFunctionArgument) = KotlinFunctionCallExpression(target, functionName, *params)
