@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForValue
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression

abstract class KotlinFunctionBaseForValue: KotlinFunction()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForValue.Builder<BUILDER, PARENT>,
        KotlinFunction.Builder<BUILDER, PARENT>,
        KotlinSignature.Builder

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForValue.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        signature: KotlinSignature.BuilderAndWriter,
        private val body: KotlinBlockForValue.BuilderAndWriter<*, *>,
        private val parent: PARENT
    ) : KotlinFunction.BaseImpl<BUILDER, PARENT>(signature, body, parent),
        BuilderAndWriter<BUILDER, PARENT>
    {
        override fun doReturn(expression: KotlinExpression): PARENT
        {
            body.doReturn(expression)
            return parent
        }
    }
}
