package com.haritejkr.pennypath.ui.screens.goals

import com.haritejkr.pennypath.model.Goal

data class GoalState(

    val goals: List<Goal> = emptyList(),

    // Derived / UI values
    val totalSaved: Double = 0.0,
    val totalTarget: Double = 0.0,

    val isLoading: Boolean = false,
    val error: String? = null
)