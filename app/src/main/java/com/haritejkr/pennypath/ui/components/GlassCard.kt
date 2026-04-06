package com.haritejkr.pennypath.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val isDark = isSystemInDarkTheme()   //  IMPORTANT

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.95f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    val elevation by animateDpAsState(
        targetValue = if (visible) 12.dp else 4.dp,
        label = ""
    )

    val infinite = rememberInfiniteTransition(label = "")

    val shift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    //  FIXED GRADIENT (DARK MODE SAFE)
    val brush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF2A2A2A),
                Color(0xFF3D3D3D),
                Color(0xFF2A2A2A)
            ),
            start = Offset(shift, shift),
            end = Offset(shift + 300f, shift + 300f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.9f),
                Color(0xFFB4E9FF).copy(alpha = 0.3f),
                Color.White.copy(alpha = 0.9f)
            ),
            start = Offset(shift, shift),
            end = Offset(shift + 300f, shift + 300f)
        )
    }

    //  FIXED CARD COLOR
    val cardColor = if (isDark) {
        Color(0xFF1E1E1E)   // dark solid
    } else {
        Color.White.copy(alpha = 0.85f)
    }

    val glowColor = if (isDark) {
        Color(0xFF00E5FF)   // neon cyan
    } else {
        Color(0xFF42A5F5)   // soft blue
    }

    val glowAlpha by infinite.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(0.dp, RoundedCornerShape(28.dp)) // remove default
            .then(
                Modifier
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = glowColor.copy(alpha = glowAlpha),
                        spotColor = glowColor.copy(alpha = glowAlpha + 0.2f)
                    )
            )
    ) {

        Box(
            modifier = Modifier
                .background(brush)
                .padding(16.dp)
        ) {
            content()
        }
    }
}