package it.tarczynski.kotlincata.bank

import java.math.BigDecimal

interface TransactionService {
    val transactionHistory: List<Transaction>

    fun transfer(payer: Account, payee: Account, amount: BigDecimal): Transaction
    fun addAccounts(vararg accounts: Account): Boolean
    fun clearAccounts()
    fun updateAccount(account: Account): Boolean
    fun findAccount(id: String): Account?
    fun getTransactionHistory(account: Account): List<Transaction>
    fun getTransactionHistory(first: Account, second: Account): List<Transaction>
}