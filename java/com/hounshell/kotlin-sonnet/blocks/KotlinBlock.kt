@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.statements.KotlinStatement
import java.io.Writer

interface KotlinBlock
{
    fun writeTo(writer: CodeWriter, indent: String)

    interface BuilderBase<THIS : BuilderBase<THIS>>
    {
        fun addStatement(statement: KotlinStatement): THIS

        fun addStatement(expression: KotlinExpression): THIS {
            return addStatement(expression.asStatement())
        }
    }

    abstract class ImplBase<THIS : BuilderBase<THIS>>() :
        BuilderBase<THIS>,
        KotlinBlock
    {
        private val statements: MutableList<KotlinStatement> = mutableListOf()

        override fun addStatement(statement: KotlinStatement): THIS {
            statements.add(statement)
            return this as THIS
        }

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            for (statement in statements) {
                statement.writeTo(writer, indent);
            }
        }
    }
}
