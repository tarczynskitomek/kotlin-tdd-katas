package it.tarczynski.kotlincata.bank

import java.math.BigDecimal

class Account(val id: String,
              val balance: BigDecimal = BigDecimal.ZERO,
              val allowedCreditAmount: BigDecimal = BigDecimal.ZERO) {

    init {
        check(id.isNotBlank())
        check(allowedCreditAmount >= BigDecimal.ZERO)
    }

    fun isWithdrawalPossible(withdrawAmount: BigDecimal): Boolean {
        check(withdrawAmount > BigDecimal.ZERO)
        return balance + allowedCreditAmount - withdrawAmount >= BigDecimal.ZERO
    }

    override fun equals(other: Any?): Boolean {
        return other is Account && other.id == this.id

    }

    override fun hashCode(): Int {
        return id.hashCode() * 31
    }
}
