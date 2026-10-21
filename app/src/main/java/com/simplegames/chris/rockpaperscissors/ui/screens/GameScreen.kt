package com.simplegames.chris.rockpaperscissors.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.game.GameState
import com.simplegames.chris.rockpaperscissors.game.Hand
import com.simplegames.chris.rockpaperscissors.game.Outcome
import com.simplegames.chris.rockpaperscissors.game.Phase
import com.simplegames.chris.rockpaperscissors.ui.theme.LocalAppColours

@Composable
fun GameScreen(
    state: GameState,
    vibrationEnabled: Boolean,
    onPlay: (Hand) -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colours = LocalAppColours.current
    val haptics = LocalHapticFeedback.current
    val idle = state.phase is Phase.Idle

    val painters = Hand.entries.associateWith {
        painterResource(it.drawable())
    }

    Box(
        modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        MenuButton(onClick = onOpenSettings, modifier = Modifier.align(Alignment.TopStart))
        AnimatedVisibility(
            visible = idle,
            modifier = Modifier.align(Alignment.TopCenter),
            enter = slideInVertically { -it * 2 },
            exit = slideOutVertically { -it * 2 },
        ) {
            ResultPill(state.result.label())
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .background(colours.pill, RoundedCornerShape(75.dp))
                .border(6.dp, colours.control, RoundedCornerShape(75.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painters.getValue(state.cpuImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(36.dp),
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(90.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Hand.entries.forEach { hand ->
                val visible = when (val phase = state.phase) {
                    Phase.Idle -> true
                    is Phase.Playing -> phase.choice == hand
                }

                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = visible,
                        enter = slideInVertically { it * 2 },
                        exit = slideOutVertically { it * 2 },
                    ) {
                        Surface(
                            onClick = {
                                if (idle) {
                                    if (vibrationEnabled) haptics.performHapticFeedback(
                                        HapticFeedbackType.LongPress
                                    )
                                    onPlay(hand)
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(25.dp),
                            color = colours.control,
                        ) {
                            Image(
                                painter = painters.getValue(hand),
                                contentDescription = hand.label,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colours = LocalAppColours.current
    Surface(
        onClick = onClick,
        modifier = Modifier
            .size(width = 65.dp, height = 45.dp),
        shape = RoundedCornerShape(50),
        color = colours.pill,
        contentColor = colours.onPill,
    ) {
        Icon(
            painterResource(R.drawable.icon_main_menu),
            contentDescription = "Back",
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
private fun ResultPill(text: String) {
    val colours = LocalAppColours.current
    Surface(
        modifier = Modifier
            .height(45.dp),
        shape = RoundedCornerShape(50),
        color = colours.pill,
        contentColor = colours.onPill,
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 28.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private fun Outcome?.label() = when (this) {
    Outcome.Win -> "You Win"
    Outcome.Lose -> "You Lose"
    Outcome.Draw -> "Draw"
    null -> "Choose"
}

@DrawableRes
private fun Hand.drawable() = when (this) {
    Hand.Rock -> R.drawable.icon_rock
    Hand.Paper -> R.drawable.icon_paper
    Hand.Scissors -> R.drawable.icon_scissors
}