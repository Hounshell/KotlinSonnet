package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement

abstract class KotlinBlockForUnit : KotlinBlock()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> : KotlinBlock.Builder<BUILDER>
    {
        fun doReturn(): PARENT
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.BuilderAndWriter<BUILDER>,
        Builder<BUILDER, PARENT>

    companion object
    {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<*, PARENT> = Impl(parent)

        private class Impl<PARENT>(
            private val parent: PARENT
        ) : KotlinBlock.BaseImpl<Impl<PARENT>>(),
            BuilderAndWriter<Impl<PARENT>, PARENT>
        {
            override fun doReturn(): PARENT
            {
                addStatement(KotlinReturnStatement())
                return parent
            }
        }
    }
}
