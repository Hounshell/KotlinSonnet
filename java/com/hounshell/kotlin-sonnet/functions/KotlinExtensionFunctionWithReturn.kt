package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionWithReturn : KotlinExtensionFunction
{
    interface Builder<P> :
        KotlinExtensionFunction.BuilderBase<Builder<P>, P>,
        KotlinFunctionWithReturn.BuilderBase<Builder<P>, P>
    {}

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT,
            callback: (KotlinExtensionFunctionWithReturn) -> Unit
        ): Builder<PARENT> = Impl(onType, name, returnType, parent, callback)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT,
            callback: (KotlinExtensionFunctionWithReturn) -> Unit
        ) :
            KotlinExtensionFunction.ImplBase<Builder<PARENT>, PARENT, KotlinExtensionFunctionWithReturn>(
                KotlinFunctionSignature.Impl(name, returnType, onType),
                parent,
                callback
            ),
            Builder<PARENT>,
            KotlinExtensionFunctionWithReturn
        {}
    }
}
