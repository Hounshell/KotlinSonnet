package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnValueStatement

interface KotlinBlockReturn : KotlinBlock
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT> : KotlinBlock.BuilderBase<THIS>
    {
        fun doReturn(expression: KotlinExpression): PARENT
    }

    interface Builder<PARENT> : BuilderBase<Builder<PARENT>, PARENT>
    {}

    companion object {
        @JvmStatic
        fun <PARENT> impl(parent: PARENT): ResultAndBuilder<KotlinBlockReturn, Builder<PARENT>> =
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
        KotlinBlockReturn
    {
        override fun doReturn(expression: KotlinExpression): PARENT
        {
            addStatement(KotlinReturnValueStatement(expression))
            return parent
        }
    }
}
