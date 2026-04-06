package com.haritejkr.pennypath.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyLineChart(data: List<Double>) {

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(1f, tween(800))
    }

    val maxValue = (data.maxOrNull() ?: 1.0).toFloat()
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Weekly Spending",
            style = MaterialTheme.typography.titleMedium,
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 32.dp)
                .padding(vertical = 8.dp)
                .pointerInput(data) {
                    detectTapGestures { offset ->

                        if (data.isEmpty()) return@detectTapGestures

                        val spacing = size.width / (data.size - 1).coerceAtLeast(1)

                        val rawIndex = (offset.x / spacing).toInt()

                        val index = rawIndex.coerceIn(0, data.size - 1)

                        selectedIndex = index
                    }
                }
        ) {

            val width = size.width
            val height = size.height

            val spacing = width / (data.size - 1)

            val points = data.mapIndexed { index, value ->
                val x = spacing * index
                val y = height - (value.toFloat() / maxValue * height * animatedProgress.value)
                Offset(x, y)
            }
            if (data.isEmpty()) return@Canvas

            val path = Path()

            points.forEachIndexed { i, point ->
                if (i == 0) {
                    path.moveTo(point.x, point.y)
                } else {
                    val prev = points[i - 1]

                    val controlX1 = (prev.x + point.x) / 2
                    val controlY1 = prev.y

                    val controlX2 = (prev.x + point.x) / 2
                    val controlY2 = point.y

                    path.cubicTo(
                        controlX1, controlY1,
                        controlX2, controlY2,
                        point.x, point.y
                    )
                }
            }

            drawPath(
                path = path,
                color = Color(0xFF42A5F5),
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )

            points.forEachIndexed { index, point ->

                val isSelected = index == selectedIndex

                drawCircle(
                    color = if (isSelected) Color(0xFF1E88E5) else Color.White,
                    radius = if (isSelected) 8f else 6f,
                    center = point
                )

                drawCircle(
                    color = Color(0xFF42A5F5),
                    radius = if (isSelected) 12f else 8f,
                    center = point,
                    style = Stroke(width = 3f)
                )
            }

            if (selectedIndex != -1) {

                val point = points[selectedIndex]
                val value = data[selectedIndex].toInt()

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "₹$value",
                        point.x,
                        point.y - 20,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 36f
                            textAlign = android.graphics.Paint.Align.CENTER
                            isFakeBoldText = true
                        }
                    )
                }
            }

            val fillPath = Path().apply {
                addPath(path)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF42A5F5).copy(alpha = 0.2f),
                        Color.Transparent
                    )
                )
            )
        }

        val spacing = 1f / 6f

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(day, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}