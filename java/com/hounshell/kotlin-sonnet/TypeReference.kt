package com.hounshell.kotlin_sonnet

class TypeReference(val fullyQualifiedName: String) {
    constructor(javaClass: Class<*>) : this(javaClass.canonicalName!!)
}
