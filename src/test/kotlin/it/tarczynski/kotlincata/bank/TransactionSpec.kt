package it.tarczynski.kotlincata.bank

import org.junit.Test
import java.lang.IllegalStateException
import java.math.BigDecimal

class TransactionSpec {

    @Test(expected = IllegalStateException::class)
    fun forEmptyTransactionIdShouldThrowAnException() {
        Transaction(Account("1"), Account("2"), BigDecimal.ONE, "")
    }

    @Test(expected = IllegalStateException::class)
    fun forBlankTransactionIdShouldThrowAnException() {
        Transaction(Account("1"), Account("2"), BigDecimal.TEN, "    ")
    }

    @Test(expected = IllegalStateException::class)
    fun forZeroAmountShouldThrowAnException() {
        Transaction(Account("1"), Account("2"), BigDecimal.ZERO, "a")
    }

    @Test(expected = IllegalStateException::class)
    fun forNegativeAmountShouldThrowAnException() {
        Transaction(Account("1"), Account("2"), BigDecimal.valueOf(-1), "a")
    }

    @Test(expected = IllegalStateException::class)
    fun forSameAccountsShouldThrowAnException() {
        Transaction(Account("1"), Account("1"), BigDecimal.ONE, "a")
    }
}