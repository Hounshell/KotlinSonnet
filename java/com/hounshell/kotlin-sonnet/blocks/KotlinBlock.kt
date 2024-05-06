@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.expressions.KotlinFunctionCallExpression
import com.hounshell.kotlin_sonnet.statements.KotlinExpressionStatement
import com.hounshell.kotlin_sonnet.statements.KotlinStatement


abstract class KotlinBlock
{
    interface Builder<BUILDER: Builder<BUILDER>>
    {
        fun expression(expression: KotlinExpression): BUILDER;
        fun call(functionName: String, vararg params: KotlinExpression) = expression(KotlinFunctionCallExpression(null, functionName, *params))
        fun call(target: KotlinExpression, functionName: String, vararg params: KotlinExpression) = expression(KotlinFunctionCallExpression(target, functionName, *params))
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER>> :
        Builder<BUILDER>,
        KotlinStatement

    protected abstract class BaseImpl<BUILDER: Builder<BUILDER>>() : BuilderAndWriter<BUILDER>
    {
        private val statements: MutableList<KotlinStatement> = mutableListOf()

        override fun expression(expression: KotlinExpression): BUILDER {
            return addStatement(KotlinExpressionStatement(expression))
        }

        protected fun addStatement(statement: KotlinStatement): BUILDER {
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
