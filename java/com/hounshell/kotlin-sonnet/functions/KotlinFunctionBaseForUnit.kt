@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit

abstract class KotlinFunctionBaseForUnit: KotlinFunction()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForUnit.Builder<BUILDER, PARENT>,
        KotlinFunction.Builder<BUILDER, PARENT>,
        KotlinSignature.Builder

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlockForUnit.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        private val signature: KotlinSignature.BuilderAndWriter,
        private val body: KotlinBlockForUnit.BuilderAndWriter<*, *>,
        private val parent: PARENT
    ) : KotlinFunction.BaseImpl<BUILDER, PARENT>(signature, body, parent),
        BuilderAndWriter<BUILDER, PARENT>
    {
        override fun doReturn(): PARENT
        {
            body.doReturn()
            return parent
        }
    }
}
