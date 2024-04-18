package com.hounshell.kotlin_sonnet

import com.hounshell.kotlin_sonnet.bases.BaseKotlinBuilder
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunction
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionWithReturn
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference
import java.io.Writer

interface KotlinFile {
    fun writeTo(writer: Writer)

    interface Builder<P>
    {
        var packageName: String?

        fun packageName(packageName: String?): Builder<P>

        fun addImport(type: KotlinTypeReference): Builder<P>

        fun addExtensionFunction(onType: KotlinTypeReference, name: String): KotlinExtensionFunction.Builder<Builder<P>>
        fun addExtensionFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference): KotlinExtensionFunctionWithReturn.Builder<Builder<P>>

        // TODO: Support more contents of a file.

        fun define(work: Builder<P>.() -> Unit): P {
            apply(work)
            return endFile()
        }

        fun endFile(): P
    }

    class Impl<P>(parent: P, callback: (KotlinFile) -> Unit):
        BaseKotlinBuilder<KotlinFile, P>(parent, callback),
        Builder<P>,
        KotlinFile
    {
        private val classImports: MutableList<KotlinClassImport> = mutableListOf()
        private val extensionFunctions: MutableList<KotlinExtensionFunction> = mutableListOf()

        override var packageName: String? = null
            get() = field
            set(value) {
                assertNotClosed()
                field = value
            }

        override fun packageName(packageName: String?): Builder<P> {
            this.packageName = packageName
            return this
        }

        override fun addImport(type: KotlinTypeReference): Builder<P> {
            classImports.add(KotlinClassImport(type))
            return this
        }

        override fun addExtensionFunction(onType: KotlinTypeReference, name: String): KotlinExtensionFunction.Builder<Builder<P>> {
            return KotlinExtensionFunction.impl(onType, name, this) { f -> extensionFunctions.add(f) }
        }

        override fun addExtensionFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference): KotlinExtensionFunctionWithReturn.Builder<Builder<P>> {
            return KotlinExtensionFunctionWithReturn.impl(onType, name, returnType, this) { f -> extensionFunctions.add(f) }
        }

        // TODO: Classes, Interfaces, Enums, Static imports, ...?

        override fun endFile(): P = close()

        override fun writeTo(writer: Writer) {
            assertClosed()
            if (packageName != null) {
                writer.write("package $packageName\n")
            }

            writeSection(writer, classImports, false)
            writeSection(writer, extensionFunctions)

            // TODO: Support more contents of a file.
        }

        private fun writeSection(writer: Writer, members: List<out CodeWriter>, spaceBetween: Boolean = true) {
            var first = true;
            for (member in members) {
                if (first || spaceBetween) {
                    first = false
                    writer.write("\n")
                }

                member.writeTo(writer, "");
            }
        }

        private class KotlinClassImport(val type: KotlinTypeReference): CodeWriter {
            override fun writeTo(writer: Writer, indent: String)
            {
                writer.write("${indent}import ${type.asName()}\n")
            }
        }
    }
}
