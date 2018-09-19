package it.tarczynski.kotlincata.bank

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class TransactionServiceSpec {

    private val transactionService: TransactionService = DefaultTransactionService(mutableSetOf())

    @Before
    fun setup() {
        transactionService.clearAccounts()
    }

    @Test
    fun forUnknownTransactionParticipantsShouldThrowAnException() {
        val unknown = Account("unknown")
        val ok = Account("ok")
        transactionService.addAccounts(ok)

        assertThatThrownBy { transactionService.transfer(unknown, ok, BigDecimal.ONE) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Unknown payment participant")

        assertThatThrownBy { transactionService.transfer(ok, unknown, BigDecimal.ONE) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Unknown payment participant")
    }

    @Test
    fun forSamePayerAndPayeeShouldThrowAnException() {
        val payer = Account("1", BigDecimal.TEN)
        val payee = Account("1", BigDecimal.TEN)
        transactionService.addAccounts(payer, payee)

        assertThatThrownBy { transactionService.transfer(payer, payee, BigDecimal.ONE) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage("Payment participants have to be different")
    }

    @Test
    fun forZeroAmountShouldThrowAnException() {
        val payer = Account("1", BigDecimal.TEN)
        val payee = Account("2", BigDecimal.TEN)
        transactionService.addAccounts(payee, payer)

        assertThatThrownBy { transactionService.transfer(payer, payee, BigDecimal.ZERO) }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun forPayerWithInsufficientFounds() {
        val payer = Account("1", BigDecimal.ZERO)
        val payee = Account("2", BigDecimal.TEN)
        transactionService.addAccounts(payer, payee)

        assertThatThrownBy { transactionService.transfer(payer, payee, BigDecimal.ONE) }
                .isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun forCorrectTransferDataShouldReturnCorrectTransactionObject() {
        val payer = Account("1", BigDecimal.TEN)
        val payee = Account("2", BigDecimal.TEN)

        transactionService.addAccounts(payer, payee)
        val transfer = transactionService.transfer(payer, payee, BigDecimal.ONE)

        assertEquals(payer, transfer.payer)
        assertEquals(payee, transfer.payee)
        assertEquals(BigDecimal.ONE, transfer.amount)

        assertEquals(BigDecimal(9), transfer.payer.balance)
        assertEquals(BigDecimal(11), transfer.payee.balance)

        assertEquals(payer.allowedCreditAmount, transfer.payer.allowedCreditAmount)
        assertEquals(payee.allowedCreditAmount, transfer.payee.allowedCreditAmount)

    }

    @Test
    fun shouldUpdateAccountInformation() {
        val old = Account("1")
        transactionService.addAccounts(old)

        val newBalance = Account("1", BigDecimal.TEN)
        val balanceResult = transactionService.updateAccount(newBalance)

        assertTrue(balanceResult)
        assertEquals(BigDecimal.TEN, transactionService.findAccount("1")!!.balance)

        val newCreditLimit = Account("1", allowedCreditAmount = BigDecimal.ONE)
        val creditLimitResult = transactionService.updateAccount(newCreditLimit)

        assertTrue(creditLimitResult)
        assertEquals(BigDecimal.ONE, transactionService.findAccount("1")!!.allowedCreditAmount)
    }

    @Test
    fun forNoTransactionShouldReturnAnEmptyList() {
        assertTrue(transactionService.getTransactionHistory(Account("1")).isEmpty())
    }

    @Test
    fun successTransferAddsTransaction() {
        val payer = Account("1", BigDecimal.TEN)
        val payee = Account("2", BigDecimal.TEN)

        transactionService.addAccounts(payer, payee)
        transactionService.transfer(payer, payee, BigDecimal.ONE)

        assertEquals(1, transactionService.transactionHistory.size)
    }

    @Test
    fun shouldReturnANonEmptyTransactionList() {
        val payer = Account("1", BigDecimal(3))
        val payee = Account("2", BigDecimal.TEN)
        val anotherPayer = Account("3", BigDecimal.TEN)

        transactionService.addAccounts(payer, payee, anotherPayer)

        transactionService.transfer(payer, payee, BigDecimal.ONE)
        transactionService.transfer(payer, payee, BigDecimal.ONE)
        transactionService.transfer(payer, payee, BigDecimal.ONE)
        transactionService.transfer(payee, payer, BigDecimal.ONE)
        transactionService.transfer(anotherPayer, payee, BigDecimal.ONE)

        assertEquals(5, transactionService.transactionHistory.size)
        assertEquals(4, transactionService.getTransactionHistory(payer).size)
        assertEquals(5, transactionService.getTransactionHistory(payee).size)
        assertEquals(1, transactionService.getTransactionHistory(anotherPayer).size)
    }

    @Test
    fun shouldReturnANonEmptyListIfTwoUsersHadTransactions() {
        val payer = Account("1", BigDecimal(3))
        val payee = Account("2", BigDecimal.TEN)
        val anotherPayer = Account("3", BigDecimal.TEN)

        transactionService.addAccounts(payer, payee, anotherPayer)

        transactionService.transfer(payer, payee, BigDecimal.ONE)
        transactionService.transfer(payee, payer, BigDecimal.ONE)
        transactionService.transfer(anotherPayer, payee, BigDecimal.ONE)

        assertEquals(2, transactionService.getTransactionHistory(payee, payer).size)
        assertEquals(2, transactionService.getTransactionHistory(payer, payee).size)
        assertEquals(1, transactionService.getTransactionHistory(anotherPayer, payee).size)
        assertTrue(transactionService.getTransactionHistory(payer, anotherPayer).isEmpty())
    }


}