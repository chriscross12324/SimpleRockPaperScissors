package com.simplegames.chris.rockpaperscissors.ui.components

import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.ui.rememberSheetState
import com.simplegames.chris.rockpaperscissors.ui.theme.LocalAppColours

@Composable
fun PanelScaffold(
    title: String,
    @DrawableRes icon: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colours = LocalAppColours.current
    val sheetPadding = 15.dp;
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(120.dp))
            LabelPill(title, icon = icon)
            Spacer(Modifier.height(5.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = sheetPadding),
                shape = RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                ),
                color = colours.panel,
                contentColor = colours.onPanel,
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 32.dp, top = 48.dp, bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = content,
                    )
                    Surface(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(5.dp)
                            .size(width = 65.dp, height = 40.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = colours.control,
                        contentColor = colours.onControl,
                    ) {
                        Icon(
                            painterResource(R.drawable.icon_arrow),
                            contentDescription = "Back",
                            modifier = Modifier.padding(6.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(sheetPadding))
        }
    }
}