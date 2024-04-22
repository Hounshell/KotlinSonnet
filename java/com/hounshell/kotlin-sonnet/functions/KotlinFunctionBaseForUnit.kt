@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration

abstract class KotlinFunctionBaseForUnit: KotlinBlockForUnit()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForUnit.Builder<BUILDER, PARENT>,
        KotlinFunction.Builder<BUILDER, PARENT>,
        KotlinSignature.Builder
    {
        fun endFunction(): PARENT

        fun define(work: Builder<BUILDER, PARENT>.() -> Unit): PARENT {
            apply(work)
            return endFunction()
        }
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForUnit.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        private val signature: KotlinSignature.BuilderAndWriter,
        private val parent: PARENT
    ) : KotlinBlockForUnit.BaseImpl<BUILDER, PARENT>(parent),
        BuilderAndWriter<BUILDER, PARENT>
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
            super.writeTo(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
