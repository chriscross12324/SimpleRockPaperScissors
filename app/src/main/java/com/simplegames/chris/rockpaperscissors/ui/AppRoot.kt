package com.simplegames.chris.rockpaperscissors.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplegames.chris.rockpaperscissors.AppViewModel
import com.simplegames.chris.rockpaperscissors.game.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simplegames.chris.rockpaperscissors.Overlay
import com.simplegames.chris.rockpaperscissors.ui.theme.AppTheme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.simplegames.chris.rockpaperscissors.ui.screens.AboutPanel
import com.simplegames.chris.rockpaperscissors.ui.screens.GameScreen
import com.simplegames.chris.rockpaperscissors.ui.screens.SettingsPanel
import com.simplegames.chris.rockpaperscissors.ui.theme.AnimatedGradientBackground
import com.simplegames.chris.rockpaperscissors.ui.theme.AppGradients
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun AppRoot(
    appViewModel: AppViewModel = viewModel(),
    gameViewModel: GameViewModel = viewModel(),
) {
    val settings by appViewModel.settings.collectAsStateWithLifecycle()
    val overlay by appViewModel.overlay.collectAsStateWithLifecycle()
    val game by gameViewModel.state.collectAsStateWithLifecycle()

    val current = settings
    if (current == null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        return
    }

    AppTheme(darkMode = current.darkMode) {
        val settingsSheet = rememberSheetState()
        val aboutSheet = rememberSheetState()
        val backdrop = rememberSheetState()

        LaunchedEffect(overlay) {
            launch { settingsSheet.animateTo(if (overlay == Overlay.Settings) 1f else 0f) }
            launch { aboutSheet.animateTo(if (overlay == Overlay.About) 1f else 0f) }
            launch { backdrop.animateTo(if (overlay != Overlay.None) 1f else 0f) }
            snapshotFlow { backdrop.progress }.collect { Log.i("AppRoot", "backdrop -> $it") }
        }

        BackHandler(enabled = overlay != Overlay.None) {
            appViewModel.back()
        }

        val settingsVisible by remember { derivedStateOf { settingsSheet.progress > 0.001f } }
        val aboutVisible by remember { derivedStateOf { aboutSheet.progress > 0.001f } }
        val blocking by remember { derivedStateOf { backdrop.progress > 0.001f } }

        Box(
            Modifier
                .background(Color.Black)
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .clip(
                    RoundedCornerShape(30.dp)
                ),
        ) {
            AnimatedGradientBackground(
                AppGradients.at(current.gradientIndex),
                Modifier.fillMaxSize()
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val p = backdrop.progress
                            val s = lerp(1f, 0.8f, p)
                            scaleX = s
                            scaleY = s
                            val radius = 0.dp.toPx() * p
                            renderEffect =
                                if (radius > 0.5f) BlurEffect(
                                    radius,
                                    radius,
                                    TileMode.Clamp
                                ) else null
                            alpha = lerp(1f, 0.0f, p)
                        }) {
                    GameScreen(
                        game,
                        current.vibration,
                        gameViewModel::play,
                        appViewModel::openSettings
                    )
                }

                if (blocking) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {},
                            )
                    )
                }

                if (settingsVisible) {
                    SettingsPanel(
                        settings = current,
                        onClose = appViewModel::closeOverlay,
                        onOpenAbout = appViewModel::openAbout,
                        onToggleVibration = appViewModel::toggleVibration,
                        onToggleDarkMode = appViewModel::toggleDarkMode,
                        onSelectGradient = appViewModel::setGradient,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .sheetOffset(settingsSheet)
                    )
                }

                if (aboutVisible) {
                    AboutPanel(
                        onBack = appViewModel::back,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .sheetOffset(aboutSheet),
                    )
                }
            }
        }
    }
}