@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration

abstract class KotlinFunction: KotlinBlock()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.Builder<BUILDER>,
            KotlinSignature.Builder
    {
        fun define(work: Builder<BUILDER, PARENT>.() -> Unit): PARENT {
            apply(work)
            return endFunction()
        }

        fun endFunction(): PARENT
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.BuilderAndWriter<BUILDER>,
        Builder<BUILDER, PARENT>

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        private val signature: KotlinSignature.BuilderAndWriter,
        private val body: KotlinBlock.BuilderAndWriter<*>,
        private val parent: PARENT
    ) : BuilderAndWriter<BUILDER, PARENT>
    {
        override fun addParameter(parameter: KotlinParameterDeclaration): BUILDER
        {
            signature.addParameter(parameter)
            return this as BUILDER
        }

        override fun endFunction() = parent

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            signature.writeTo(writer, indent)
            writer.write("${indent}{\n")
            body.writeTo(writer, "$indent  ")
            writer.write("${indent}}\n")
        }    }
}
