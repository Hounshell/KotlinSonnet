package com.hounshell.kotlin_sonnet.bases

abstract class BaseKotlinBuilder<CALLBACK, PARENT>(
    private val parent: PARENT,
    private val callback: ((CALLBACK) -> Unit)?) {

    var closed = false

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

    protected fun close(): PARENT {
        assertNotClosed()
        closed = true

        @Suppress("UNCHECKED_CAST")
        callback?.invoke(this as CALLBACK)

        return parent
    }
}

