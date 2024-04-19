package com.hounshell.kotlin_sonnet.statements

import java.io.Writer

class KotlinReturnStatement() : KotlinStatement
{
    override fun writeTo(writer: Writer, indent: String)
    {
        writer.write("${indent}return\n")
    }
}

fun doReturn() = KotlinReturnStatement()