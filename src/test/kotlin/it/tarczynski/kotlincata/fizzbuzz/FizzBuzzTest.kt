package it.tarczynski.kotlincata.fizzbuzz

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.function.IntUnaryOperator
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

class FizzBuzzTest {

    @Test
    fun isBuzzShouldReturnTrueForAnyNumberDivisableByThree() {
        val allBuzz = createFizzBuzzCheckStream(3, IntUnaryOperator { it + 3 }) { isBuzz(it) }.allMatch { it }
        assertTrue(allBuzz)
    }

    @Test
    fun forHundredValuesOneThirdShouldBeBuzz() {
        val oneThirdBuzz = createFizzBuzzCheckStream(1, IntUnaryOperator { it.inc() }) { isBuzz(it) }
                .filter { it }
                .collect(Collectors.toList())
        assertEquals(33, oneThirdBuzz.size)
    }

    @Test
    fun isFizzShouldReturnTrueForAnyNumberDivisableByFive() {
        val allFizz = createFizzBuzzCheckStream(5, IntUnaryOperator { it + 5 }) { isFizz(it) }.allMatch { it }
        assertTrue(allFizz)
    }

    @Test
    fun forHundredValuesOneFifthShouldBeFizz() {
        val oneFifthFizz = createFizzBuzzCheckStream(1, IntUnaryOperator { it.inc() }) { isFizz(it) }
                .filter { it }
                .collect(Collectors.toList())
        assertEquals(20, oneFifthFizz.size)
    }

    @Test
    fun isFizzBuzzShouldReturnTrueForAnyNumberDivisableBothByThreeAndFive() {
        val allFizzBuzz = createFizzBuzzCheckStream(15, IntUnaryOperator { it + 15 }) { isFizzBuzz(it) }
                .allMatch { it }
        assertTrue(allFizzBuzz)
    }

    @Test
    fun toFizzBuzzStringWhenGivenANumberDivisibleByThreeButNotByFiveShouldReturnBuzz() {
        val allBuzz = IntStream.of(3, 6, 9, 12, 18, 21, 24)
                .mapToObj { toFizzBuzzString(it) }
                .allMatch { it == "Buzz" }
        assertTrue(allBuzz)
    }

    @Test
    fun toFizzBuzzStringWhenGivenANumberDivisibleByFiveByNotByThreeShouldReturnFizz() {
        val allFizz = IntStream.of(5, 10, 20, 25, 35, 40)
                .mapToObj { toFizzBuzzString(it) }
                .allMatch { it == "Fizz" }
        assertTrue(allFizz)
    }

    @Test
    fun toFizzBuzzStringWhenGivenANumberNotDivisibleByThreeOrFiveShouldReturnItsStringRepresentation() {
        assertEquals("2", toFizzBuzzString(2))
        assertEquals("1", toFizzBuzzString(1))
        assertEquals("4", toFizzBuzzString(4))
        assertEquals("44", toFizzBuzzString(44))
        assertEquals("62", toFizzBuzzString(62))
        assertEquals("98", toFizzBuzzString(98))
    }

    @Test
    fun toFizzBuzzStringWhenGivenANumberDivisibleByBothThreeAndFiveShouldReturnFizzBuzz() {
        val allFizzBuzz = IntStream.of(15, 30, 45, 60, 75, 90)
                .mapToObj { toFizzBuzzString(it) }
                .allMatch { it == "FizzBuzz" }
        assertTrue(allFizzBuzz)
    }

    private fun createFizzBuzzCheckStream(seed: Int,
                                          generator: IntUnaryOperator,
                                          predicate: (Int) -> Boolean): Stream<Boolean> {
        return IntStream.iterate(seed) { generator.applyAsInt(it) }
                .limit(100)
                .mapToObj { predicate.invoke(it) }
    }
}
