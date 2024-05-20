package com.github.nejer6.util.naturalsort

sealed class Part {
    data class Number(val value: Long) : Part()
    data class String(val value: kotlin.String) : Part()
}