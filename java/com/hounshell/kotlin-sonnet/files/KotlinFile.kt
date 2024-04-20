package com.hounshell.kotlin_sonnet.files

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunction
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForUnit
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForValue
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

abstract class KotlinFile
{
    interface Writer
    {
        fun writeTo(writer: CodeWriter)
    }

    interface Builder<PARENT>
    {
        fun packageName(packageName: String?): Builder<PARENT>

        fun addImport(type: KotlinTypeReference): Builder<PARENT>

        fun addExtensionFunction(onType: KotlinTypeReference, name: String): KotlinExtensionFunctionForUnit.Builder<Builder<PARENT>>
        fun addExtensionFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference): KotlinExtensionFunctionForValue.Builder<Builder<PARENT>>

        // TODO: Support more contents of a file.

        fun define(work: Builder<PARENT>.() -> Unit): PARENT {
            apply(work)
            return endFile()
        }

        fun endFile(): PARENT
    }

    interface BuilderAndWriter<PARENT>:
        Builder<PARENT>,
        Writer

    companion object {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<PARENT> = Impl(parent)

        private class Impl<PARENT>(private val parent: PARENT) : BuilderAndWriter<PARENT> {
            private var packageName: String? = null
            private val classImports: MutableList<KotlinClassImport> = mutableListOf()
            private val extensionFunctions: MutableList<KotlinExtensionFunction.BuilderAndWriter<*, *>> = mutableListOf()

            override fun packageName(packageName: String?): Builder<PARENT>
            {
                this.packageName = packageName
                return this
            }

            override fun addImport(type: KotlinTypeReference): Builder<PARENT>
            {
                classImports.add(KotlinClassImport(type))
                return this
            }

            override fun addExtensionFunction(onType: KotlinTypeReference, name: String): KotlinExtensionFunctionForUnit.Builder<Builder<PARENT>> {
                val function = KotlinExtensionFunctionForUnit.impl(onType, name, this as Builder<PARENT>)
                extensionFunctions.add(function)
                return function
            }

            override fun addExtensionFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference): KotlinExtensionFunctionForValue.Builder<Builder<PARENT>> {
                val function = KotlinExtensionFunctionForValue.impl(onType, name, returnType, this as Builder<PARENT>)
                extensionFunctions.add(function)
                return function
            }

            // TODO: Classes, Interfaces, Enums, Static imports, ...?

            override fun endFile() = parent

            override fun writeTo(writer: CodeWriter) {
                if (packageName != null) {
                    writer.write("package $packageName\n")
                }

                if (classImports.isNotEmpty())
                {
                    writer.write("\n")
                    for (classImport in classImports)
                    {
                        classImport.writeTo(writer)
                    }
                }

                for (extensionFunction in extensionFunctions) {
                    writer.write("\n")
                    extensionFunction.writeTo(writer, "")
                }

                // TODO: Support more contents of a file.
            }
        }

        private class KotlinClassImport(val type: KotlinTypeReference)
        {
            fun writeTo(writer: CodeWriter)
            {
                writer.write("import ${type.asName()}\n")
            }
        }
    }
}