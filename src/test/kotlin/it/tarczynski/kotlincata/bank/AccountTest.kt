package it.tarczynski.kotlincata.bank

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class AccountTest {

    @Test(expected = IllegalStateException::class)
    fun forEmptyClientIdShouldThrowAnException() {
        Account("")
    }

    @Test(expected = IllegalStateException::class)
    fun forBlankClientIdShouldThrowAnException() {
        Account("    ")
    }

    @Test(expected = IllegalStateException::class)
    fun forNegativeAllowedCreditAmountShouldThrowAnException() {
        Account(id = "1", allowedCreditAmount = BigDecimal(-1))
    }

    @Test(expected = IllegalStateException::class)
    fun forNegativeWithdrawalAmountShouldThrowAnException() {
        Account("1", BigDecimal.ONE, BigDecimal.ONE).isWithdrawalPossible(BigDecimal(-1))
    }

    @Test
    fun isWithdrawalPossibleShouldAlwaysReturnFalse() {
        assertFalse(Account("1", BigDecimal.ZERO, BigDecimal.ZERO).isWithdrawalPossible(BigDecimal.ONE))
        assertFalse(Account("1", BigDecimal.ZERO, BigDecimal.ONE).isWithdrawalPossible(BigDecimal.TEN))
        assertFalse(Account("1", BigDecimal(9), BigDecimal.ZERO).isWithdrawalPossible(BigDecimal.TEN))
        assertFalse(Account("1", BigDecimal(8), BigDecimal.ONE).isWithdrawalPossible(BigDecimal.TEN))
        assertFalse(Account("1", BigDecimal(-8), BigDecimal(9)).isWithdrawalPossible(BigDecimal.TEN))
    }

    @Test
    fun isWithdrawalPossibleShouldAlwaysReturnTrue() {
        assertTrue(Account("1", BigDecimal.ONE, BigDecimal.ZERO).isWithdrawalPossible(BigDecimal.ONE))
        assertTrue(Account("1", BigDecimal.ZERO, BigDecimal.ONE).isWithdrawalPossible(BigDecimal.ONE))
        assertTrue(Account("1", BigDecimal.ONE, BigDecimal.ONE).isWithdrawalPossible(BigDecimal.ONE))
        assertTrue(Account("1", BigDecimal(-10), BigDecimal(20)).isWithdrawalPossible(BigDecimal.TEN))
    }

}