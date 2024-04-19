package com.hounshell.kotlin_sonnet.functions

interface KotlinExtensionFunction : KotlinFunction
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>
    {
    }
}
