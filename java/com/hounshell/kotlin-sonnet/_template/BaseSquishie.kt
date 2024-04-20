package com.hounshell.kotlin_sonnet._template

import com.hounshell.kotlin_sonnet.CodeWriter

/**
 * This is a template file for a thing that is just an interface, no implementation.
 */
abstract class BaseSquishie
{
    interface Writer
    {
        // Probably need a function to write it somewhere.
        fun writeTo(writer: CodeWriter, indent: String)
    }

    // Builder interface knows how to build stuff. PARENT is optional.
    interface Builder<BUILDER: Builder<BUILDER, PARENT>, PARENT>
    {
        // Builder methods go here, they may have default implementations.
        fun setName(name: String): BUILDER

        fun endSquishie(): PARENT
    }

    interface BuilderAndWriter<BUILDER: Builder<BUILDER, PARENT>, PARENT>:
        Builder<BUILDER, PARENT>,
        Writer
}
