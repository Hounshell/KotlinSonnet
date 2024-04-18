package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.types.KotlinTypeReference

interface KotlinFunctionWithReturn : KotlinFunction
{
    interface BuilderBase<THIS: BuilderBase<THIS, P>, P> :
            KotlinFunction.BuilderBase<THIS, P>
    {
    }

    interface Builder<P> : BuilderBase<Builder<P>, P>
    {}

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT,
            callback: (KotlinFunctionWithReturn) -> Unit
        ): Builder<PARENT> = Impl(name, returnType, parent, callback)

        private class Impl<PARENT>(
            name: String,
            returnType: KotlinTypeReference,
            parent: PARENT,
            callback: (KotlinFunctionWithReturn) -> Unit
        ) :
            ImplBase<Builder<PARENT>, PARENT, KotlinFunctionWithReturn>(
                KotlinFunctionSignature.Impl(name, returnType),
                parent,
                callback
            ),
            Builder<PARENT>,
            KotlinFunctionWithReturn
        {}
    }

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
