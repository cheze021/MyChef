package com.example.mychef.utils.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.EggAlt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mychef.ui.theme.quickSandFamily
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedLoadingModal(loadingMessage: String) {
    val icons = listOf(
        Icons.Filled.RestaurantMenu,
        Icons.Filled.DinnerDining,
        Icons.Filled.EggAlt
    )

    var currentIconIndex by remember { mutableStateOf(0) }

    // Scale animation when icon change
    val scale = remember { Animatable(0.8f) }

    LaunchedEffect(currentIconIndex) {
        scale.snapTo(0.8f)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    }

    // Change icon in 1 sec
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentIconIndex = (currentIconIndex + 1) % icons.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFFFAF9)),
            contentAlignment = Alignment.Center
        ) {
            BubblesCanvas(modifier = Modifier.matchParentSize())

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icons[currentIconIndex],
                    contentDescription = "Animated Loading Icon",
                    tint = Color(0xFFE07061),
                    modifier = Modifier
                        .size(48.dp)
                        .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = loadingMessage,
                    color = Color(0xFF7A4E4E),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = quickSandFamily
                )
            }
        }
    }
}

@Composable
fun BubblesCanvas(modifier: Modifier = Modifier) {
    // We define a list of bubbles with initial position and speed
    val bubbles = remember {
        List(10) {
            Bubble(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 6 + 4,
                alpha = Random.nextFloat() * 0.5f + 0.3f,
                speed = Random.nextFloat() * 0.002f + 0.001f
            )
        }
    }

    // Animamos la posición vertical de cada burbuja
    val animatedBubbles = remember { bubbles.toMutableStateList() }

    LaunchedEffect(Unit) {
        while (true) {
            // Actualizamos la posición y alpha de cada burbuja
            for (i in animatedBubbles.indices) {
                val b = animatedBubbles[i]
                var newY = b.y - b.speed
                var newAlpha = b.alpha

                if (newY < 0f) {
                    newY = 1f
                    newAlpha = Random.nextFloat() * 0.5f + 0.3f
                }

                animatedBubbles[i] = b.copy(y = newY, alpha = newAlpha)
            }
            delay(16) // ~60 FPS
        }
    }

    Canvas(modifier = modifier) {
        val widthPx = size.width
        val heightPx = size.height

        for (bubble in animatedBubbles) {
            drawCircle(
                color = Color(0xFFE07061).copy(alpha = bubble.alpha),
                radius = bubble.radius,
                center = Offset(bubble.x * widthPx, bubble.y * heightPx)
            )
        }
    }
}