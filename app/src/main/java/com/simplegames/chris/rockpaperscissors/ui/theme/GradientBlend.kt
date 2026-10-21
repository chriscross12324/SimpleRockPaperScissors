package com.simplegames.chris.rockpaperscissors.ui.theme

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

private const val SAMPLES = 13

private fun List<Color>.sampleAt(t: Float): Color {
    if (size == 1) {
        return first()
    }

    val pos = t.coerceIn(0f, 1f) * (size - 1)
    val i = pos.toInt().coerceAtMost(size - 2)
    return lerp(this[i], this[i + 1], pos - 1)
}

private fun List<Color>.resample(count: Int) = List(count) {
    sampleAt(it / (count - 1f))
}

@Stable
class GradientBlendState(initial: List<Color>) {
    private var from by mutableStateOf(initial.resample(SAMPLES))
    private var to by mutableStateOf(from)
    private var fraction by mutableFloatStateOf(1f)

    fun current(): List<Color> {
        return if (fraction >= 1f) to else List(SAMPLES) {
            lerp(from[it], to[it], fraction)
        }
    }

    suspend fun blendTo(target: List<Color>, durationMs: Int) {
        val newTo = target.resample(SAMPLES)
        if (newTo == to) return

        Snapshot.withMutableSnapshot {
            from = current()
            to = newTo
            fraction = 0f
        }

        animate(
            0f,
            1f,
            animationSpec = tween(durationMs, easing = FastOutSlowInEasing),
        ) { v, _ ->
            fraction = v
        }
    }
}

@Composable
fun AnimatedGradientBackground(
    gradient: GradientDef,
    modifier: Modifier = Modifier,
    durationMs: Int = 250,
    content: @Composable BoxScope.() -> Unit,
) {
    val blend = remember { GradientBlendState(gradient.colours) }
    LaunchedEffect(gradient) { blend.blendTo(gradient.colours, durationMs) }

    Box(
        modifier = modifier.drawBehind {
            drawRect(
                Brush.linearGradient(
                    blend.current(),
                    Offset.Zero,
                    Offset(size.width, size.height),
                )
            )
        },
        content = content
    )
}