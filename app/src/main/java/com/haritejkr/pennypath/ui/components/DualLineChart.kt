package com.haritejkr.pennypath.ui.components

import android.graphics.Canvas
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import java.nio.file.Files.size
import androidx.compose.ui.graphics.Path
import kotlin.io.path.moveTo
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun DualLineChart(
    thisWeek: List<Float>,
    lastWeek: List<Float>
) {

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(800))
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {

        val max = (thisWeek + lastWeek).maxOrNull() ?: 1f
        val stepX = size.width / (thisWeek.size - 1)

        fun drawSmoothLine(data: List<Float>, color: Color) {
            val path = Path()

            data.forEachIndexed { i, value ->

                val x = i * stepX
                val y = size.height - (value / max * size.height * progress.value)

                if (i == 0) path.moveTo(x, y)
                else {
                    val prevX = (i - 1) * stepX
                    val prevY = size.height - (data[i - 1] / max * size.height * progress.value)

                    val midX = (prevX + x) / 2
                    path.quadraticBezierTo(prevX, prevY, midX, (prevY + y) / 2)
                }
            }

            drawPath(path, color, style = Stroke(width = 5f, cap = StrokeCap.Round))

            // Dots
            data.forEachIndexed { i, value ->
                val x = i * stepX
                val y = size.height - (value / max * size.height * progress.value)

                drawCircle(color, 6f, Offset(x, y))
            }
        }

        drawSmoothLine(thisWeek, Color(0xFF00BCD4))
        drawSmoothLine(lastWeek, Color(0xFFFF9800))
    }
}