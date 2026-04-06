package com.haritejkr.pennypath.logic.goals

import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoal @Inject constructor(
    private val repository: GoalRepository
) {
    operator fun invoke(): Flow<List<Goal>> {
        return repository.getGoals()
    }
}