package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockReturn
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnValueStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionReturn<PARENT> : KotlinExtensionFunction {
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
        ): ResultAndBuilder<KotlinExtensionFunctionReturn<PARENT>, Builder<PARENT>> = ResultAndBuilder(Impl(
                onType,
                name,
                returnType,
                KotlinBlockReturn.impl(parent),
                parent))

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            private val body: ResultAndBuilder<KotlinBlockReturn, KotlinBlockReturn.Builder<PARENT>>,
            private val parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.impl(name, returnType, onType),
                body,
                parent),
            Builder<PARENT>,
            KotlinExtensionFunctionReturn<PARENT>
        {
            override fun addStatement(statement: KotlinStatement): Builder<PARENT>
            {
                body.builder.addStatement(statement)
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
