package com.hounshell.kotlin_sonnet.files

import com.hounshell.kotlin_sonnet.bases.BaseKotlinBuilder
import java.util.Collections

class KotlinFiles():
    BaseKotlinBuilder<KotlinFiles, Void?>(null, null)
{
    private val files: MutableMap<String, KotlinFile> = mutableMapOf()

    fun getFileByPath(path: String) = files.get(path)

    fun getFilesByPath(): Map<String, KotlinFile> = Collections.unmodifiableMap(files)

    fun addKotlinFile(path: String): KotlinFile.Builder<KotlinFiles>
    {
        return KotlinFile.Impl(this) { file -> files[path] = file }
    }
}
