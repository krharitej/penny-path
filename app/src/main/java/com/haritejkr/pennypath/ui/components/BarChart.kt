package com.haritejkr.pennypath.ui.components

import android.graphics.Canvas
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.nio.file.Files.size
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius

@Composable
fun BarChart(data: List<Float>) {

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(700))
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {

        val max = data.maxOrNull() ?: 1f
        val barWidth = size.width / data.size

        data.forEachIndexed { i, value ->

            val height = (value / max) * size.height * progress.value

            drawRoundRect(
                color = Color(0xFF00BCD4),
                topLeft = Offset(i * barWidth, size.height - height),
                size = Size(barWidth * 0.5f, height),
                cornerRadius = CornerRadius(20f, 20f)
            )
        }
    }
}