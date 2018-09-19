package it.tarczynski.kotlincata.bank

import java.math.BigDecimal

class Account(val id: String,
              val balance: BigDecimal = BigDecimal.ZERO,
              val allowedCreditAmount: BigDecimal = BigDecimal.ZERO) {

    init {
        check(id.isNotBlank()) { "Account id cannot be empty or blank" }
        check(allowedCreditAmount >= BigDecimal.ZERO) { "Allowed credit limit cannot be negative" }
    }

    fun isWithdrawalPossible(withdrawAmount: BigDecimal): Boolean {
        check(withdrawAmount >= BigDecimal.ZERO) { "Withdrawal amount cannot be negative" }
        return balance + allowedCreditAmount - withdrawAmount >= BigDecimal.ZERO
    }

    override fun equals(other: Any?): Boolean {
        return other === this || other is Account && other.id == this.id

    }

    override fun hashCode(): Int {
        return id.hashCode() * 31
    }
}
