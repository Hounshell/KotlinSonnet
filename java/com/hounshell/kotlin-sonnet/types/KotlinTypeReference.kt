package com.hounshell.kotlin_sonnet.types

interface KotlinTypeReference {
    fun asDeclaration(): String
    fun asName(): String
}
