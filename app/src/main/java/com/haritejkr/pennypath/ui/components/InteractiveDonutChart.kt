package com.haritejkr.pennypath.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun InteractiveDonutChart(
    data: Map<String, Double>,
    centerText: String
) {

    Box(contentAlignment = Alignment.Center) {

        PieChart(data = data)

        Text(
            text = centerText,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
    }
}