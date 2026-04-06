package com.haritejkr.pennypath.data.repository

import com.haritejkr.pennypath.data.local.dao.GoalDao
import com.haritejkr.pennypath.data.mapper.toDomain
import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.data.mapper.toEntity
import com.haritejkr.pennypath.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao
) : GoalRepository {

    override suspend fun addGoal(goal: Goal) {
        goalDao.insertGoal(goal.toEntity())
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.updateGoal(goal.toEntity())
    }

    override suspend fun deleteGoal(goal: Goal) {
        goalDao.deleteGoal(goal.toEntity())
    }

    override fun getGoals(): Flow<List<Goal>> {
        return goalDao.getGoals().map { list ->
            list.map { it.toDomain() }
        }
    }
}