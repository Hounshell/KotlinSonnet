package com.hounshell.kotlin_sonnet.expressions

import java.io.Writer

class KotlinLiteralExpression(private val representation: String) : KotlinExpression
{
    override fun writeTo(writer: Writer, indent: String)
    {
        writer.write(representation)
    }
}

fun literal(value: Boolean) = KotlinLiteralExpression(value.toString())

fun literal(value: Byte) = KotlinLiteralExpression(value.toString())
fun literal(value: Short) = KotlinLiteralExpression(value.toString())
fun literal(value: Int) = KotlinLiteralExpression(value.toString())
fun literal(value: Long) = KotlinLiteralExpression("${value}L")

fun literal(value: Float) = KotlinLiteralExpression("${value}F")
fun literal(value: Double) = KotlinLiteralExpression(value.toString())

fun literal(value: String) = KotlinLiteralExpression("\"$value\"")
