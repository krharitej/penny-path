package com.haritejkr.pennypath.model

data class Goal(
    val id: Long,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val deadline: Long = System.currentTimeMillis()
)