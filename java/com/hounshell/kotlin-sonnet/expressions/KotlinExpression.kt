package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.statements.KotlinExpressionStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

interface KotlinExpression
{
    companion object
    {
        val _this_: KotlinExpression = KotlinSimpleExpression("this")
        val _super_: KotlinExpression = KotlinSimpleExpression("super")
    }

    fun writeTo(writer: CodeWriter, indent: String)
    fun asStatement(): KotlinStatement = KotlinExpressionStatement(this)
    fun shouldWrapInParentheses(): Boolean = false
}

open class KotlinSimpleExpression(val representation: String): KotlinExpression {
    override fun writeTo(writer: CodeWriter, indent: String) = writer.write(representation)
}

val _this_ = KotlinExpression.Companion._this_
val _super_ = KotlinExpression.Companion._super_
