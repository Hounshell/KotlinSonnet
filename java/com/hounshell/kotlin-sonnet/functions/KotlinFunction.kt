@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinFunction : CodeWriter
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT>:
        KotlinFunctionSignature.BuilderBase<THIS>,
            KotlinBlock.BuilderBase<THIS>
    {
        fun endFunction(): PARENT
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
        private val body = KotlinBlock.impl()

        override fun addParameter(variable: VariableDeclaration): THIS
        {
            assertNotClosed()
            signature.addParameter(variable)
            return this as THIS
        }

        override fun addStatement(statement: KotlinStatement): THIS
        {
            assertNotClosed()
            body.addStatement(statement)
            return this as THIS
        }

        override fun endFunction(): PARENT
        {
            return close();
        }

        override fun writeTo(writer: Writer, indent: String)
        {
            signature.writeTo(writer, indent)
            writer.write("${indent}{\n")
            body.writeTo(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
