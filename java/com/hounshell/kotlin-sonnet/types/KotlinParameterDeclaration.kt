package com.hounshell.kotlin_sonnet.types

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression

class KotlinParameterDeclaration(
    val name: String,
    val type: KotlinTypeReference,
    val defaultValue: KotlinExpression? = null) {

    fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write(name)
        writer.write(": ${type.asDeclaration()}")
        if (defaultValue != null) {
            writer.write(" = ")
            defaultValue.writeTo(writer, indent)
        }
    }
}

fun parameter(
    variable: KotlinVariableReference,
    type: KotlinTypeReference,
    defaultValue: KotlinExpression? = null) = KotlinParameterDeclaration(variable.name, type, defaultValue)

fun parameter(
    name: String,
    type: KotlinTypeReference,
    defaultValue: KotlinExpression? = null) = KotlinParameterDeclaration(name, type, defaultValue)
