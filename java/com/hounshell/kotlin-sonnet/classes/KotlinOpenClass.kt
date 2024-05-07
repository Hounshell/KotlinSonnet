@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.classes

import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForUnit
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForValue
import com.hounshell.kotlin_sonnet.functions.KotlinFunctionForUnit
import com.hounshell.kotlin_sonnet.functions.KotlinFunctionForValue
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

/**
 * This is a template file for a thing that is abstract and derives from a base interface.
 */
abstract class KotlinOpenClass: KotlinClass()
{
    interface BaseBuilder<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT> : KotlinClass.BaseBuilder<BUILDER, PARENT>
    {
        fun addOpenFunction(name: String, tailRecursion: Boolean = false): KotlinFunctionForUnit.Builder<BUILDER>
        fun addOpenFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinFunctionForValue.Builder<BUILDER>

        fun addOpenFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean = false): KotlinExtensionFunctionForUnit.Builder<BUILDER>
        fun addOpenFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinExtensionFunctionForValue.Builder<BUILDER>
    }

    interface Builder<PARENT> : BaseBuilder<Builder<PARENT>, PARENT>

    interface BaseBuilderAndWriter<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT> :
        KotlinClass.BaseBuilderAndWriter<BUILDER, PARENT>,
        BaseBuilder<BUILDER, PARENT>

    interface BuilderAndWriter<PARENT> :
        BaseBuilderAndWriter<Builder<PARENT>, PARENT>,
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

    // This is the base implementation, provided for derived implementations.
    protected abstract class BaseImpl<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT>(
        name: String,
        parent: PARENT
    ) : KotlinClass.BaseImpl<BUILDER, PARENT>(name, parent),
        BaseBuilderAndWriter<BUILDER, PARENT>
    {
        override fun addOpenFunction(name: String, tailRecursion: Boolean): KotlinFunctionForUnit.Builder<BUILDER>
        {
            return addFunction(KotlinFunctionForUnit.impl(name, true, false, tailRecursion, this as BUILDER))
        }

        override fun addOpenFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinFunctionForValue.Builder<BUILDER>
        {
            return addFunction(KotlinFunctionForValue.impl(name, returnType, true, false, tailRecursion, this as BUILDER))
        }

        override fun addOpenFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean): KotlinExtensionFunctionForUnit.Builder<BUILDER>
        {
            return addFunction(KotlinExtensionFunctionForUnit.impl(onType, name, true, false, tailRecursion, this as BUILDER))
        }

        override fun addOpenFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinExtensionFunctionForValue.Builder<BUILDER>
        {
            return addFunction(KotlinExtensionFunctionForValue.impl(onType, name, returnType, true, false, tailRecursion, this as BUILDER))
        }
    }
}