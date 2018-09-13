package it.tarczynski.kotlincata.pager

import java.math.RoundingMode

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

    fun showPreviousPageLink(): Boolean = currentPage != 0

    fun showNextPageLink(): Boolean = currentPage < numPages

}