package com.hounshell.kotlin_sonnet.expressions

import com.hounshell.kotlin_sonnet.CodeWriter

class KotlinBinaryExpression(
    private val operator: String,
    private val first: KotlinExpression,
    private val second: KotlinExpression,
    vararg private val rest: KotlinExpression
) : KotlinExpression
{
    override fun writeTo(writer: CodeWriter, indent: String)
    {
        writeTerm(first, writer, indent)
        writer.write(" $operator ")
        writeTerm(second, writer, indent)
        for (next in rest) {
            writer.write(" $operator ")
            writeTerm(next, writer, indent)
        }
    }

    private fun writeTerm(term: KotlinExpression, writer: CodeWriter, indent: String) {
        if (term.shouldWrapInParentheses()) {
            writer.write("(")
            term.writeTo(writer, indent)
            writer.write(")")
        } else {
            term.writeTo(writer, indent)
        }
    }

    override fun shouldWrapInParentheses() = true
}

fun add(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("+", first, second, *rest)

fun subtract(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("-", first, second, *rest)

fun multiply(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("*", first, second, *rest)

fun divide(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("/", first, second, *rest)

fun and(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("&&", first, second, *rest)

fun or(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("||", first, second, *rest)

fun bitwiseAnd(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("and", first, second, *rest)

fun bitwiseOr(first: KotlinExpression, second: KotlinExpression, vararg rest:KotlinExpression) =
    KotlinBinaryExpression("or", first, second, *rest)
