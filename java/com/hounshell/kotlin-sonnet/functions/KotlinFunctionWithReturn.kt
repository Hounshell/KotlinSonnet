package com.hounshell.kotlin_sonnet.functions

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.KotlinFile
import com.hounshell.kotlin_sonnet.types.TypeReference
import java.io.Writer

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
    ) : ImplBase<Impl<PARENT>, PARENT, KotlinFunctionWithReturn>(name, returnType, parent, callback)
    {}

    abstract class ImplBase<THIS : BuilderBase<THIS, PARENT>, PARENT, CALLBACK : KotlinFunctionWithReturn>(
            name: String,
            val returnType: TypeReference,
            parent: PARENT,
            callback: (CALLBACK) -> Unit
    ) :
            KotlinFunction.ImplBase<THIS, PARENT, CALLBACK>(name, parent, callback),
            BuilderBase<THIS, PARENT>,
            KotlinFunctionWithReturn
    {
        override fun writeTo(writer: Writer, indent: String)
        {
            writer.write("${indent}fun $name")
            writeFunctionArguments(writer, "$indent    ")
            writer.write(": ${returnType.asDeclaration()} ")
            writer.write("${indent}{\n")
            writeFunctionBody(writer, "$indent  ")
            writer.write("${indent}}\n")
        }
    }
}
