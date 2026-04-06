package com.haritejkr.pennypath.ui.screens.goals

import androidx.lifecycle.ViewModel
import com.haritejkr.pennypath.model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(GoalState())
    val state: StateFlow<GoalState> = _state

    fun addGoal(goal: Goal) {

        val updatedGoals = _state.value.goals + goal

        _state.value = _state.value.copy(
            goals = updatedGoals,
            totalSaved = updatedGoals.sumOf { it.savedAmount },
            totalTarget = updatedGoals.sumOf { it.targetAmount }
        )
    }

    fun removeGoal(goalId: Long) {
        val updatedGoals = _state.value.goals.filterNot { it.id == goalId }

        _state.value = _state.value.copy(
            goals = updatedGoals,
            totalSaved = updatedGoals.sumOf { it.savedAmount },
            totalTarget = updatedGoals.sumOf { it.targetAmount }
        )
    }

    fun addMoney(goalId: Long, amount: Double) {

        val updatedGoals = _state.value.goals.map {
            if (it.id == goalId) {
                it.copy(savedAmount = it.savedAmount + amount)
            } else it
        }

        _state.value = _state.value.copy(
            goals = updatedGoals,
            totalSaved = updatedGoals.sumOf { it.savedAmount },
            totalTarget = updatedGoals.sumOf { it.targetAmount }
        )
    }
}