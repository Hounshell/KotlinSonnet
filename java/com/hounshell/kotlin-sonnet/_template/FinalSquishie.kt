@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet._template

/**
 * This is a template file for a thing that derives from a base interface but can be built.
 */
abstract class FinalSquishie: AbstractSquishie()
{
    // We don't have a Writer interface, because it's in the base class.

    // Builder interface knows how to build stuff. PARENT is optional.
    interface Builder<PARENT> : AbstractSquishie.Builder<Builder<PARENT>, PARENT>
    {
        // Builder methods go here, they may have default implementations.
        fun setHeight(height: Int): Builder<PARENT>
    }

    interface BuilderAndWriter<PARENT> :
        AbstractSquishie.BuilderAndWriter<Builder<PARENT>, PARENT>,
        Builder<PARENT>

    companion object
    {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<PARENT> = Impl(parent)

        private class Impl<PARENT>(
            parent: PARENT
        ) : AbstractSquishie.BaseImpl<Builder<PARENT>, PARENT>(parent),
            BuilderAndWriter<PARENT>
        {
            override fun setHeight(height: Int): Builder<PARENT>
            {
                // TODO: Not yet implemented
                return this
            }
        }
    }
}