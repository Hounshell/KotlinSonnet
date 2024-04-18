package com.hounshell.kotlin_sonnet

import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import java.util.Collections

class KotlinFiles():
    BaseKotlinBlock<KotlinFiles, Void?>(null, null)
{
    private val files: MutableMap<String, KotlinFile> = mutableMapOf()

    fun getFileByPath(path: String) = files.get(path)

    fun getFilesByPath(): Map<String, KotlinFile> = Collections.unmodifiableMap(files)

    fun addKotlinFile(path: String): KotlinFile.Builder<KotlinFiles> {
        return KotlinFile.Impl(this) { file -> files[path] = file }
    }
}
