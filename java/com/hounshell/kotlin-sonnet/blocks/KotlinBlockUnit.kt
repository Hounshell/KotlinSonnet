package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement

interface KotlinBlockUnit : KotlinBlock
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT> : KotlinBlock.BuilderBase<THIS>
    {
        fun doReturn(): PARENT
    }

    interface Builder<PARENT> : BuilderBase<Builder<PARENT>, PARENT>
    {}

    companion object {
        @JvmStatic
        fun <PARENT> impl(parent: PARENT): ResultAndBuilder<KotlinBlockUnit, Builder<PARENT>> =
            ResultAndBuilder(Impl(parent))

        private class Impl<PARENT>(parent: PARENT) :
            ImplBase<Builder<PARENT>, PARENT>(parent),
            Builder<PARENT>
        {}
    }

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT>(
        private val parent: PARENT
    ) :
        KotlinBlock.ImplBase<THIS>(),
        BuilderBase<THIS, PARENT>,
        KotlinBlockUnit
    {
        override fun doReturn(): PARENT
        {
            addStatement(KotlinReturnStatement())
            return parent
        }
    }
}
