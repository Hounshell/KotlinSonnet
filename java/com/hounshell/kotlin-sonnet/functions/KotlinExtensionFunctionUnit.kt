package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.SelfBuildable
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockUnit
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionUnit<PARENT> :
    KotlinExtensionFunction,
    SelfBuildable<KotlinExtensionFunctionUnit.Builder<PARENT>>
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>,
        KotlinBlockUnit.BuilderBase<Builder<PARENT>, PARENT>
    {
    }

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            parent: PARENT
        ): KotlinExtensionFunctionUnit<PARENT> = Impl(
            onType,
            name,
            KotlinBlockUnit.impl(parent),
            parent)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            private val body: KotlinBlockUnit.Builder<PARENT>,
            private val parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.Impl(name, onType = onType),
                body as KotlinBlockUnit,
                parent),
            Builder<PARENT>,
            KotlinExtensionFunctionUnit<PARENT>
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

            override fun asBuilder(): Builder<PARENT> = this
        }
    }
}
