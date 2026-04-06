package com.haritejkr.pennypath.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haritejkr.pennypath.logic.transaction.GetTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTransactions: GetTransactions
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        viewModelScope.launch {
            getTransactions().collect { list ->

                val income = list.filter { it.type == "income" }.sumOf { it.amount }
                val expense = list.filter { it.type == "expense" }.sumOf { it.amount }
                val savings = list.filter { it.type == "savings" }.sumOf { it.amount }

                val balance = income - expense - savings

                val categoryData = list
                    .filter { it.type == "expense" }
                    .groupBy { it.category }
                    .mapValues { entry ->
                        entry.value.sumOf { it.amount }
                    }

                _state.value = DashboardState(
                    balance = balance,
                    income = income,
                    expense = expense,
                    savings = savings,
                    categoryBreakdown = categoryData
                )
            }
        }
    }

    private val allTransactions = getTransactions()
    val weeklySpending = allTransactions.map { list ->

        val calendar = Calendar.getInstance()
        val result = MutableList(7) { 0.0 }

        list.forEach { transaction ->
            if (transaction.type == "expense") {

                calendar.timeInMillis = transaction.date

                val day = calendar.get(Calendar.DAY_OF_WEEK)

                val index = when (day) {
                    Calendar.MONDAY -> 0
                    Calendar.TUESDAY -> 1
                    Calendar.WEDNESDAY -> 2
                    Calendar.THURSDAY -> 3
                    Calendar.FRIDAY -> 4
                    Calendar.SATURDAY -> 5
                    Calendar.SUNDAY -> 6
                    else -> 0
                }

                result[index] += transaction.amount
            }
        }

        result
    }
}