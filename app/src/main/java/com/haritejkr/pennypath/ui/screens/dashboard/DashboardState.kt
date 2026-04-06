package com.haritejkr.pennypath.ui.screens.dashboard

data class DashboardState(
    val balance: Double = 0.0,
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val savings: Double = 0.0,
    val categoryBreakdown: Map<String, Double> = emptyMap(),
    val weeklySpending: List<Double> = emptyList()
)