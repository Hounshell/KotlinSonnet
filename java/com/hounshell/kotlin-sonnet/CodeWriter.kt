package com.hounshell.kotlin_sonnet

import java.io.Writer

class CodeWriter(private val writer: Writer) {

    fun write(str: String) = writer.write(str)
}



