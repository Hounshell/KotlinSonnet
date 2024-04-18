package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunction
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionWithReturn
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinFunctionSignature {
    fun writeTo(writer: Writer, indent: String)

    interface BuilderBase<THIS : BuilderBase<THIS>>
    {
        fun addParameter(variable: VariableDeclaration): THIS
    }

    interface Builder : BuilderBase<Builder>
    {
        fun close(): KotlinFunctionSignature

    }

    class Impl(
        val name: String,
        val returnType: TypeReference? = null,
        val onType: TypeReference? = null
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

        override fun close() = this
    }
}

interface VariableDeclaration {}