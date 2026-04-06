package com.haritejkr.pennypath.data.local.entity

data class FilterState(
    val type: String? = null,
    val category: String? = null,
    val minAmount: Float = 0f,
    val maxAmount: Float = Float.MAX_VALUE
)