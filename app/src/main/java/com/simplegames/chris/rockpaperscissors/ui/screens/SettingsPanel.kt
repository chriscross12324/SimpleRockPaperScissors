package com.simplegames.chris.rockpaperscissors.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.data.AppSettings
import com.simplegames.chris.rockpaperscissors.ui.components.PanelScaffold
import com.simplegames.chris.rockpaperscissors.ui.components.PillButton
import com.simplegames.chris.rockpaperscissors.ui.theme.AppGradients
import com.simplegames.chris.rockpaperscissors.ui.theme.GradientDef

@Composable
fun SettingsPanel(
    settings: AppSettings,
    onClose: () -> Unit,
    onOpenAbout: () -> Unit,
    onToggleVibration: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onSelectGradient: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val gradientCount = AppGradients.all.size

    PanelScaffold("Settings", R.drawable.icon_settings, onClose, modifier) {
        SectionTitle("Options")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PillButton(
                    "Vibrate: ${if (settings.vibration) "On" else "Off"}", onToggleVibration,
                    Modifier.weight(1f)
                )
                PillButton(
                    "Dark Mode: ${if (settings.darkMode) "On" else "Off"}", onToggleDarkMode,
                    Modifier.weight(1f)
                )
            }

        SectionTitle("Backgrounds")
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            AppGradients.all.forEachIndexed { index, gradient ->
                GradientOption(gradient, selected = index == settings.gradientIndex) {
                    onSelectGradient(index)
                }
            }
        }

        SectionTitle("Debug")
        SectionTitle("Miscellaneous")
        PillButton("App Info", onOpenAbout, Modifier.fillMaxWidth())
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 8.dp),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun GradientOption(
    gradient: GradientDef,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Brush.linearGradient(gradient.colours))
            .then(if (selected) Modifier.border(3.dp, Color.White, RoundedCornerShape(25.dp)) else Modifier)
            .clickable(onClick = onClick), contentAlignment = Alignment.Center,
    ) {
        Surface(shape = RoundedCornerShape(10.dp), color = Color.Black.copy(alpha = 0.55f), contentColor = Color.White) {
            Text(gradient.name, Modifier.padding(horizontal = 10.dp, vertical = 2.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
