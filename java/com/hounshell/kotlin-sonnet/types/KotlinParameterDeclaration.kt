package com.hounshell.kotlin_sonnet.types

import com.hounshell.kotlin_sonnet.CodeWriter
import java.io.Writer

class KotlinParameterDeclaration(
    val name: String,
    val type: KotlinTypeReference? = null) {

    fun writeTo(writer: Writer, requireType: Boolean = true)
    {
        writer.write(name)

        if (requireType && type != null) {
            writer.write(": ${type.asDeclaration()}")
        }
    }
}

fun parameter(
    variable: KotlinVariableReference,
    type: KotlinTypeReference? = null) = KotlinParameterDeclaration(variable.name, type)

fun parameter(
    name: String,
    type: KotlinTypeReference? = null) = KotlinParameterDeclaration(name, type)
