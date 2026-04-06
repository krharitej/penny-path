package com.haritejkr.pennypath.di

import com.haritejkr.pennypath.data.repository.GoalRepositoryImpl
import com.haritejkr.pennypath.data.repository.TransactionRepositoryImpl
import com.haritejkr.pennypath.repository.GoalRepository
import com.haritejkr.pennypath.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(
        impl: GoalRepositoryImpl
    ): GoalRepository
}