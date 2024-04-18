package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.KotlinFile
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference
import java.io.Writer

interface KotlinExtensionFunction : KotlinFunction
{
    interface BuilderBase<THIS: BuilderBase<THIS, PARENT>, PARENT> :
            KotlinFunction.BuilderBase<THIS, PARENT>
    {
    }

    interface Builder<PARENT> : BuilderBase<Builder<PARENT>, PARENT>
    {}

    companion object
    {
        @JvmStatic
        fun <PARENT> impl(
            onType: KotlinTypeReference,
            name: String,
            parent: PARENT,
            callback: (KotlinExtensionFunction) -> Unit
        ): Builder<PARENT> = Impl(onType, name, parent, callback)

        private class Impl<PARENT>(
            onType: KotlinTypeReference,
            name: String,
            parent: PARENT,
            callback: (KotlinExtensionFunction) -> Unit
        ) : ImplBase<Builder<PARENT>, PARENT, KotlinExtensionFunction>(
            KotlinFunctionSignature.Impl(name, onType = onType),
            parent,
            callback
        ),
            Builder<PARENT>
        {}
    }

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
