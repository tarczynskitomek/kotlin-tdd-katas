package it.tarczynski.kotlincata.bank

import java.math.BigDecimal
import java.util.*

class DefaultTransactionService(private val accounts: MutableSet<Account>) : TransactionService {
    private val _transactionHistory = mutableListOf<Transaction>()

    override val transactionHistory: List<Transaction>
        get() = _transactionHistory

    override fun transfer(payer: Account, payee: Account, amount: BigDecimal): Transaction {
        validateTransactionArguments(payer, payee, amount)

        val transactionPayer = Account(payer.id, payer.balance - amount, payer.allowedCreditAmount)
        val transactionPayee = Account(payee.id, payee.balance + amount, payee.allowedCreditAmount)

        accounts.remove(payer)
        accounts.remove(payee)

        accounts.add(transactionPayer)
        accounts.add(transactionPayee)

        val transaction = Transaction(transactionPayer, transactionPayee, amount, UUID.randomUUID().toString())
        _transactionHistory.add(transaction)

        return transaction
    }

    override fun updateAccount(account: Account): Boolean {
        accounts.remove(account)
        return accounts.add(account)
    }

    private fun validateTransactionArguments(payer: Account, payee: Account, amount: BigDecimal) {
        check(accounts.contains(payer) && accounts.contains(payee)) { "Unknown payment participant" }
        check(payer != payee) { "Payment participants have to be different" }
        check(amount > BigDecimal.ZERO) { "Transmitted amount has to be positive" }
        check(payer.isWithdrawalPossible(amount)) { "Payer account has insufficient founds" }
    }

    override fun addAccounts(vararg accounts: Account) = this.accounts.addAll(accounts)

    override fun findAccount(id: String): Account? {
        return accounts.find { it.id == id }
    }

    override fun clearAccounts() = accounts.clear()

    override fun getTransactionHistory(account: Account): List<Transaction> {
        return transactionHistory.filter { it.payer == account || it.payee == account }
    }

    override fun getTransactionHistory(first: Account, second: Account): List<Transaction> {
        return getTransactionHistory(first).intersect(getTransactionHistory(second)).toList()
    }
}