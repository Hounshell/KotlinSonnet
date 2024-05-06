package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter

open class KotlinUnaryExpression(
    private val operator: String,
    private val value: KotlinExpression
) : KotlinExpression
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writer.write(operator)
        value.writeTo(writer, indent)
    }

    class Negative(internal val value: KotlinExpression): KotlinUnaryExpression("-", value)

    class Not(internal val value: KotlinExpression): KotlinUnaryExpression("!", value)
}

fun negative(value: KotlinExpression): KotlinExpression {
    return if (value is KotlinUnaryExpression.Negative) {
        value.value
    } else {
        KotlinUnaryExpression.Negative(value)
    }
}

fun not(value: KotlinExpression): KotlinExpression {
    return if (value is KotlinUnaryExpression.Not) {
        value.value
    } else {
        KotlinUnaryExpression.Not(value)
    }
}

fun spread(value: KotlinExpression) = KotlinUnaryExpression("*", value)
