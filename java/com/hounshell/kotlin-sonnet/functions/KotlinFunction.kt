@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinFunction : CodeWriter
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT>: KotlinFunctionSignature.BuilderBase<THIS>
    {
        fun startFunctionBody(): KotlinBlock.Builder<PARENT>

        fun skip(): PARENT
    }

    interface Builder<P> : KotlinFunction.BuilderBase<Builder<P>, P>
    {}

    class Impl<PARENT>(
        name: String,
        parent: PARENT,
        callback: (KotlinFunction) -> Unit
    ) : ImplBase<Impl<PARENT>, PARENT, KotlinFunction>(KotlinFunctionSignature.Impl(name), parent, callback)
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinFunction>(
        private val signature: KotlinFunctionSignature.Builder,
        parent: PARENT,
        callback: (CALLBACK) -> Unit
    ) :
        BaseKotlinBlock<CALLBACK, PARENT>(parent, callback),
        BuilderBase<THIS, PARENT>,
            KotlinFunction
    {
        private lateinit var body: KotlinBlock

        override fun addParameter(variable: VariableDeclaration): THIS
        {
            assertNotClosed()
            signature.addParameter(variable)
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
            signature.close().writeTo(writer, indent)
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
