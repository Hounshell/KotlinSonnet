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
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(
            name,
            parent)

        private class Impl<PARENT>(
            name: String,
            parent: PARENT
        ) : KotlinFunctionBaseForUnit.BaseImpl<Builder<PARENT>, PARENT>(
            KotlinSignature.impl(name),
            parent
        ),
            BuilderAndWriter<PARENT>
    }
}
