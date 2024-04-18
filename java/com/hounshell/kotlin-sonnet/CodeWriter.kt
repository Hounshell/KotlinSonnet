package com.hounshell.kotlin_sonnet

import java.io.Writer

interface CodeWriter {
    fun writeTo(writer: Writer, indent: String)
}



