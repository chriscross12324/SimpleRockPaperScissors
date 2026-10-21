package com.simplegames.chris.rockpaperscissors.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simplegames.chris.rockpaperscissors.BuildConfig
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.ui.components.PanelScaffold

// Done later
@Composable
fun AboutPanel(onBack: () -> Unit, modifier: Modifier = Modifier) {
    val changes = stringArrayResource(R.array.about_changes).map { "• $it" }

    PanelScaffold("About", R.drawable.icon_info, onBack, modifier) {
        InfoSection("App Name", stringResource(R.string.app_name))
        InfoSection("Version", BuildConfig.VERSION_NAME)
        InfoSection("Changes", changes)
        //InfoSection("Build Date", BuildConfig.BUILD_DATE)
        InfoSection("Build Type", BuildConfig.BUILD_TYPE)
        InfoSection("App Developer", stringResource(R.string.developer_name))
    }
}

@Composable
private fun InfoSection(title: String, value: String) = InfoSection(title, listOf(value))

@Composable
private fun InfoSection(title: String, lines: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        lines.forEach {
            Text(
                it,
                color = LocalContentColor.current.copy(alpha = 0.75f),
                textAlign = TextAlign.Center,
            )
        }
    }
}
