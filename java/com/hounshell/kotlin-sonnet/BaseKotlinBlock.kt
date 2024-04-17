package com.hounshell.kotlin_sonnet

import java.util.Collections

abstract class BaseKotlinBlock<IT, P, M>(
    private val parent: P,
    private val callback: ((IT) -> Unit)?) {

    var closed = false

    private val members: MutableList<M> = mutableListOf()

    protected fun getMembers(): List<M> = Collections.unmodifiableList(members)

    protected fun addMember(member: M) {
        assertNotClosed()
        members.add(member)
    }

    protected fun assertClosed() {
        if (!closed) {
            throw IllegalStateException("This object has not been finalized yet")
        }
    }

    protected fun assertNotClosed() {
        if (closed) {
            throw IllegalStateException("This object has already been finalized")
        }
    }

    protected fun close(): P {
        assertNotClosed()
        closed = true

        @Suppress("UNCHECKED_CAST")
        callback?.invoke(this as IT)

        return parent
    }
}

