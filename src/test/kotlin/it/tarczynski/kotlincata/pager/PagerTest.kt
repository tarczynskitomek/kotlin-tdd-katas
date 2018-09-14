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

    @Test
    fun onFirstPagePreviousPageLinkShouldBeNull() {
        val pager = Pager(50, 0, 100)
        assertNull(pager.previousPageLinkParam)
    }

    @Test
    fun onSecondPagePreviousPageLinkParamShouldReturnZero() {
        val pager = Pager(20, 1, 42)
        assertEquals(0, pager.previousPageLinkParam)
    }

    @Test
    fun lastPageLinkParamShouldReturnTheNumberEqualToNumberOfPages() {
        val pagerOne = Pager()
        assertEquals(pagerOne.numPages, pagerOne.lastPageLinkParam)

        val pagerTwo = Pager(20, 4, 242)
        assertEquals(pagerTwo.numPages, pagerTwo.lastPageLinkParam)
    }

    @Test
    fun firstPageLinkParamShouldAlwaysReturnZeroNoMatterThePagerSetup() {
        val empty = Pager()
        assertEquals(0, empty.firstPageLinkParam)

        val notEmpty = Pager(10, 0, 22)
        assertEquals(0, notEmpty.firstPageLinkParam)
    }

    @Test
    fun inEmptyPagerFirstPageEqualsLastPageAll() {
        val empty = Pager()

        assertTrue(empty.isEmpty())
        assertEquals(0, empty.numPages)
        assertEquals(empty.firstPageLinkParam, empty.lastPageLinkParam)
        assertNull(empty.nextPageLinkParam)
        assertNull(empty.previousPageLinkParam)
    }

    @Test
    fun isEmptyShouldReturnFalseInNonEmptyPager() {
        val singleItemPager = Pager(1, 0, 1)
        assertFalse(singleItemPager.isEmpty())

        val multiItemPager = Pager(10, 20, 1024)
        assertFalse(multiItemPager.isEmpty())
    }

}