package com.hounshell.kotlin_sonnet.types

import java.io.Writer

class KotlinVariableDeclaration(
    val name: String,
    val type: KotlinTypeReference? = null,
    val isFinal: Boolean = true) {

    fun writeTo(writer: Writer, requireType: Boolean = true)
    {
        if (isFinal) {
            writer.write("val ")
        } else {
            writer.write("var ")
        }
        writer.write(name)

        if (requireType && type != null) {
            writer.write(": ${type.asDeclaration()}")
        }
    }
}

fun declare(
    variable: KotlinVariableReference,
    type: KotlinTypeReference? = null,
    isFinal: Boolean = true) = KotlinVariableDeclaration(variable.name, type, isFinal)

fun declare(
    name: String,
    type: KotlinTypeReference? = null,
    isFinal: Boolean = true) = KotlinVariableDeclaration(name, type, isFinal)
