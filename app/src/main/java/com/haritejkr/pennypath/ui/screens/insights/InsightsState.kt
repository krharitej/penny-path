package com.haritejkr.pennypath.ui.screens.insights

data class InsightsState(
    val weeklyThis: List<Float> = emptyList(),
    val weeklyLast: List<Float> = emptyList(),
    val categoryData: Map<String, Double> = emptyMap(),
    val monthly: List<Float> = emptyList(),
    val topCategory: String = "",
    val topAmount: Double = 0.0
)