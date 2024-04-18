@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinFunction : CodeWriter
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT>
    {
        val name: String

        fun addParameter(variable: VariableDeclaration): THIS

        fun startFunctionBody(): KotlinBlock.Builder<PARENT>

        fun skip(): PARENT
    }

    interface Builder<P> : KotlinFunction.BuilderBase<Builder<P>, P>
    {}

    class Impl<PARENT>(
        name: String,
        parent: PARENT,
        callback: (KotlinFunction) -> Unit
    ) : ImplBase<Impl<PARENT>, PARENT, KotlinFunction>(name, parent, callback)
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinFunction>(
        override val name: String,
        parent: PARENT,
        callback: (CALLBACK) -> Unit
    ) :
        BaseKotlinBlock<CALLBACK, PARENT>(parent, callback),
        BuilderBase<THIS, PARENT>,
            KotlinFunction
    {
        private val parameters: MutableList<VariableDeclaration> = mutableListOf()

        private lateinit var body: KotlinBlock

        override fun addParameter(variable: VariableDeclaration): THIS
        {
            assertNotClosed()
            parameters.add(variable)
            return this as THIS
        }

        override fun startFunctionBody(): KotlinBlock.Builder<PARENT>
        {
            TODO("Not yet implemented")
        }

        override fun skip(): PARENT
        {
            // TODO: Remove this.
            return close();
        }

        override fun writeTo(writer: Writer, indent: String)
        {
            writer.write("${indent}fun $name")
            writeFunctionArguments(writer, "$indent    ")
            writer.write("${indent}{\n")
            writeFunctionBody(writer, "$indent  ")
            writer.write("${indent}}\n")
        }

        protected fun writeFunctionArguments(writer: Writer, indent: String) {
            writer.write("(/* TODO: KotlinFunction arguments not implemented yet. */)")
        }

        protected fun writeFunctionBody(writer: Writer, indent: String) {
            writer.write("${indent}// TODO: KotlinFunction body not implemented yet.\n")
        }
    }
}

interface VariableDeclaration {}