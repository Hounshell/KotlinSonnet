@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement
import com.hounshell.kotlin_sonnet.statements.KotlinReturnValueStatement


abstract class KotlinBlockForValue : KotlinBlock()
{
    interface Builder<BUILDER : Builder<BUILDER, PARENT>, PARENT> : KotlinBlock.Builder<BUILDER>
    {
        fun ifBlock(condition: KotlinExpression): KotlinBlockIfForValue.Builder<BUILDER>
        fun doReturn(expression: KotlinExpression): PARENT
    }

    interface BuilderAndWriter<BUILDER : Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.BuilderAndWriter<BUILDER>,
        Builder<BUILDER, PARENT>

    companion object
    {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<*, PARENT> = Impl(parent)

        private class Impl<PARENT>(
            parent: PARENT
        ) : BaseImpl<Impl<PARENT>, PARENT>(parent),
            BuilderAndWriter<Impl<PARENT>, PARENT>
    }

    protected abstract class BaseImpl<BUILDER : Builder<BUILDER, PARENT>, PARENT>(
        private val parent: PARENT
    ) : KotlinBlock.BaseImpl<BUILDER>(),
        BuilderAndWriter<BUILDER, PARENT>
    {
        override fun ifBlock(condition: KotlinExpression): KotlinBlockIfForValue.Builder<BUILDER> {
            val child = KotlinBlockIfForValue.impl(condition, this as BUILDER)
            addStatement(child)
            return child
        }

        override fun doReturn(expression: KotlinExpression): PARENT
        {
            addStatement(KotlinReturnValueStatement(expression))
            return parent
        }
    }
}