package com.github.nejer6.util.naturalsort

sealed class Part {
    data class Number(val value: Int) : Part()
    data class String(val value: kotlin.String) : Part()
}