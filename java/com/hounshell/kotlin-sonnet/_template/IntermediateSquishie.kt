@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet._template

import com.hounshell.kotlin_sonnet.CodeWriter

/**
 * This is a template file for a thing that is abstract and derives from a base interface.
 */
abstract class IntermediateSquishie: BaseSquishie()
{
    // We don't have a Writer interface, because it's in the base class.

    // Builder interface knows how to build stuff. PARENT is optional.
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT> : BaseSquishie.Builder<BUILDER, PARENT>
    {
        // Builder methods go here, they may have default implementations.
        fun setAge(age: Int): BUILDER
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT> :
        BaseSquishie.BuilderAndWriter<BUILDER, PARENT>,
        Builder<BUILDER, PARENT>

    // If you can't create an instance of an IntermediateSquishie, then remove this.
    companion object {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<*, PARENT> = Impl(parent)

        private class Impl<PARENT>(
            parent: PARENT
        ) :
            BaseImpl<Impl<PARENT>, PARENT>(parent)
    }

    // This is the base implementation, provided for derived implementations.
    protected abstract class BaseImpl<BUILDER: Builder<BUILDER, PARENT>, PARENT>(
        private val parent: PARENT
    ) : BuilderAndWriter<BUILDER, PARENT>
    {
        override fun setName(name: String): BUILDER
        {
            // TODO: Not yet implemented
            return this as BUILDER
        }

        override fun setAge(age: Int): BUILDER
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