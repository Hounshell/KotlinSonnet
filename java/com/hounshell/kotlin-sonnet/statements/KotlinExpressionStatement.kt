package com.hounshell.kotlin_sonnet.statements

import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import java.io.Writer

class KotlinExpressionStatement(val expression: KotlinExpression) : KotlinStatement
{
    override fun writeTo(writer: Writer, indent: String)
    {
        writer.write(indent)
        expression.writeTo(writer, "$indent    ")
        writer.write("\n")
    }
}