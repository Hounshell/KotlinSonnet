package com.hounshell.kotlin_sonnet.statements

import com.hounshell.kotlin_sonnet.CodeWriter
import java.io.Writer

class KotlinReturnStatement() : KotlinStatement
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write("${indent}return\n")
    }
}
