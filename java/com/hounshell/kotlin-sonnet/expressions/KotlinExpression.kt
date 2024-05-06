package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.statements.KotlinExpressionStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

interface KotlinExpression : KotlinFunctionArgument
{
    companion object
    {
        val _this_: KotlinExpression = KotlinSimpleExpression("this")
        val _super_: KotlinExpression = KotlinSimpleExpression("super")
    }

    fun asStatement(): KotlinStatement = KotlinExpressionStatement(this)
    fun shouldWrapInParentheses(): Boolean = false
}

interface KotlinFunctionArgument {
    fun writeTo(writer: CodeWriter, indent: String)
}

open class KotlinSimpleExpression(val representation: String): KotlinExpression {
    override fun writeTo(writer: CodeWriter, indent: String) = writer.write(representation)
}

val _this_ = KotlinExpression.Companion._this_
val _super_ = KotlinExpression.Companion._super_
