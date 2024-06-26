package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter

class KotlinLiteralExpression(representation: String) : KotlinSimpleExpression(representation)

fun literal(value: Boolean) = KotlinLiteralExpression(value.toString())

fun literal(value: Byte) = KotlinLiteralExpression(value.toString())
fun literal(value: Short) = KotlinLiteralExpression(value.toString())
fun literal(value: Int) = KotlinLiteralExpression(value.toString())
fun literal(value: Long) = KotlinLiteralExpression("${value}L")

fun literal(value: Float) = KotlinLiteralExpression("${value}F")
fun literal(value: Double) = KotlinLiteralExpression(value.toString())

fun literal(value: String) = KotlinLiteralExpression("\"$value\"")
