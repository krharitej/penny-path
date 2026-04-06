package com.haritejkr.pennypath.logic.transaction

import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.repository.TransactionRepository
import javax.inject.Inject

class AddTransaction @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.addTransaction(transaction)
    }
}