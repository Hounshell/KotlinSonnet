package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.statements.KotlinExpressionStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

interface KotlinExpression
{
    fun writeTo(writer: CodeWriter, indent: String)
    fun asStatement(): KotlinStatement = KotlinExpressionStatement(this)
    fun shouldWrapInParentheses(): Boolean = false
}