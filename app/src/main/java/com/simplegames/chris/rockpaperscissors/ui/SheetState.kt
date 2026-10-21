package com.simplegames.chris.rockpaperscissors.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private val SheetSpring = spring<Float>(dampingRatio = 0.85f, stiffness = 300f)

@Stable
class SheetState {
    private val anim = Animatable(0f)
    val progress: Float get() = anim.value

    suspend fun animateTo(target: Float) {
        anim.animateTo(target, tween(durationMillis = 450, easing = FastOutSlowInEasing))
    }

    suspend fun animateToSprint(target: Float) {
        anim.animateTo(target, SheetSpring)
    }

    suspend fun snapTo(value: Float) {
        anim.snapTo(value)
    }
}

@Composable
fun rememberSheetState() = remember {
    SheetState()
}

fun Modifier.sheetOffset(sheet: SheetState): Modifier = graphicsLayer {
    translationY = (1f - sheet.progress).coerceAtLeast(0f) * size.height
}