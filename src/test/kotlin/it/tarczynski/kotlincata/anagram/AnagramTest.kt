package it.tarczynski.kotlincata.anagram

import org.junit.Assert.assertEquals
import org.junit.Test

class AnagramTest {

    @Test
    fun forEmptyStringShouldReturnAsetWithAnEmptyString() {
        assertEquals(setOf(""), anagram(""))
    }

    @Test
    fun forASingleLetterShouldReturnASetWithTheInput() {
        assertEquals(setOf("a"), anagram("a"))
    }

    @Test
    fun forADoubleLetterInputShouldReturnASetWithTwoTwoLetterElements() {
        assertEquals(setOf("ab", "ba"), anagram("ab"))
        assertEquals(setOf("ab", "ba"), anagram("ba"))
    }
}