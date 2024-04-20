@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinStatement


abstract class KotlinBlock
{
    interface Writer
    {
        fun writeTo(writer: CodeWriter, indent: String)
    }

    interface Builder<BUILDER: Builder<BUILDER>>
    {
        fun addStatement(statement: KotlinStatement): BUILDER

        fun addStatement(expression: KotlinExpression): BUILDER {
            return addStatement(expression.asStatement())
        }
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER>> :
        Builder<BUILDER>,
        Writer

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER>>() : BuilderAndWriter<BUILDER>
    {
        private val statements: MutableList<KotlinStatement> = mutableListOf()

        override fun addStatement(statement: KotlinStatement): BUILDER {
            statements.add(statement)
            return this as BUILDER
        }

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            for (statement in statements) {
                statement.writeTo(writer, indent);
            }
        }
    }
}
