package it.tarczynski.kotlincata.bank

import java.math.BigDecimal

class Transaction(val payer: Account,
                  val payee: Account,
                  val amount: BigDecimal,
                  val uid: String) {

    init {
        check(uid.isNotBlank())
        check(amount > BigDecimal.ZERO)
        check(payer != payee)
    }

    override fun equals(other: Any?): Boolean {
        return other is Transaction && other.uid == this.uid
    }

    override fun hashCode(): Int {
        return this.uid.hashCode() * 31
    }
}