package com.haritejkr.pennypath.repository

import com.haritejkr.pennypath.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun addTransaction(transaction: Transaction)

    fun getTransactions(): Flow<List<Transaction>>

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun getTransactionById(id: Long): Transaction
}