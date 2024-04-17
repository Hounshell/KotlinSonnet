package com.hounshell.kotlin_sonnet

import java.util.Collections

class KotlinFiles():
    BaseKotlinBlock<KotlinFiles, Void?, Pair<String, KotlinFile>>(null, null),
    KotlinFile.Parent
{
    fun getFileByPath(path: String) = getMembers()
        .filter { (filePath, _) -> path.equals(filePath) }
        .map { (_, file ) -> file }
        .firstOrNull()

    fun getFilesByPath(): Map<String, KotlinFile> = getMembers().associate({ it })

    fun getFiles(): List<Pair<String, KotlinFile>> = Collections.unmodifiableList(getMembers())

    fun addKotlinFile(path: String): KotlinFile.Builder<KotlinFiles> {
        return KotlinFile.Impl(this) { file -> addMember(path to file) }
    }
}
