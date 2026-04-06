package com.haritejkr.pennypath.di

import android.content.Context
import androidx.room.Room
import com.haritejkr.pennypath.data.local.dao.GoalDao
import com.haritejkr.pennypath.data.local.db.AppDatabase
import com.haritejkr.pennypath.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "penny_path_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao {
        return db.transactionDao()
    }

    @Provides
    fun provideGoalDao(db: AppDatabase): GoalDao {
        return db.goalDao()
    }
}