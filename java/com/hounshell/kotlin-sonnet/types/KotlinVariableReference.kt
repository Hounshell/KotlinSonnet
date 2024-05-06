package com.hounshell.kotlin_sonnet.types

import com.hounshell.kotlin_sonnet.expressions.KotlinSimpleExpression

class KotlinVariableReference(val name: String) : KotlinSimpleExpression(name) {
}

fun variable(name: String) = KotlinVariableReference(name)
