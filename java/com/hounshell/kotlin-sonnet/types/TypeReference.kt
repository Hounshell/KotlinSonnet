package com.hounshell.kotlin_sonnet.types

interface TypeReference {
    fun asDeclaration(): String
    fun asName(): String
}

class RealTypeReference(val packageName: String, val localName: String) : TypeReference
{
    constructor(javaClass: Class<*>) : this(javaClass.packageName, javaClass.simpleName) {
    }

    override fun asDeclaration(): String
    {
        return if (packageName == "java.lang") {
            localName;
        } else {
            "$packageName.$localName"
        }
    }

    override fun asName() = asDeclaration()
}
