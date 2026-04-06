package com.haritejkr.pennypath.ui.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haritejkr.pennypath.logic.transaction.GetTransactions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val getTransactions: GetTransactions
) : ViewModel() {

    private val _state = MutableStateFlow(InsightsState())
    val state: StateFlow<InsightsState> = _state

    init {
        calculateInsights()
    }

    private fun calculateInsights() {
        viewModelScope.launch {
            getTransactions().collect { list ->

                val expenses = list.filter { it.type == "expense" }

                val weeklyThis = MutableList(7) { 0f }
                val weeklyLast = MutableList(7) { 0f }

                expenses.forEach {
                    val day = (it.date / (1000 * 60 * 60 * 24) % 7).toInt()
                    weeklyThis[day] += it.amount.toFloat()
                }

                val grouped = expenses.groupBy { it.category }
                val categoryData = grouped.mapValues { it.value.sumOf { t -> t.amount } }

                val top = categoryData.maxByOrNull { it.value }

                val monthly = MutableList(6) { 0f }
                expenses.forEach {
                    val month = (it.date / (1000L * 60 * 60 * 24 * 30) % 6).toInt()
                    monthly[month] += it.amount.toFloat()
                }

                _state.value = InsightsState(
                    weeklyThis = weeklyThis,
                    weeklyLast = weeklyLast,
                    categoryData = categoryData,
                    monthly = monthly,
                    topCategory = top?.key ?: "",
                    topAmount = top?.value ?: 0.0
                )
            }
        }
    }
}