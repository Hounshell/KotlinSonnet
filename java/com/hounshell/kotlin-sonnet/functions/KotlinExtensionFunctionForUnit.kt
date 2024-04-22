package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

sealed class KotlinExtensionFunctionForUnit:
    KotlinExtensionFunction,
    KotlinFunctionBaseForUnit()
{
    interface Builder<PARENT> :
        KotlinFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinExtensionFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinFunctionBaseForUnit.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
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
            body: KotlinBlockForUnit.BuilderAndWriter<*, *>,
            parent: PARENT
        ) : KotlinFunctionBaseForUnit.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, onType = onType),
            body,
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
