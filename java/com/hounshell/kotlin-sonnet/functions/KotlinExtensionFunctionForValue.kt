package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForValue
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinReturnValueStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

abstract class KotlinExtensionFunctionForValue: KotlinExtensionFunction()
{
    interface Builder<PARENT> :
        KotlinExtensionFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinBlockForValue.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinExtensionFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            onType,
            name,
            returnType,
            KotlinBlockForValue.impl(parent),
            parent)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            private val body: KotlinBlock.BuilderAndWriter<*>,
            private val parent: PARENT
        ) : KotlinFunction.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, returnType, onType),
            body,
            parent
        ),
            BuilderAndWriter<PARENT>
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
