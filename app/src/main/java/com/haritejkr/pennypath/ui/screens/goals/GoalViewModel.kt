package com.haritejkr.pennypath.ui.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val repository: GoalRepository
) : ViewModel() {

    //  Flow from DB
    val goals = repository.getGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    //  ADD GOAL
    fun addGoal(goal: Goal) {
        viewModelScope.launch {
            repository.addGoal(goal)
        }
    }

    //  DELETE GOAL
    fun removeGoal(goal: Goal) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
        }
    }

    //  UPDATE GOAL (generic)
    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            repository.updateGoal(goal)
        }
    }

    //  ADD MONEY (FIXED )
    fun addMoney(goal: Goal, amount: Double) {
        viewModelScope.launch {
            val updatedGoal = goal.copy(
                savedAmount = goal.savedAmount + amount
            )
            repository.updateGoal(updatedGoal)
        }
    }
}