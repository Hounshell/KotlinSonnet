package com.hounshell.kotlin_sonnet.types

interface KotlinTypeReference {
    val isNullable: Boolean
    fun asDeclaration(): String
    fun asName(): String
}
