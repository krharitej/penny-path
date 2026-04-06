package com.haritejkr.pennypath.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haritejkr.pennypath.data.local.entity.FilterState
import com.haritejkr.pennypath.logic.transaction.AddTransaction
import com.haritejkr.pennypath.logic.transaction.DeleteTransaction
import com.haritejkr.pennypath.logic.transaction.GetTransactionById
import com.haritejkr.pennypath.logic.transaction.GetTransactions
import com.haritejkr.pennypath.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val addTransaction: AddTransaction,
    private val getTransactions: GetTransactions,
    private val deleteTransaction: DeleteTransaction,
    private val getTransactionById: GetTransactionById
) : ViewModel() {

    private val allTransactions = getTransactions()
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    val transactions = combine(allTransactions, filterState) { list, filter ->
        println("FILTER APPLIED: $filter")

        list.filter { transaction ->
            println("Checking: ${transaction.category} vs ${filter.category}")

            val matchesType =
                filter.type == null || transaction.type == filter.type

            val matchesCategory =
                filter.category == null || transaction.category.trim().equals(filter.category.trim(), ignoreCase = true)

            val min = minOf(filter.minAmount, filter.maxAmount)
            val max = maxOf(filter.minAmount, filter.maxAmount)

            val matchesAmount =
                transaction.amount in min..max

            matchesType && matchesCategory && matchesAmount
        }
    }

    fun addTransaction(
        amount: Double,
        type: String,
        category: String,
        note: String,
        date: Long
    ) {
        val transaction = Transaction(
            amount = amount,
            type = type,
            category = category,
            date = date,
            note = note
        )

        viewModelScope.launch {
            addTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            deleteTransaction.invoke(transaction)
        }
    }

    fun getTransaction(id: Long, onResult: (Transaction) -> Unit) {
        viewModelScope.launch {
            val transaction = getTransactionById(id)
            onResult(transaction)
        }
    }

    fun updateTransaction(
        id: Long,
        amount: Double,
        type: String,
        category: String,
        note: String,
        date: Long
    ) {
        val transaction = Transaction(
            id = id,
            amount = amount,
            type = type,
            category = category,
            date = date,
            note = note
        )

        viewModelScope.launch {
            addTransaction(transaction)
        }
    }

    fun applyFilters(
        type: String?,
        category: String?,
        min: Float,
        max: Float
    ) {
        _filterState.value = FilterState(type, category, min, max)
    }

    fun clearFilters() {
        _filterState.value = FilterState()
    }
}