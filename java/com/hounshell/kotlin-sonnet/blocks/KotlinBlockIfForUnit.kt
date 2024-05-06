package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter
import com.hounshell.kotlin_sonnet.expressions.KotlinExpression
import com.hounshell.kotlin_sonnet.files.KotlinFile
import com.hounshell.kotlin_sonnet.statements.KotlinStatement

abstract class KotlinBlockIfForUnit : KotlinBlockForUnit()
{
    interface IfContinuation<PARENT> {
        fun endIf(): PARENT

        fun _else_(work: KotlinBlockElseForUnit.Builder<PARENT>.() -> Unit) = elseBlock(work)
        fun elseBlock(work: KotlinBlockElseForUnit.Builder<PARENT>.() -> Unit): PARENT

        fun _else_if_(condition: KotlinExpression, work: Builder<PARENT>.() -> Unit) = elseIf(condition, work)
        fun elseIf(condition: KotlinExpression, work: Builder<PARENT>.() -> Unit): IfContinuation<PARENT>
    }

    interface Builder<PARENT> :
        KotlinBlockForUnit.Builder<Builder<PARENT>, PARENT> {
        fun endIf(): PARENT

        fun _else_() = elseBlock()
        fun elseBlock(): KotlinBlockElseForUnit.Builder<PARENT>

        fun _else_if_(condition: KotlinExpression) = elseIf(condition)
        fun elseIf(condition: KotlinExpression): Builder<PARENT>

        fun define(work: Builder<PARENT>.() -> Unit): IfContinuation<PARENT> {
            apply(work)
            return object: IfContinuation<PARENT> {
                override fun endIf() = this@Builder.endIf()
                override fun elseBlock(work: KotlinBlockElseForUnit.Builder<PARENT>.() -> Unit) = this@Builder.elseBlock().define(work)
                override fun elseIf(condition: KotlinExpression, work: Builder<PARENT>.() -> Unit) =
                    this@Builder.elseIf(condition).define(work)
            }
        }
    }

    interface BuilderAndWriter<PARENT> :
        KotlinBlockForUnit.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            condition: KotlinExpression,
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(condition, true, parent)

        private class Impl<PARENT>(
            private val condition: KotlinExpression,
            private val ifFirstCase: Boolean,
            private val parent: PARENT
        ) : KotlinBlockForUnit.BaseImpl<Builder<PARENT>, PARENT>(parent),
            BuilderAndWriter<PARENT>
        {
            private var nextBlock: KotlinStatement? = null

            override fun endIf() = parent

            override fun elseIf(condition: KotlinExpression): Builder<PARENT> {
                val next = Impl(condition, false, parent)
                nextBlock = next
                return next
            }

            override fun elseBlock(): KotlinBlockElseForUnit.Builder<PARENT> {
                val next = KotlinBlockElseForUnit.impl(parent)
                nextBlock = next
                return next
            }

            override fun writeTo(writer: CodeWriter, indent: String)
            {
                if (ifFirstCase) {
                    writer.write("${indent}if (")
                } else
                {
                    writer.write(" else if (")
                }

                condition.writeTo(writer, "$indent    ")
                writer.write(") {\n")
                super.writeTo(writer, "$indent  ")
                writer.write("$indent}")
                nextBlock?.writeTo(writer, indent)

                if (ifFirstCase)
                {
                    writer.write("\n")
                }
            }
        }
    }
}