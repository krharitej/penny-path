package com.haritejkr.pennypath

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haritejkr.pennypath.ui.theme.PennyPathTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PennyPathTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Splash()
                }
            }
        }
        val handler = Handler()
        val intent = Intent(this, MainActivity::class.java)
        handler.postDelayed({startActivity(intent)
            finish()}, 1500)
    }
}

@Composable
fun Splash() {
    var neverOffsetX by remember { mutableFloatStateOf(-80f) }
    var lostOffsetY by remember { mutableFloatStateOf(80f) }
    var logoRotation by remember { mutableFloatStateOf(-135f) }
    val neverAnimatable = remember { Animatable(neverOffsetX) }
    val lostAnimatable = remember { Animatable(lostOffsetY) }
    val logoAnimatable = remember { Animatable(logoRotation) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            neverAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(1500, easing = EaseOut)
            )
        }
        coroutineScope.launch {
            lostAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(1500, easing = EaseOut)
            )
        }
        coroutineScope.launch {
            logoAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(1500, easing = EaseOut)
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(
                "PENNY",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                modifier = Modifier.offset(x = neverAnimatable.value.dp)
            )
            Text(
                "PATH",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                modifier = Modifier.offset(y = lostAnimatable.value.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(92.dp)
                .graphicsLayer {
                    rotationZ = logoAnimatable.value
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PennyPathTheme {
        Splash()
    }
}