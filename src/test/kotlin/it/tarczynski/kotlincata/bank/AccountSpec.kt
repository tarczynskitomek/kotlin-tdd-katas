package it.tarczynski.kotlincata.bank

import junit.framework.Assert.*
import org.assertj.core.api.Assertions
import org.junit.Test
import java.lang.IllegalStateException
import java.math.BigDecimal

class AccountSpec {

    @Test
    fun equalsTest() {
        val account = Account("a")

        assertEquals(account, account)
        assertTrue(account === account)
        assertEquals(account, Account("a"))

        assertFalse(account === Account("a"))
        assertFalse(account == null)
        assertFalse(account == Any())
    }

    @Test
    fun forEmptyOrBlankClientIdShouldThrowAnException() {
        Assertions
                .assertThatThrownBy { Account("") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Account id cannot be empty or blank")

        Assertions
                .assertThatThrownBy { Account("    ") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Account id cannot be empty or blank")
    }

    @Test
    fun forNegativeAllowedCreditAmountShouldThrowAnException() {
        Assertions.assertThatThrownBy { Account(id = "1", allowedCreditAmount = BigDecimal(-1)) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Allowed credit limit cannot be negative")
    }

    @Test
    fun forNegativeWithdrawalAmountShouldThrowAnException() {
        Assertions.assertThatThrownBy { Account("1", BigDecimal.ONE, BigDecimal.ONE).isWithdrawalPossible(BigDecimal(-1)) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Withdrawal amount cannot be negative")
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