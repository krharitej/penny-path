package com.haritejkr.pennypath.repository

import com.haritejkr.pennypath.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {

    fun getGoals(): Flow<List<Goal>>

    suspend fun addGoal(goal: Goal)

    suspend fun deleteGoal(goal: Goal)

    suspend fun updateGoal(goal: Goal)
}