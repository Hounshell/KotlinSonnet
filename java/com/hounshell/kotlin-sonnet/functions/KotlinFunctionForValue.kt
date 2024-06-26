package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

sealed class KotlinFunctionForValue: KotlinFunctionBaseForValue()
{
    interface Builder<PARENT> :
        KotlinFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinFunctionBaseForValue.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            name: String,
            returnType: KotlinTypeReference,
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            name,
            returnType,
            isOpen,
            isOverride,
            tailRecursion,
            parent)

        private class Impl<PARENT>(
            name: String,
            returnType: KotlinTypeReference,
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ) : KotlinFunctionBaseForValue.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, returnType, isOpen = isOpen, isOverride = isOverride, tailRecursion = tailRecursion, parent = null),
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
