package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinExtensionFunctionWithReturn : KotlinExtensionFunction
{
    interface Builder<P> :
        KotlinExtensionFunction.BuilderBase<Builder<P>, P>,
        KotlinFunctionWithReturn.BuilderBase<Builder<P>, P>
    {}

    class Impl<P>(
        onType: KotlinTypeReference,
        name: String,
        returnType: KotlinTypeReference,
        parent: P,
        callback: (KotlinExtensionFunctionWithReturn) -> Unit
    ) :
        KotlinExtensionFunction.ImplBase<Builder<P>, P, KotlinExtensionFunctionWithReturn>(
            KotlinFunctionSignature.Impl(name, returnType, onType),
            parent,
            callback
        ),
        Builder<P>,
        KotlinExtensionFunctionWithReturn
    {}
}