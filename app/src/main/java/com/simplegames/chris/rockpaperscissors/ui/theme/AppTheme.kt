package com.simplegames.chris.rockpaperscissors.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColours(
    val panel: Color, val onPanel: Color,
    val control: Color, val onControl: Color,
    val controlActive: Color, val onControlActive: Color,
    val pill: Color, val onPill: Color,
)

private val LightAppColours = AppColours(
    panel = Color(0xD9FFFFFF),
    onPanel = Color(0xFF1E2B1E),
    control = Color(0xFFFFFFFF),
    onControl = Color(0xFF1E2B1E),
    controlActive = Color(0xFF2E7D32),
    onControlActive = Color(0xFFFFFFFF),
    pill = Color(0xB3FFFFFF),
    onPill = Color(0xFF1E2B1E),
)

private val DarkAppColours = AppColours(
    panel = Color(0xC0141C12),
    onPanel = Color(0xFFF2F6F0),
    control = Color(0xFF161F12),
    onControl = Color(0xFFF2F6F0),
    controlActive = Color(0xFF7BD66F),
    onControlActive = Color(0xFF0B1A08),
    pill = Color(0xB3101810),
    onPill = Color(0xFFFFFFFF),
)

val LocalAppColours = compositionLocalOf { DarkAppColours }

@Composable
private fun animatedColour(target: Color): Color =
    animateColorAsState(target, tween(450), label="appColour").value

@Composable
private fun AppColours.animated() = AppColours(
    panel = animatedColour(panel),
    onPanel = animatedColour(onPanel),
    control = animatedColour(control),
    onControl = animatedColour(onControl),
    controlActive = animatedColour(controlActive),
    onControlActive = animatedColour(onControlActive),
    pill = animatedColour(pill),
    onPill = animatedColour(onPill),
)

@Composable
fun AppTheme(darkMode: Boolean, content: @Composable () -> Unit) {
    val colours = (if (darkMode) DarkAppColours else LightAppColours).animated()
    MaterialTheme(colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()) {
        CompositionLocalProvider(LocalAppColours provides colours, content = content)
    }
}
