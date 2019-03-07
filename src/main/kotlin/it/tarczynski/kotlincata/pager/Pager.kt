package it.tarczynski.kotlincata.pager


class Pager(val itemCount: Int,
            val pageSize: Int,
            val currentPage: Int = 1) {

    init {
        if (itemCount < 1) throw IllegalArgumentException("Pager cannot be created for zero or negative number of items")
        if (pageSize < 1) throw IllegalArgumentException("Page size has to be positive")
    }

    val numberOfPages: Int
        get() {
            val result = itemCount / pageSize
            return if (itemCount % pageSize == 0) return result else result + 1
        }

    val prevButtonVisible: Boolean
        get() = currentPage > 1

    val nextButtonVisible: Boolean
        get() = currentPage < numberOfPages

    val getPagesToPrint: Array<Link>
        get() {
            return with(mutableListOf<Link>()) {
                if (prevButtonVisible) add(Link("<"))
                if (currentPage > 2) add(Link("1"))
                if (currentPage > 3) add(Link("..."))

                addAll(getMiddlePages())

                if (currentPage < numberOfPages - 2) add(Link("..."))
                if (currentPage < numberOfPages - 1) add(Link(numberOfPages.toString()))
                if (nextButtonVisible) add(Link(">"))

                toTypedArray()
            }
        }

    private fun getMiddlePages(): Array<Link> {
        if (numberOfPages == 1) return arrayOf(Link("1"))
        if (currentPage == 1) return arrayOf(Link("1"), Link("2"))
        if (currentPage == numberOfPages) return arrayOf(Link((numberOfPages - 1).toString()), Link(numberOfPages.toString()))

        return arrayOf(Link((currentPage - 1).toString()), Link(currentPage.toString()), Link((currentPage + 1).toString()))
    }


}

data class Link(val label: String)
