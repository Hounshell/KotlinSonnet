package com.hounshell.kotlin_sonnet.files

import java.util.Collections

class KotlinFiles() {
    private val files: MutableMap<String, KotlinFile.Writer> = mutableMapOf()

    fun getFileByPath(path: String) = files.get(path)

    fun getFilesByPath(): Map<String, KotlinFile.Writer> = Collections.unmodifiableMap(files)

    fun addKotlinFile(path: String): KotlinFile.Builder<KotlinFiles>
    {
        val file = KotlinFile.impl(this)
        files[path] = file
        return file
    }
}
