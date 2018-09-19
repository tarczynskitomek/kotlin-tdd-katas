package it.tarczynski.kotlincata.bank

import java.math.BigDecimal

data class Transaction(val payer: Account,
                  val payee: Account,
                  val amount: BigDecimal,
                  val uid: String) {

    init {
        check(uid.isNotBlank()) { "Transaction id cannot be null or blank" }
        check(amount > BigDecimal.ZERO) { "Transaction amount has to be positive" }
        check(payer != payee) { "Transaction sides have to be different" }
    }
}