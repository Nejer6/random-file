package com.github.nejer6.util.naturalsort

import java.util.*

class StringParser(
    inputString: String
) {
    private val inputLower = inputString.lowercase(Locale.getDefault())

    private var currentIndex = 0

    fun next(): Part? {
        if (currentIndex >= inputLower.length) return null

        val currentChar = inputLower[currentIndex]
        val part = when {
            currentChar.isDigit() -> Part.Number(parsePart { it.isDigit() }.toInt())
            else -> Part.String(parsePart { !it.isDigit() })
        }
        return part
    }

    private fun parsePart(predicate: (Char) -> Boolean): String {
        val result = StringBuilder()
        while (currentIndex < inputLower.length && predicate(inputLower[currentIndex])) {
            result.append(inputLower[currentIndex])
            currentIndex++
        }
        return result.toString()
    }
}