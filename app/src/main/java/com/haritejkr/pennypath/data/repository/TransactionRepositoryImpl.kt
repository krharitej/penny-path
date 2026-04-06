package com.haritejkr.pennypath.data.repository

import com.haritejkr.pennypath.data.local.dao.TransactionDao
import com.haritejkr.pennypath.data.mapper.toEntity
import com.haritejkr.pennypath.data.mapper.toModel
import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { list ->
            list.map { it.toModel() }
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.delete(transaction.toEntity())
    }

    override suspend fun getTransactionById(id: Long): Transaction {
        return dao.getTransactionById(id).toModel()
    }
}