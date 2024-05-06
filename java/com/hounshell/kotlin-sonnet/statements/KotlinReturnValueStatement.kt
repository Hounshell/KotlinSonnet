package com.hounshell.kotlin_sonnet.statements

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import java.io.Writer

class KotlinReturnValueStatement(val expression: KotlinExpression) : KotlinStatement
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write("${indent}return ")
        expression.writeTo(writer, indent)
        writer.write("\n")
    }
}
