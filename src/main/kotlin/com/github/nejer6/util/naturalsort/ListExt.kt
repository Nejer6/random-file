package com.github.nejer6.util.naturalsort

fun <T> List<T>.naturalSort(selector: (T) -> String): List<T> {
    val comparator = NaturalComparator()
    return this.sortedWith(Comparator { obj1, obj2 ->
        val str1 = selector(obj1)
        val str2 = selector(obj2)
        return@Comparator comparator.compare(str1, str2)
    })
}

fun List<String>.naturalSort(): List<String> {
    return this.naturalSort { it }
}
