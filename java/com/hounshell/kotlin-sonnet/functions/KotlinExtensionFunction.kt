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
    ) : ImplBase<Builder<PARENT>, PARENT, KotlinExtensionFunction>(onType, name, parent, callback),
        Builder<PARENT>
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinExtensionFunction>(
            val onType: TypeReference,
            name: String,
            parent: PARENT,
            callback: (CALLBACK) -> Unit
    ) :
            KotlinFunction.ImplBase<THIS, PARENT, CALLBACK>(name, parent, callback),
            BuilderBase<THIS, PARENT>,
            KotlinExtensionFunction
    {
        override fun writeTo(writer: Writer, indent: String)
        {
            writer.write("${indent}fun ${onType.asDeclaration()}.$name")
            writeFunctionArguments(writer, "$indent    ")
            writer.write("${indent} {\n")
            writeFunctionBody(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
