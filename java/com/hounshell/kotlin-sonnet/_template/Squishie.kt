@file:Suppress("UNCHECKED_CAST")

package com.hounshell.kotlin_sonnet._template

import com.hounshell.kotlin_sonnet.CodeWriter

/**
 * This is a template file for a thing that is standalone: no base class, no derived classes.
 */
abstract class Squishie
{
    interface Writer
    {
        // Probably need a function to write it somewhere.
        fun writeTo(writer: CodeWriter, indent: String)
    }

    // Builder interface knows how to build stuff. PARENT is optional.
    interface Builder<PARENT>
    {
        // Builder methods go here, they may have default implementations.
        fun setName(name: String): Builder<PARENT>

        fun endSquishie(): PARENT
    }

    interface BuilderAndWriter<PARENT>:
        Builder<PARENT>,
        Writer

    companion object {
        fun <PARENT> impl(parent: PARENT): BuilderAndWriter<PARENT> = Impl(parent)

        private class Impl<PARENT>(
            private val parent: PARENT) : BuilderAndWriter<PARENT> {
            override fun setName(name: String): Builder<PARENT>
            {
                // TODO: Not yet implemented
                return this
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

}
