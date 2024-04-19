package com.hounshell.kotlin_sonnet.files

import java.util.Collections

class KotlinFiles() {
    private val files: MutableMap<String, KotlinFile> = mutableMapOf()

    fun getFileByPath(path: String) = files.get(path)

    fun getFilesByPath(): Map<String, KotlinFile> = Collections.unmodifiableMap(files)

    fun addKotlinFile(path: String): KotlinFile.Builder<KotlinFiles>
    {
        val file = KotlinFile.Impl(this)
        files[path] = file
        return file
    }
}
