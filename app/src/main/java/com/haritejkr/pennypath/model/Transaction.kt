package com.haritejkr.pennypath.model

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val type: String,
    val category: String,
    val date: Long,
    val note: String
)