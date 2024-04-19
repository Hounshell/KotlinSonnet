package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockUnit
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

interface KotlinFunctionUnit : KotlinFunction
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>
    {}

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            name: String,
            parent: PARENT
        ): ResultAndBuilder<KotlinFunctionUnit, Builder<PARENT>> = ResultAndBuilder(
            Impl(
                name,
                KotlinBlockUnit.impl(parent),
                parent
            )
        )

        private class Impl<PARENT>(
            name: String,
            private val body: ResultAndBuilder<KotlinBlockUnit, KotlinBlockUnit.Builder<PARENT>>,
            parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.impl(name),
                body,
                parent
            ),
            Builder<PARENT>,
            KotlinFunctionUnit
        {
            override fun addStatement(statement: KotlinStatement): Builder<PARENT>
            {
                body.builder.addStatement(statement)
                return this
            }
        }
    }
}