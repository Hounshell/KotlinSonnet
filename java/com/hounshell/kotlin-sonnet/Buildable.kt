package com.hounshell.kotlin_sonnet;

interface SelfBuildable<BUILDER> {
    fun asBuilder(): BUILDER = this as BUILDER
}
