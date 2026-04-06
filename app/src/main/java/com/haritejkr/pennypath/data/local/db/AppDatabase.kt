package com.haritejkr.pennypath.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haritejkr.pennypath.data.local.dao.TransactionDao
import com.haritejkr.pennypath.data.local.dao.GoalDao
import com.haritejkr.pennypath.data.local.entity.TransactionEntity
import com.haritejkr.pennypath.data.local.entity.Goal

@Database(
    entities = [
        TransactionEntity::class,
        Goal::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    abstract fun goalDao(): GoalDao
}