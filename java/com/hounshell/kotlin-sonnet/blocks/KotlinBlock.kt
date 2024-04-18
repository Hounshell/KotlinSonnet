@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.bases.BaseKotlinBuilder
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import java.io.Writer

interface KotlinBlock : CodeWriter
{
    interface BuilderBase<THIS : BuilderBase<THIS>>
    {
        fun addStatement(statement: KotlinStatement): THIS

        fun addStatement(expression: KotlinExpression): THIS {
            return addStatement(expression.asStatement())
        }
    }

    interface Builder : BuilderBase<Builder>, CodeWriter
    {}

    companion object {
        @JvmStatic
        fun impl(): Builder = Impl()

        private class Impl() :
            ImplBase<Builder>(),
            Builder
        {}
    }

    abstract class ImplBase<THIS : BuilderBase<THIS>>() :
        BaseKotlinBuilder<Void?, Void?>(null, null),
        BuilderBase<THIS>,
        KotlinBlock
    {
        private val statements: MutableList<KotlinStatement> = mutableListOf()

        override fun addStatement(statement: KotlinStatement): THIS {
            assertNotClosed()
            statements.add(statement)
            return this as THIS
        }

        override fun writeTo(writer: Writer, indent: String)
        {
            for (statement in statements) {
                statement.writeTo(writer, indent);
            }
        }
    }
}
