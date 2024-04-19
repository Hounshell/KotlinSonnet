package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.SelfBuildable
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockReturn
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnValueStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionReturn<PARENT> :
    KotlinExtensionFunction,
    SelfBuildable<KotlinExtensionFunctionReturn.Builder<PARENT>>
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>,
        KotlinBlockReturn.BuilderBase<Builder<PARENT>, PARENT>
    {
    }

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT
        ): KotlinExtensionFunctionReturn<PARENT> = Impl(
            onType,
            name,
            returnType,
            KotlinBlockReturn.impl(parent),
            parent)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            private val body: KotlinBlockReturn.Builder<PARENT>,
            private val parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.Impl(name, returnType, onType),
                body as KotlinBlockReturn,
                parent),
            Builder<PARENT>,
            KotlinExtensionFunctionReturn<PARENT>
        {
            override fun addStatement(statement: KotlinStatement): Builder<PARENT>
            {
                body.addStatement(statement)
                return this
            }

            override fun doReturn(expression: KotlinExpression): PARENT
            {
                addStatement(KotlinReturnValueStatement(expression))
                return parent
            }
        }
    }
}
