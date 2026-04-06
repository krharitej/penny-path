package com.haritejkr.pennypath.ui.screens.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.haritejkr.pennypath.ui.components.GlassCard
import com.haritejkr.pennypath.ui.navigation.Routes

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun GoalScreen(
    navController: NavHostController
) {

    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Routes.Goals.route)
    }
    val viewModel: GoalsViewModel = hiltViewModel(parentEntry)

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (state.goals.isEmpty()) {
                Text("No goals yet. Start saving 🚀")
            }

            state.goals.forEach { goal ->

                var showAddDialog by remember { mutableStateOf(false) }

                val progress =
                    (goal.savedAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)

                GlassCard {
                    Column {

                        Text(goal.name, style = MaterialTheme.typography.titleMedium)

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("₹${goal.savedAmount.toInt()} saved")
                            Text("${(progress * 100).toInt()}%")
                        }

                        Text("Target: ₹${goal.targetAmount.toInt()}")

                        Spacer(Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            Button(
                                onClick = { showAddDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("+ Add")
                            }

                            OutlinedButton(
                                onClick = { viewModel.removeGoal(goal.id) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }

                if (showAddDialog) {

                    var amount by remember { mutableStateOf("") }

                    AlertDialog(
                        onDismissRequest = { showAddDialog = false },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.addMoney(
                                    goal.id,
                                    amount.toDoubleOrNull() ?: 0.0
                                )
                                showAddDialog = false
                            }) {
                                Text("Add")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showAddDialog = false }) {
                                Text("Cancel")
                            }
                        },
                        title = { Text("Add Money") },
                        text = {
                            OutlinedTextField(
                                value = amount,
                                onValueChange = { amount = it },
                                label = { Text("Amount") }
                            )
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("add_goal") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+")
        }
    }
}