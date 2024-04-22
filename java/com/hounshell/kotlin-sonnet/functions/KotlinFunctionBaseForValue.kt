@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForValue
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration

abstract class KotlinFunctionBaseForValue: KotlinBlockForValue()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForValue.Builder<BUILDER, PARENT>,
        KotlinFunction.Builder<BUILDER, PARENT>,
        KotlinSignature.Builder

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForValue.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        private val signature: KotlinSignature.BuilderAndWriter,
        parent: PARENT
    ) : KotlinBlockForValue.BaseImpl<BUILDER, PARENT>(parent),
        BuilderAndWriter<BUILDER, PARENT>
    {
        override fun addParameter(parameter: KotlinParameterDeclaration): BUILDER
        {
            signature.addParameter(parameter)
            return this as BUILDER
        }

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            signature.writeTo(writer, indent)
            writer.write("${indent}{\n")
            super.writeTo(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
