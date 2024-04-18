@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.bases.BaseKotlinBlock
import java.io.Writer

interface KotlinBlock : CodeWriter
{
    interface BuilderBase<THIS : BuilderBase<THIS, PARENT>, PARENT>
    {
        fun addStatement(): THIS
    }

    interface Builder<P> : BuilderBase<Builder<P>, P>
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinBlock>(
        parent: PARENT,
        callback: (CALLBACK) -> Unit
    ) :
        BaseKotlinBlock<CALLBACK, PARENT>(parent, callback),
        BuilderBase<THIS, PARENT>,
            KotlinBlock
    {
        override fun addStatement(): THIS {
            TODO("Not implemented")
        }

        override fun writeTo(writer: Writer, indent: String)
        {
            TODO("Not implemented")
        }
    }
}
