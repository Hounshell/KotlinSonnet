package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockUnit
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference
import java.io.Writer

interface KotlinFunctionUnit : KotlinFunction
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>
    {
    }

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT
        ): Builder<PARENT> = Impl(
            name,
            returnType,
            KotlinBlockUnit.impl(parent),
            parent)

        private class Impl<PARENT>(
            name: String,
            returnType: KotlinTypeReference,
            val body: KotlinBlockUnit.Builder<PARENT>,
            parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.Impl(name, returnType),
                body as KotlinBlockUnit,
                parent),
            Builder<PARENT>,
            KotlinFunctionUnit
        {
            override fun addStatement(statement: KotlinStatement): Builder<PARENT>
            {
                body.addStatement(statement)
                return this
            }
        }
    }
}
