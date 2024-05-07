@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet._template

import com.hounshell.kotlin_sonnet.CodeWriter

/**
 * This is a template file for a thing that is standalone: no base class, no derived classes.
 */
abstract class Squishie
{
    // We probably need some way to write something, this is a common Writer.
    interface Writer
    {
        fun writeTo(writer: CodeWriter, indent: String)
    }

    // BaseBuilder and BaseBuilderAndWriter are used for classes that are going to be derived from.
    // This can derive from another BaseBuilder. PARENT is optional.
    interface BaseBuilder<BUILDER, PARENT>
    {
        // Builder methods go here, they may have default implementations.
        fun setName(name: String): BUILDER

        fun endSquishie(): PARENT
    }

    // This can derive from another BaseBuilderAndWriter. PARENT is optional.
    interface BaseBuilderAndWriter<BUILDER, PARENT>:
        BaseBuilder<BUILDER, PARENT>,
        Writer

    // Builder and BuilderAndWriter are used for classes that can be instantiated.
    interface Builder<PARENT> : BaseBuilder<Builder<PARENT>, PARENT>

    interface BuilderAndWriter<PARENT>:
        BaseBuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    // This is the implementation, used for classes that can be instantiated.
    companion object {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<PARENT> = Impl(parent)

        private class Impl<PARENT>(
            parent: PARENT
        ) :
            BaseImpl<Builder<PARENT>, PARENT>(parent),
            BuilderAndWriter<PARENT>
    }

    // This is the base implementation, provided for derived implementations.
    protected abstract class BaseImpl<BUILDER: BaseBuilder<BUILDER, PARENT>, PARENT>(
        private val parent: PARENT
    ) : BaseBuilderAndWriter<BUILDER, PARENT>
    {
        override fun setName(name: String): BUILDER
        {
            // TODO: Not yet implemented
            return this as BUILDER
        }

        override fun endSquishie(): PARENT
        {
            // TODO: Not yet implemented
            return parent
        }

        override fun writeTo(writer: CodeWriter, indent: String)
        {
            // TODO: Not yet implemented
        }
    }
}
