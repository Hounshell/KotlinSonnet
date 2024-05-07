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
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            onType,
            name,
            returnType,
            isOpen,
            isOverride,
            tailRecursion,
            parent)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ) : KotlinFunctionBaseForValue.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, returnType, onType, isOpen, isOverride, tailRecursion, null),
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
