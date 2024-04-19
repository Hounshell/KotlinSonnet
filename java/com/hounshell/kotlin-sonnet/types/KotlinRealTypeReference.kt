package com.hounshell.kotlin_sonnet.types

class KotlinRealTypeReference(val canonicalName: String, override val isNullable: Boolean = false) : KotlinTypeReference
{
    constructor(javaClass: Class<*>, isNullable: Boolean = false): this(
        javaClass.canonicalName,
        isNullable)

    private val baseName = canonicalName
        .splitToSequence('$')
        .first()

    val packageName:String
        get()
        {
            val lastDotIndex = baseName.lastIndexOf('.')
            return if (lastDotIndex >= 0)
            {
                baseName.substring(0, lastDotIndex)
            }
            else
            {
                ""
            }
        }

    val localName:String
        get()
        {
            val lastDotIndex = baseName.lastIndexOf('.')
            return if (lastDotIndex >= 0)
            {
                baseName.substring(lastDotIndex + 1)
            }
            else
            {
                baseName
            }
        }

    override fun asDeclaration(): String
    {
        return if (isNullable) {
            "${asName()}?" }
        else {
            asName()
        }
    }

    override fun asName(): String {
        return if (canonicalName == "int") {
            "Int"
        } else if (packageName == "java.lang") {
            localName
        } else {
            canonicalName
        }
    }
}

fun type(javaClass: Class<*>) = KotlinRealTypeReference(javaClass)

fun type(canonicalName: String) = KotlinRealTypeReference(canonicalName)

fun nullableType(javaClass: Class<*>) = KotlinRealTypeReference(javaClass, true)

fun nullableType(canonicalName: String) = KotlinRealTypeReference(canonicalName, true)
