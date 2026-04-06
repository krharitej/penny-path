package com.haritejkr.pennypath.logic.goals

import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.repository.GoalRepository
import javax.inject.Inject

class AddGoal @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goal: Goal) {
        repository.addGoal(goal)
    }
}