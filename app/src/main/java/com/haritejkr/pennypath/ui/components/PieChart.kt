package com.haritejkr.pennypath.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    data: Map<String, Double>,
    modifier: Modifier = Modifier
) {

    var selectedIndex by remember { mutableStateOf(-1) }
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(900)
        )
    }

    val total = data.values.sum()

    val colors = listOf(
        Color(0xFF42A5F5), // soft blue
        Color(0xFF7E57C2), // modern purple
        Color(0xFF26C6DA), // cyan teal
        Color(0xFFEC407A), // pink accent
        Color(0xFFFF6B6B), // soft coral red
        Color(0xFFFFA726), // rich amber
        Color(0xFFFFD54F), // warm yellow
        Color(0xFF66BB6A) // fresh green
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Chart
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            val progress by animateFloatAsState(
                targetValue = 1f,
                animationSpec = tween(1200),
                label = ""
            )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->

                            var startAngle = -90f

                            data.entries.forEachIndexed { index, entry ->

                                val sweep = ((entry.value / total) * 360f * animatedProgress.value).toFloat()
                                val endAngle = startAngle + sweep

                                val angle = Math.toDegrees(
                                    atan2(
                                        offset.y - size.height / 2,
                                        offset.x - size.width / 2
                                    ).toDouble()
                                ).toFloat() + 90f

                                val normalized = if (angle < 0) angle + 360 else angle

                                if (normalized in startAngle..endAngle) {
                                    selectedIndex = index
                                }

                                startAngle += sweep
                            }
                        }
                    }
            ) {

                var startAngle = -90f
                val strokeWidth = size.minDimension / 6

                data.entries.forEachIndexed { index, entry ->

                    val sweep =
                        (entry.value / total * 360f * animatedProgress.value).toFloat()

                    val baseColor = colors[index % colors.size]
                    val isSelected = index == selectedIndex

                    val finalStroke = if (isSelected) strokeWidth + 10 else strokeWidth

                    drawArc(
                        color = baseColor.copy(
                            red = baseColor.red * 0.95f,
                            green = baseColor.green * 0.95f,
                            blue = baseColor.blue * 0.95f
                        ),
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = finalStroke + 6)
                    )

                    drawArc(
                        color = baseColor.copy(alpha = 0.95f),
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = finalStroke)
                    )

                    startAngle += sweep
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Legend
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.entries.forEachIndexed { index, entry ->
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                colors[index % colors.size],
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val percentage = (entry.value / total * 100).toInt()
                    Text(
                        text = "${entry.key}  ${percentage}%",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}