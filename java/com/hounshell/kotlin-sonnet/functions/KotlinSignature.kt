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

    interface Builder
    {
        fun addParameter(parameter: KotlinParameterDeclaration): Builder
    }

    interface BuilderAndWriter:
        Builder,
        Writer

    companion object {
        fun impl(
            name: String,
            returnType: KotlinTypeReference? = null,
            onType: KotlinTypeReference? = null,
            tailRecursion: Boolean = false
        ): BuilderAndWriter = Impl(name, returnType, onType, tailRecursion)

        private class Impl(
            val name: String,
            val returnType: KotlinTypeReference?,
            val onType: KotlinTypeReference?,
            val tailRecursion: Boolean
        ) : BuilderAndWriter {
            private val parameters: MutableList<KotlinParameterDeclaration> = mutableListOf()

            override fun addParameter(parameter: KotlinParameterDeclaration): Builder
            {
                parameters.add(parameter)
                return this
            }

            override fun writeTo(writer: CodeWriter, indent: String)
            {
                writer.write(indent)

                if (tailRecursion) {
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
