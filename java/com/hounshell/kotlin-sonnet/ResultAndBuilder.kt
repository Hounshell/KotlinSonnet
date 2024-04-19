package com.hounshell.kotlin_sonnet

class ResultAndBuilder<RESULT, BUILDER>(value: Any) {

    val result = value as RESULT

    val builder = value as BUILDER
}



