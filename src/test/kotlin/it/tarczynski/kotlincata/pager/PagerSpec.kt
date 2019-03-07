package it.tarczynski.kotlincata.pager

import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

class PagerSpec {

    @Test
    fun shouldCreateAnEmptyPagerInstance() {
        val pager = Pager(1, 20)
        assertNotNull(pager)
        assertEquals(1, pager.itemCount)
        assertEquals(20, pager.pageSize)
    }

    @Test
    fun shouldCreatePagerWithCorrectNumberOfPages() {
        createAndAssert(1, 10, 10)
        createAndAssert(2, 11, 10)
    }

    @Test
    fun shouldThrowAnExceptionWhenNumberOfPagesIsZero() {
        Assertions
                .assertThatThrownBy { Pager(1, 0) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Page size has to be positive")
    }

    @Test
    fun shouldThrowAnExceptionWhenNumberOfPagesIsNegative() {
        Assertions
                .assertThatThrownBy { Pager(10, -1) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Page size has to be positive")
    }

    @Test
    fun shouldThrowAnExceptionWhenNumberOfItemsIsZero() {
        Assertions
                .assertThatThrownBy { Pager(0, 10) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Pager cannot be created for zero or negative number of items")
    }

    @Test
    fun prevButtonShouldNotBeVisibleForFirstPage() {
        assertFalse(Pager(1, 1, 1).prevButtonVisible)
    }

    @Test
    fun prevButtonShouldBeVisibleOnSecondPage() {
        assertTrue(Pager(2, 1, 2).prevButtonVisible)
    }

    @Test
    fun nextButtonShouldNotBeVisibleOnTheLastPage() {
        assertFalse(Pager(1, 1, 1).nextButtonVisible)
    }

    @Test
    fun nextButtonShouldBeVisibleOnTheOneButLastPage() {
        assertTrue(Pager(29, 20, 1).nextButtonVisible)
    }

    @Test
    fun shouldReturnLabelsToPrint() {
        assertLabelsToPrint(30, 10, 2, arrayOf("<", "1", "2", "3", ">"))
        assertLabelsToPrint(20, 10, 1, arrayOf("1", "2", ">"))
        assertLabelsToPrint(30, 10, 3, arrayOf("<", "2", "3"))
        assertLabelsToPrint(10, 10, 1, arrayOf("1"))
        assertLabelsToPrint(100, 10, 4, arrayOf("<", "3", "4", "5", ">"))

        assertLabelsToPrint(100, 10, 1, arrayOf("1", "2", "...", "10", ">"))
    }

    private fun createAndAssert(expected: Int, numItems: Int, pageSize: Int) {
        val pager = Pager(numItems, pageSize)
        assertEquals(expected, pager.numberOfPages)
    }

    private fun assertLabelsToPrint(numberOfItems: Int, pageSize: Int, currentPage: Int, expected: Array<String>) {
        assertEquals(expected, Pager(numberOfItems, pageSize, currentPage).getPagesToPrint.map { it.label }.toTypedArray())
    }

    private fun assertUrlsToPrint() {
        assertEquals(expected)
    }

}