package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

abstract class KotlinExtensionFunctionForUnit: KotlinExtensionFunction()
{
    interface Builder<PARENT> :
        KotlinExtensionFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinBlockForUnit.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinExtensionFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            onType,
            name,
            KotlinBlockForUnit.impl(parent),
            parent)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            private val body: KotlinBlock.BuilderAndWriter<*>,
            private val parent: PARENT
        ) : KotlinFunction.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, onType = onType),
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

            override fun doReturn(): PARENT
            {
                addStatement(KotlinReturnStatement())
                return parent
            }
        }
    }
}
