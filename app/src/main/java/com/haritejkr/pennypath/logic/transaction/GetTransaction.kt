package com.haritejkr.pennypath.logic.transaction

import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactions @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return repository.getTransactions()
    }
}

class GetTransactionById @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Long): Transaction {
        return repository.getTransactionById(id)
    }
}