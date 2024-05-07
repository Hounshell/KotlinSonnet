package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockForUnit

sealed class KotlinFunctionForUnit: KotlinFunctionBaseForUnit()
{
    interface Builder<PARENT> :
        KotlinFunction.Builder<Builder<PARENT>, PARENT>,
        KotlinFunctionBaseForUnit.Builder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinFunction.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            name: String,
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            name,
            isOpen,
            isOverride,
            tailRecursion,
            parent)

        private class Impl<PARENT>(
            name: String,
            isOpen: Boolean,
            isOverride: Boolean,
            tailRecursion: Boolean,
            parent: PARENT
        ) : KotlinFunctionBaseForUnit.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name, tailRecursion = tailRecursion, isOpen = isOpen, isOverride = isOverride, parent = null),
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
