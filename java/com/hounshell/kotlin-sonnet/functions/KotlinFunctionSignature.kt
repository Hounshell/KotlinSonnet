package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference
import java.io.Writer

interface KotlinFunctionSignature {
    fun writeTo(writer: Writer, indent: String)

    interface BuilderBase<THIS : BuilderBase<THIS>>
    {
        fun addParameter(variable: VariableDeclaration): THIS
    }

    interface Builder : BuilderBase<Builder>, CodeWriter
    {}

    class Impl(
        val name: String,
        val returnType: KotlinTypeReference? = null,
        val onType: KotlinTypeReference? = null
    ):
        Builder,
        KotlinFunctionSignature
    {
        private val parameters: MutableList<VariableDeclaration> = mutableListOf()

        override fun addParameter(variable: VariableDeclaration): Builder
        {
            parameters.add(variable)
            return this
        }

        override fun writeTo(writer: Writer, indent: String)
        {
            if (onType != null) {
                writer.write("${indent}fun ${onType.asDeclaration()}.$name")
            } else {
                writer.write("${indent}fun $name")
            }

            writer.write("(/* TODO: KotlinFunctionSignature arguments not implemented yet. */)")

            if (returnType != null) {
                writer.write(": ${returnType.asDeclaration()}")
            }
        }
    }
}

interface VariableDeclaration {}