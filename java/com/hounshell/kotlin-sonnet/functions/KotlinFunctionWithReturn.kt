package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.types.TypeReference

interface KotlinFunctionWithReturn : KotlinFunction
{
    interface BuilderBase<THIS: BuilderBase<THIS, P>, P> :
            KotlinFunction.BuilderBase<THIS, P>
    {
    }

    interface Builder<P> : BuilderBase<Builder<P>, P>
    {}

    class Impl<PARENT>(
            name: String,
            returnType: TypeReference,
            parent: PARENT,
            callback: (KotlinFunctionWithReturn) -> Unit
    ) : ImplBase<Impl<PARENT>, PARENT, KotlinFunctionWithReturn>(
            KotlinFunctionSignature.Impl(name, returnType),
            parent,
            callback)
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinFunctionWithReturn>(
            signature: KotlinFunctionSignature.Builder,
            parent: PARENT,
            callback: (CALLBACK) -> Unit
    ) :
            KotlinFunction.ImplBase<THIS, PARENT, CALLBACK>(signature, parent, callback),
            BuilderBase<THIS, PARENT>,
            KotlinFunctionWithReturn
    {}
}
