package com.haritejkr.pennypath.ui.screens.dashboard

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.haritejkr.pennypath.ui.components.GlassCard
import com.haritejkr.pennypath.ui.components.PieChart
import com.haritejkr.pennypath.ui.components.WeeklyLineChart
import com.haritejkr.pennypath.ui.navigation.Routes

@Composable
fun HomeScreenDashboard(navController: NavHostController) {

    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val weeklyData by viewModel.weeklySpending.collectAsState(initial = emptyList())
    val goals by viewModel.topGoals.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Text(
                    text = "Current Balance",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                val animatedBalance by animateFloatAsState(
                    targetValue = state.balance.toFloat(),
                    label = ""
                )

                Text(
                    text = "₹${animatedBalance.toInt()}",
                    style = MaterialTheme.typography.headlineMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Income", color = Color.Gray)
                        }

                        val animatedIncome by animateFloatAsState(
                            targetValue = state.income.toFloat(),
                            label = ""
                        )

                        Text(
                            text = "₹${animatedIncome.toInt()}",
                            color = Color(0xFF2E7D32)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = null,
                                tint = Color(0xFFC62828),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Expense", color = Color.Gray)
                        }

                        val animatedExpense by animateFloatAsState(
                            targetValue = state.expense.toFloat(),
                            label = ""
                        )

                        Text(
                            text = "₹${animatedExpense.toInt()}",
                            color = Color(0xFFC62828)
                        )
                    }
                }

                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Savings", color = Color.Gray)
                    }

                    val animatedSavings by animateFloatAsState(
                        targetValue = state.savings.toFloat(),
                        label = ""
                    )

                    Text(
                        text = "₹${animatedSavings.toInt()}",
                        color = Color(0xFFFF9800)
                    )
                }
            }
        }

        var pressed by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
            targetValue = if (pressed) 0.97f else 1f,
            label = ""
        )

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            pressed = true
                            tryAwaitRelease()
                            pressed = false
                        },
                        onTap = {
                            navController.navigate(Routes.Goals.route)
                        }
                    )
                }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Text(
                    text = "Goals Progress 🎯",
                    style = MaterialTheme.typography.titleMedium
                )

                if (goals.isEmpty()) {
                    Text(
                        text = "No goals yet. Start saving 🚀",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                goals.forEach { goal ->

                    val progress =
                        (goal.savedAmount / goal.targetAmount)
                            .toFloat()
                            .coerceIn(0f, 1f)

                    Column {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(goal.name)
                            Text("${(progress * 100).toInt()}%")
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        var startAnimation by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            startAnimation = true
                        }

                        val animatedProgress by animateFloatAsState(
                            targetValue = if (startAnimation) progress else 0f,
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = FastOutSlowInEasing
                            ),
                            label = ""
                        )

                        LinearProgressIndicator(
                            progress = animatedProgress,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "₹${goal.savedAmount.toInt()} / ₹${goal.targetAmount.toInt()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Spending Breakdown",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                PieChart(
                    data = state.categoryBreakdown,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            WeeklyLineChart(data = weeklyData)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}