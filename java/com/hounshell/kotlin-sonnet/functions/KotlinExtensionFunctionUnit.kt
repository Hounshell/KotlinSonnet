package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.ResultAndBuilder
import com.hounshell.kotlin_sonnet.blocks.KotlinBlockUnit
import com.hounshell.kotlin_sonnet.statements.KotlinReturnStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionUnit<PARENT> : KotlinExtensionFunction {
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>,
        KotlinBlockUnit.BuilderBase<Builder<PARENT>, PARENT>
    {}

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            parent: PARENT
        ): ResultAndBuilder<KotlinExtensionFunctionUnit<PARENT>, Builder<PARENT>> = ResultAndBuilder(Impl(
                onType,
                name,
                KotlinBlockUnit.impl(parent),
                parent))

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            private val body: ResultAndBuilder<KotlinBlockUnit, KotlinBlockUnit.Builder<PARENT>>,
            private val parent: PARENT
        ) :
            KotlinFunction.ImplBase<Builder<PARENT>, PARENT>(
                KotlinFunctionSignature.impl(name, onType = onType),
                body,
                parent),
            Builder<PARENT>,
            KotlinExtensionFunctionUnit<PARENT>
        {
            override fun addStatement(statement: KotlinStatement): Builder<PARENT>
            {
                body.builder.addStatement(statement)
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
