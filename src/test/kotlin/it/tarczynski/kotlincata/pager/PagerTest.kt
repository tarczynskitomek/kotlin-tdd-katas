package it.tarczynski.kotlincata.pager

import org.junit.Assert.*
import org.junit.Test

class PagerTest {

    @Test(expected = IllegalArgumentException::class)
    fun createNewInstancePageSizeCannotBeNegativeOrZero() {
        Pager(-1, 1, 0)
        Pager(0, 1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createNewInstanceCurrentPageCannotBeNegative() {
        Pager(currentPage = -1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createNewInstanceNumOfItemsFoundCannotBeNegative() {
        Pager(numItems = -1)
    }

    @Test
    fun createNewInstanceWithDefaultValues() {
        val pager = Pager()
        assertEquals(pager.pageSize, 1)
        assertEquals(pager.currentPage, 0)
        assertEquals(pager.numItems, 0)
    }

    @Test
    fun totalPagesShouldBeCalculatedBasedOnTheNumberOfItemsAndPageSize() {
        val emptyPager = Pager()

        assertEquals(emptyPager.numPages, 0)

        val singlePagePager = Pager(numItems = 1)
        assertEquals(1, singlePagePager.numPages)

        val stillSingePagePager = Pager(pageSize = 50, numItems = 49)
        assertEquals(1, stillSingePagePager.numPages)

        val twoPagePager = Pager(numItems = 2)
        assertEquals(2, twoPagePager.numPages)

        val stillTwoPagePager = Pager(pageSize = 20, numItems = 21)
        assertEquals(2, stillTwoPagePager.numPages)

        val threePagePager = Pager(pageSize = 20, numItems = 47)
        assertEquals(3, threePagePager.numPages)
    }

    @Test
    fun onFirstPageHideThePreviousPageLink() {
        val pager = Pager()
        assertFalse(pager.showPreviousPageLink())
    }

    @Test
    fun onLastPageHideTheNextItemLink() {
        val pagerOne = Pager()
        assertFalse(pagerOne.showNextPageLink())
    }

    @Test
    fun nextPageLinkOnLastPageShouldBeNull() {
        val pager = Pager()
        val nextPageLinkParam = pager.nextPageLinkParam
        assertNull(nextPageLinkParam)
    }

    @Test
    fun nextPageLinkOnTheLastButOnePageShouldReturnLastPageNumber() {
        val pager = Pager(numItems = 2)
        assertEquals(1, pager.nextPageLinkParam)
    }
}