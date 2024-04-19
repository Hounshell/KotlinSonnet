package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.blocks.KotlinBlockReturn
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import com.hounshell.kotlin_sonnet.types.KotlinTypeReference
import java.io.Writer

interface KotlinExtensionFunction : KotlinFunction
{
    interface Builder<PARENT> :
        KotlinFunction.BuilderBase<Builder<PARENT>, PARENT>
    {
    }
}
