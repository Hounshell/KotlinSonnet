@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.blocks.KotlinBlock
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.types.KotlinParameterDeclaration

abstract class KotlinFunction: KotlinBlock()
{
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.Builder<BUILDER>,
            KotlinSignature.Builder

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        KotlinBlock.BuilderAndWriter<BUILDER>,
        Builder<BUILDER, PARENT>
}
