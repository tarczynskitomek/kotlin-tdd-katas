package it.tarczynski.kotlincata.anagram

fun anagram(word: String) : Set<String> {
    if (word.length < 2) return setOf(word)
    var result = setOf<String>()

    for (i in 0 until word.length) {
        val letter = word.elementAt(i)
        val rest = word.removeRange(i, i + 1)
        for (subanagram in anagram(rest)) {
            result = result.plus((letter + subanagram))
        }
    }

    return result
}

