package com.hounshell.kotlin_sonnet.blocks

import com.hounshell.kotlin_sonnet.CodeWriter

abstract class KotlinBlockElseForValue : KotlinBlockForValue()
{
    interface Builder<PARENT> : KotlinBlockForValue.Builder<Builder<PARENT>, PARENT>
    {
        fun endIf(): PARENT

        fun define(work: Builder<PARENT>.() -> Unit): PARENT
        {
            apply(work)
            return endIf()
        }
    }

    interface BuilderAndWriter<PARENT> :
        KotlinBlockForValue.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(
            parent: PARENT
        ): BuilderAndWriter<PARENT> = Impl(parent)

        private class Impl<PARENT>(
            private val parent: PARENT
        ) : KotlinBlockForValue.BaseImpl<Builder<PARENT>, PARENT>(parent),
            BuilderAndWriter<PARENT>
        {
            override fun endIf() = parent

            override fun writeTo(writer: CodeWriter, indent: String)
            {
                writer.write(" else {\n")
                super.writeTo(writer, "$indent  ")
                writer.write("$indent}")
            }
        }
    }
}