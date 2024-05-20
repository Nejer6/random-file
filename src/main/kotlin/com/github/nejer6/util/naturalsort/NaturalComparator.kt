package com.github.nejer6.util.naturalsort

class NaturalComparator : Comparator<String> {
    override fun compare(s1: String, s2: String): Int {
        val parser1 = StringParser(s1)
        val parser2 = StringParser(s2)

        while (true) {
            val part1 = parser1.next()
            val part2 = parser2.next()

            if (part1 == null && part2 == null) return 0
            if (part1 == null) return -1
            if (part2 == null) return 1

            if (part1 is Part.Number && part2 is Part.String) return  -1
            if (part1 is Part.String && part2 is Part.Number) return 1

            val compareResult = when {
                part1 is Part.Number && part2 is Part.Number -> part1.value.compareTo(part2.value)
                part1 is Part.String && part2 is Part.String -> part1.value.compareTo(part2.value)
                else -> 0
            }

            if (compareResult != 0) {
                return compareResult
            }
        }
    }
}