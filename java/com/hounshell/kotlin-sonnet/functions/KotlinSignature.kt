package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

sealed class KotlinSignature
{
    interface Writer
    {
        fun writeTo(writer: CodeWriter, indent: String)
    }

    interface Builder<PARENT>
    {
        fun addParameter(parameter: KotlinParameterDeclaration): Builder<PARENT>
        fun endFunctionSignature(): PARENT
    }

    interface BuilderAndWriter<PARENT> :
        Builder<PARENT>,
        Writer

    companion object
    {
        fun <PARENT> impl(
            name: String,
            returnType: KotlinTypeReference? = null,
            onType: KotlinTypeReference? = null,
            isOpen: Boolean = false,
            isOverride: Boolean = false,
            tailRecursion: Boolean = false,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(name, returnType, onType, isOpen, isOverride, tailRecursion, parent)

        private class Impl<PARENT>(
            val name: String,
            val returnType: KotlinTypeReference?,
            val onType: KotlinTypeReference?,
            val isOpen: Boolean = false,
            val isOverride: Boolean = false,
            val tailRecursion: Boolean,
            val parent: PARENT
        ) : BuilderAndWriter<PARENT>
        {
            private val parameters: MutableList<KotlinParameterDeclaration> = mutableListOf()

            override fun addParameter(parameter: KotlinParameterDeclaration): Builder<PARENT>
            {
                parameters.add(parameter)
                return this
            }

            override fun endFunctionSignature(): PARENT
            {
                return parent
            }

            override fun writeTo(writer: CodeWriter, indent: String)
            {
                writer.write(indent)

                if (isOpen && isOverride)
                {
                    writer.write("override ")
                }
                else if (isOverride)
                {
                    writer.write("final override ")
                }
                else if (isOpen)
                {
                    writer.write("open ")
                }

                if (tailRecursion)
                {
                    writer.write("tailrec ")
                }

                writer.write("fun ")

                if (onType != null)
                {
                    writer.write("${onType.asDeclaration()}.")
                }

                writer.write(name)

                if (parameters.isEmpty())
                {
                    writer.write("()")
                }
                else
                {
                    writer.write("(\n    $indent")
                    parameters.first().writeTo(writer, "    $indent")

                    for (parameter in parameters.drop(1))
                    {
                        writer.write(",\n    $indent")
                        parameter.writeTo(writer, "    $indent")
                    }
                    writer.write("\n$indent)")
                }

                if (returnType != null)
                {
                    writer.write(": ${returnType.asDeclaration()}")
                }
                writer.write(" ")
            }
        }
    }
}
