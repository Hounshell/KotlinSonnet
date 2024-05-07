@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.classes

abstract class KotlinFinalClass: KotlinClass()
{
    interface Builder<PARENT> : KotlinClass.BaseBuilder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT> :
        KotlinClass.BaseBuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object {
        fun <PARENT> impl(name: String, parent: PARENT): BuilderAndWriter<PARENT> = Impl(name, parent)

        private class Impl<PARENT>(
            name: String,
            parent: PARENT
        ) :
            BaseImpl<Builder<PARENT>, PARENT>(name, parent),
            BuilderAndWriter<PARENT>
    }
}