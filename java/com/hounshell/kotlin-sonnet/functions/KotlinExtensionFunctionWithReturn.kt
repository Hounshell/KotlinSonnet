package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.KotlinFile
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinExtensionFunctionWithReturn : KotlinExtensionFunction
{
    interface Builder<P> :
            KotlinExtensionFunction.BuilderBase<Builder<P>, P>,
            KotlinFunctionWithReturn.BuilderBase<Builder<P>, P>
    {
    }

    class Impl<P>(
        onType: TypeReference,
        name: String,
        returnType: TypeReference,
        parent: P,
        callback: (KotlinExtensionFunctionWithReturn) -> Unit
    ) :
        KotlinExtensionFunction.ImplBase<Builder<P>, P, KotlinExtensionFunctionWithReturn>(
                KotlinFunctionSignature.Impl(name, returnType, onType),
                parent,
                callback),
        Builder<P>,
        KotlinExtensionFunctionWithReturn
    {
    }
}
