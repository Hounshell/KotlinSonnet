package com.hounshell.kotlin_sonnet.types

class KotlinRealTypeReference(val canonicalName: String) : KotlinTypeReference
{
    constructor(javaClass: Class<*>): this(javaClass.canonicalName!!)

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
        return if (packageName == "java.lang") {
            localName
        } else {
            canonicalName
        }
    }

    override fun asName() = asDeclaration()
}

fun type(javaClass: Class<*>) = KotlinRealTypeReference(javaClass)

fun type(canonicalName: String) = KotlinRealTypeReference(canonicalName)
