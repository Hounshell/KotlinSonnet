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
        val returnType: TypeReference,
        parent: P,
        callback: (KotlinExtensionFunctionWithReturn) -> Unit
    ) :
        KotlinExtensionFunction.ImplBase<Builder<P>, P, KotlinExtensionFunctionWithReturn>(onType, name, parent, callback),
        Builder<P>,
            KotlinExtensionFunctionWithReturn
    {
        override fun writeTo(writer: Writer, indent: String)
        {
            writer.write("${indent}fun ${onType.asDeclaration()}.$name")
            writeFunctionArguments(writer, "$indent    ")
            writer.write(": ${returnType.asDeclaration()} ")
            writer.write("${indent}{\n")
            writeFunctionBody(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
