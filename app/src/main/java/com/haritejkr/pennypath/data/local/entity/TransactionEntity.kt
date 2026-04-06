package com.haritejkr.pennypath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val type: String, // "income" or "expense"
    val category: String,
    val date: Long,
    val note: String
)