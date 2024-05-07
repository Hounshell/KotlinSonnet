package com.hounshell.kotlin_sonnet._template

import com.hounshell.kotlin_sonnet.classes.KotlinOpenClass
import com.hounshell.kotlin_sonnet.functions.KotlinSignature
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

/**
 * This is a template file for a thing that derives from a base interface but can be built.
 */
abstract class KotlinAbstractClass: KotlinOpenClass()
{
    // Builder interface knows how to build stuff. PARENT is optional.
    interface Builder<PARENT> : KotlinOpenClass.BaseBuilder<Builder<PARENT>, PARENT>
    {
        fun addAbstractFunction(name: String, tailRecursion: Boolean = false): KotlinSignature.Builder<Builder<PARENT>>
        fun addAbstractFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinSignature.Builder<Builder<PARENT>>

        fun addAbstractFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean = false): KotlinSignature.Builder<Builder<PARENT>>
        fun addAbstractFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean = false): KotlinSignature.Builder<Builder<PARENT>>
    }

    interface BuilderAndWriter<PARENT> :
        KotlinOpenClass.BaseBuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(name: String, parent: PARENT): BuilderAndWriter<PARENT> = Impl(name, parent)

        private class Impl<PARENT>(
            name: String,
            parent: PARENT
        ) : KotlinOpenClass.BaseImpl<Builder<PARENT>, PARENT>(name, parent),
            BuilderAndWriter<PARENT>
        {
            val abstractSignatures: MutableList<KotlinSignature.BuilderAndWriter<*>> = mutableListOf()

            override fun addAbstractFunction(name: String, tailRecursion: Boolean): KotlinSignature.Builder<Builder<PARENT>>
            {
                val signature = KotlinSignature.impl(name, tailRecursion = tailRecursion, parent = this as Builder<PARENT>)
                abstractSignatures.add(signature)
                return signature
            }

            override fun addAbstractFunction(name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinSignature.Builder<Builder<PARENT>>
            {
                val signature = KotlinSignature.impl(name, returnType, tailRecursion = tailRecursion, parent = this as Builder<PARENT>)
                abstractSignatures.add(signature)
                return signature
            }

            override fun addAbstractFunction(onType: KotlinTypeReference, name: String, tailRecursion: Boolean): KotlinSignature.Builder<Builder<PARENT>>
            {
                val signature = KotlinSignature.impl(name, onType = onType, tailRecursion = tailRecursion, parent = this as Builder<PARENT>)
                abstractSignatures.add(signature)
                return signature
            }

            override fun addAbstractFunction(onType: KotlinTypeReference, name: String, returnType: KotlinTypeReference, tailRecursion: Boolean): KotlinSignature.Builder<Builder<PARENT>>
            {
                val signature = KotlinSignature.impl(name, returnType, onType, tailRecursion = tailRecursion, parent = this as Builder<PARENT>)
                abstractSignatures.add(signature)
                return signature
            }
        }
    }
}