package com.haritejkr.pennypath.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @PrimaryKey val id: Long,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double
)