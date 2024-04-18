package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.KotlinFile
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

interface KotlinExtensionFunction : KotlinFunction
{
    interface BuilderBase<THIS: BuilderBase<THIS, PARENT>, PARENT> :
            KotlinFunction.BuilderBase<THIS, PARENT>
    {
    }

    interface Builder<PARENT> : BuilderBase<Builder<PARENT>, PARENT>
    {}

    class Impl<PARENT>(
            onType: TypeReference,
            name: String,
            parent: PARENT,
            callback: (KotlinExtensionFunction) -> Unit
    ) : ImplBase<Builder<PARENT>, PARENT, KotlinExtensionFunction>(
            KotlinFunctionSignature.Impl(name, onType = onType),
            parent,
            callback),
        Builder<PARENT>
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinExtensionFunction>(
            signature: KotlinFunctionSignature.Builder,
            parent: PARENT,
            callback: (CALLBACK) -> Unit
    ) :
            KotlinFunction.ImplBase<THIS, PARENT, CALLBACK>(signature, parent, callback),
            BuilderBase<THIS, PARENT>,
            KotlinExtensionFunction
    {
    }
}
