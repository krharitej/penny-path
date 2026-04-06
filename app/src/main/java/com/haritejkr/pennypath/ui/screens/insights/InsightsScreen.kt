package com.haritejkr.pennypath.ui.screens.insights

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haritejkr.pennypath.ui.components.BarChart
import com.haritejkr.pennypath.ui.components.DualLineChart
import com.haritejkr.pennypath.ui.components.GlassCard
import com.haritejkr.pennypath.ui.components.PieChart

@Composable
fun InsightsScreen() {

    val viewModel: InsightsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val totalThisWeek = state.weeklyThis.sum()
    val totalLastWeek = state.weeklyLast.sum()

    val isIncrease = totalThisWeek > totalLastWeek

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        // WEEKLY COMPARISON
        GlassCard {
            Column {

                Text(
                    text = "Weekly Comparison",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                DualLineChart(
                    thisWeek = state.weeklyThis,
                    lastWeek = state.weeklyLast
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = if (isIncrease)
                        "Spending increased by ₹${(totalThisWeek - totalLastWeek).toInt()} compared to last week 📈"
                    else
                        "Spending reduced by ₹${(totalLastWeek - totalThisWeek).toInt()} compared to last week 📉",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // CATEGORY BREAKDOWN
        GlassCard {
            Column {

                Text(
                    text = "Top Category",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    PieChart(
                        data = state.categoryData,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Highest spending on ${state.topCategory}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "₹${state.topAmount.toInt()} spent in this category",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // MONTHLY TREND
        GlassCard {
            Column {

                Text(
                    text = "Monthly Trend",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                BarChart(data = state.monthly)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Track your spending trend over last months",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // SMART INSIGHTS (MOST IMPORTANT)
        GlassCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(
                    text = "Insights",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("• Your highest spending category is ${state.topCategory}")

                Text("• You spent ₹${state.topAmount.toInt()} in this category")

                Text(
                    if (isIncrease)
                        "• Your expenses are rising — consider reducing unnecessary spending"
                    else
                        "• Great job! You are reducing your expenses"
                )

                Text(
                    "• Average daily expense: ₹${(totalThisWeek / 7).toInt()}"
                )

                if (state.topAmount > totalThisWeek * 0.5f) {
                    Text("• More than 50% spending is in one category ⚠️")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}