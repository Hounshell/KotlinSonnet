package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForValue
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

sealed class KotlinExtensionFunctionForValue:
    KotlinExtensionFunction,
    KotlinFunctionBaseForValue()
{
    interface Builder<PARENT> :
        KotlinFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinExtensionFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinFunctionBaseForValue.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
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
            body: KotlinBlockForValue.BuilderAndWriter<*, *>,
            parent: PARENT
        ) : KotlinFunctionBaseForValue.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, returnType, onType),
            body,
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
