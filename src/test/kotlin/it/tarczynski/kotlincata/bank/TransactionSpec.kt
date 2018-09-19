package it.tarczynski.kotlincata.bank

import org.assertj.core.api.Assertions
import org.junit.Test
import java.math.BigDecimal

class TransactionSpec {

    @Test
    fun forEmptyTransactionIdShouldThrowAnException() {
        Assertions.assertThatThrownBy { Transaction(Account("1"), Account("2"), BigDecimal.ONE, "") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Transaction id cannot be null or blank")

        Assertions.assertThatThrownBy { Transaction(Account("1"), Account("2"), BigDecimal.ONE, "   ") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Transaction id cannot be null or blank")
    }

    @Test
    fun forZeroAmountShouldThrowAnException() {
        Assertions.assertThatThrownBy { Transaction(Account("1"), Account("2"), BigDecimal.ZERO, "a") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Transaction amount has to be positive")

        Assertions.assertThatThrownBy { Transaction(Account("1"), Account("2"), BigDecimal.valueOf(-1), "a") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Transaction amount has to be positive")
    }

    @Test
    fun forSameAccountsShouldThrowAnException() {
        Assertions.assertThatThrownBy { Transaction(Account("1"), Account("1"), BigDecimal.ONE, "a") }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Transaction sides have to be different")
    }

}