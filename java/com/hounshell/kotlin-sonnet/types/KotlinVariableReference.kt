package com.hounshell.kotlin_sonnet.types

class KotlinVariableReference(val name: String) {
}

fun variable(name: String) = KotlinVariableReference(name)
