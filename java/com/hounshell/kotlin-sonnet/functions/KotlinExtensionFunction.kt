package com.hounshell.kotlin_sonnet.functions

abstract class KotlinExtensionFunction: KotlinFunction()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinFunction.Builder<BUILDER, PARENT>

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinFunction.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>
}
