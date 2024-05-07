@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.classes

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForUnit
import com.hounshell.kotlin_sonnet.functions.KotlinExtensionFunctionForValue
import com.hounshell.kotlin_sonnet.functions.KotlinFunction
import com.hounshell.kotlin_sonnet.functions.KotlinFunctionForUnit
import com.hounshell.kotlin_sonnet.functions.KotlinFunctionForValue
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

/**
 * This is a template file for a thing that is abstract and derives from a base interface.
 */
abstract class KotlinClass
{
    interface BaseBuilder<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT>
    {
        fun addFunction(name: String, tailRecursion: Boolean = false): KotlinFunctionForUnit.Builder<BUILDER>
        fun addFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinFunctionForValue.Builder<BUILDER>

        fun addFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean = false): KotlinExtensionFunctionForUnit.Builder<BUILDER>
        fun addFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinExtensionFunctionForValue.Builder<BUILDER>

        fun endClass(): PARENT
    }

    interface Writer
    {
        fun writeTo(writer: CodeWriter, indent: String)
    }

    interface BaseBuilderAndWriter<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT> :
        BaseBuilder<BUILDER, PARENT>,
        Writer

    protected abstract class BaseImpl<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT>(
        private val name: String,
        private val parent: PARENT
    ) : BaseBuilderAndWriter<BUILDER, PARENT>
    {
        private val functions: MutableList<KotlinFunction.BuilderAndWriter<*, *>> = mutableListOf()

        protected fun <T : KotlinFunction.BuilderAndWriter<*, *>> addFunction(function: T): T {
            functions.add(function)
            return function
        }

        override fun addFunction(name: String, tailRecursion: Boolean): KotlinFunctionForUnit.Builder<BUILDER>
        {
            return addFunction(KotlinFunctionForUnit.impl(name, false, false, tailRecursion, this as BUILDER))
        }

        override fun addFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinFunctionForValue.Builder<BUILDER>
        {
            return addFunction(KotlinFunctionForValue.impl(name, returnType, false, false, tailRecursion, this as BUILDER))
        }

        override fun addFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean): KotlinExtensionFunctionForUnit.Builder<BUILDER>
        {
            return addFunction(KotlinExtensionFunctionForUnit.impl(onType, name, false, false, tailRecursion, this as BUILDER))
        }

        override fun addFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinExtensionFunctionForValue.Builder<BUILDER>
        {
            return addFunction(KotlinExtensionFunctionForValue.impl(onType, name, returnType, false, false, tailRecursion, this as BUILDER))
        }

        override fun endClass(): PARENT
        {
            return parent
        }

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            writer.write("${indent}class $name {\n${indent}}\n")
            // TODO: Finish this.
        }
    }
}