package com.hounshell.kotlin_sonnet.statements

import com.hounshell.kotlin_sonnet.CodeWriter

interface KotlinStatement
{
    fun writeTo(writer: CodeWriter, indent: String)
}