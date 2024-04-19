package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinFunctionSignature {
    fun writeTo(writer: CodeWriter, indent: String)

    interface BuilderBase<THIS : BuilderBase<THIS>>
    {
        fun addParameter(parameter: KotlinParameterDeclaration): THIS
    }

    interface Builder : BuilderBase<Builder>
    {}

    companion object
    {
        @JvmStatic
        fun impl(
            name: String,
            returnType: KotlinTypeReference? = null,
            onType: KotlinTypeReference? = null
        ): ResultAndBuilder<KotlinFunctionSignature, Builder> =
            ResultAndBuilder(Impl(name, returnType, onType))

        private class Impl(
            val name: String,
            val returnType: KotlinTypeReference?,
            val onType: KotlinTypeReference?
        ) :
            Builder,
            KotlinFunctionSignature
        {
            private val parameters: MutableList<KotlinParameterDeclaration> = mutableListOf()

            override fun addParameter(parameter: KotlinParameterDeclaration): Builder
            {
                parameters.add(parameter)
                return this
            }

            override fun writeTo(writer: CodeWriter, indent: String)
            {
                if (onType != null)
                {
                    writer.write("${indent}fun ${onType.asDeclaration()}.$name")
                }
                else
                {
                    writer.write("${indent}fun $name")
                }

                if (parameters.isEmpty())
                {
                    writer.write("()")
                }
                else
                {
                    writer.write("(\n$indent    ")
                    parameters.first().writeTo(writer, true)

                    for (parameter in parameters.drop(1))
                    {
                        writer.write(",\n$indent    ")
                        parameter.writeTo(writer, true)
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
