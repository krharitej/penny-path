package com.haritejkr.pennypath.ui.navigation

sealed class Routes(val route: String) {
    object Dashboard : Routes("dashboard")
    object Transactions : Routes("transactions")
    object Insights : Routes("insights")
    object Goals : Routes("goals")
    object AddTransaction : Routes("add_transaction")
    object UpdateTransaction : Routes("update_transaction/{id}")
    object AddGoal : Routes("add_goal")
}