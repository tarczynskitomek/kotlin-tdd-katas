package it.tarczynski.kotlincata.pager

import java.math.RoundingMode

private const val FIRST_PAGE_INDEX = 0
/**
 * Some assumptions I've made while coding this class:
 * - the pages are indexed from zero, thus the displayed value of the current page should be incremented by 1
 * - the class is ignorant about any "link" / "url" creation logic returning only the first / last / next / previous page index.
 *   Thus that logic should be a concert of a client of Pager class.
 * - pager is empty when there are no items. Single page pager is a valid, non empty pager
 */
class Pager(val pageSize: Int = 1, val currentPage: Int = 0, val numItems: Int = 0) {

    init {
        if (pageSize < 1) throw IllegalArgumentException("Page size has to be a positive number")
        if (currentPage < 0) throw IllegalArgumentException("Current page has to be a non-negative number")
        if (numItems < 0) throw IllegalArgumentException("Number of items has to be a non-negative number")
    }

    val numPages: Int
        get() = numItems.toBigDecimal()
                .divide(pageSize.toBigDecimal())
                .setScale(0, RoundingMode.UP)
                .toInt()

    val nextPageLinkParam: Int?
        get() = if (currentPage == numPages) null else currentPage + 1

    val previousPageLinkParam: Int?
        get() = if (currentPage == 0) null else currentPage - 1

    val lastPageLinkParam: Int = numPages

    val firstPageLinkParam: Int = FIRST_PAGE_INDEX

    fun showPreviousPageLink(): Boolean = currentPage != 0

    fun showNextPageLink(): Boolean = currentPage < numPages

    fun isEmpty(): Boolean = numItems == 0

}