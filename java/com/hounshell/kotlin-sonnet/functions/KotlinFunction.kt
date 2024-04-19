@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration

interface KotlinFunction
{
    fun writeTo(writer: CodeWriter, indent: String)

    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT>:
        KotlinSignature.Builder,
        KotlinBlock.BuilderBase<THIS>
    {

        fun define(work: BuilderBase<THIS, PARENT>.() -> Unit): PARENT {
            apply(work)
            return endFunction()
        }

        fun endFunction(): PARENT
    }

    interface Builder<P> : BuilderBase<Builder<P>, P>
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT>(
        private val signature: KotlinSignature.BuilderAndWriter,
        private val body: ResultAndBuilder<out KotlinBlock, out KotlinBlock.BuilderBase<*>>,
        private val parent: PARENT
    ) :
        BuilderBase<THIS, PARENT>,
        KotlinFunction
    {
        override fun addParameter(parameter: KotlinParameterDeclaration): THIS
        {
            signature.addParameter(parameter)
            return this as THIS
        }

        override fun endFunction() = parent

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            signature.writeTo(writer, indent)
            writer.write("${indent}{\n")
            body.result.writeTo(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
