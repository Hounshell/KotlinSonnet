package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

abstract class KotlinFunctionForUnit: KotlinFunction()
{
    interface Builder<PARENT> :
        KotlinFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinBlockForUnit.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            name: String,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            name,
            KotlinBlockForUnit.impl(parent),
            parent)

        private class Impl<PARENT>(
            name: String,
            private val body: KotlinBlock.BuilderAndWriter<*>,
            private val parent: PARENT
        ) : KotlinFunction.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name),
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
