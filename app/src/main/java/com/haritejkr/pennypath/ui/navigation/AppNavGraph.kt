package com.haritejkr.pennypath.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.ui.screens.transaction.TransactionScreen
import com.haritejkr.pennypath.ui.screens.dashboard.HomeScreenDashboard
import com.haritejkr.pennypath.ui.screens.goals.AddGoalScreen
import com.haritejkr.pennypath.ui.screens.goals.GoalScreen
import com.haritejkr.pennypath.ui.screens.insights.InsightsScreen
import com.haritejkr.pennypath.ui.screens.transaction.TransactionViewModel
import com.haritejkr.pennypath.ui.screens.transaction.UpdateTransactionScreen

@Composable
fun AppNavGraph(navController: NavHostController,
                viewModel: TransactionViewModel,
                isSelectionMode: Boolean,
                isEditMode: Boolean,
                selectedItems: MutableList<Transaction>,
                onSelectToggle: (Transaction, Boolean) -> Unit,
                onEditClick: (Transaction) -> Unit) {

    NavHost(
        navController = navController,
        startDestination = Routes.Dashboard.route
    ) {

        composable(Routes.Dashboard.route) {
            HomeScreenDashboard()
        }

        composable(Routes.Transactions.route) {
            TransactionScreen(
                viewModel = viewModel,
                isSelectionMode = isSelectionMode,
                selectedItems = selectedItems,
                onSelectToggle = { transaction, selected ->
                    if (selected) selectedItems.add(transaction)
                    else selectedItems.remove(transaction)
                },
                onEditClick = onEditClick,
                isEditMode = isEditMode
            )
        }

        composable(Routes.Insights.route) {
            InsightsScreen()
        }

        composable(Routes.Goals.route) {
            GoalScreen(navController = navController)
        }

        composable(Routes.AddTransaction.route) {
            UpdateTransactionScreen(navController = navController)
        }

        composable(Routes.UpdateTransaction.route) {
            val id = it.arguments?.getString("id")?.toLongOrNull()
            UpdateTransactionScreen(transactionId = id, navController = navController)
        }

        composable(Routes.AddGoal.route) {
            AddGoalScreen(navController = navController)
        }
    }
}