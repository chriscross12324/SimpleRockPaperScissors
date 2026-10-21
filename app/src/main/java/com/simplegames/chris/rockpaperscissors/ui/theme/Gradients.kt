package com.simplegames.chris.rockpaperscissors.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class GradientDef(val name: String, val colours: List<Color>)

object AppGradients {
    val all: List<GradientDef> = listOf(
        GradientDef("Snowfall", listOf(Color(0xFFEBF6FB), Color(0xFFAFBCD1))),
        GradientDef("Faded Red", listOf(Color(0xFFED9694), Color(0xFFEF3B36))),
        GradientDef("Sunset", listOf(Color(0xFFC31432), Color(0xFF360B23))),
        GradientDef("Hot Lava", listOf(Color(0xFFF6C359), Color(0xFFDF3E2B))),
        GradientDef("Cotton Candy", listOf(Color(0xFFEB836E), Color(0xFF8920E6))),
        GradientDef("Sunshine", listOf(Color(0xFFf6d365), Color(0xFFfda085))),
        GradientDef("Traffic Lights", listOf(Color(0xFFC6FFDD), Color(0xFFFFD575), Color(0xFFF45459))),
        GradientDef("Green Grass", listOf(Color(0xFFD0FF85), Color(0xFF6AD575))),
        GradientDef("Coniferous", listOf(Color(0xFF2C975C), Color(0xFF084D11))),
        GradientDef("Tropical Ocean", listOf(Color(0xFF80FFB9), Color(0xFF6CC8F3))),
        GradientDef("Sunny Depths", listOf(Color(0xFF4df7b9), Color(0xFF172650))),
        GradientDef("Orbit", listOf(Color(0xFF141E30), Color(0xFF19293C), Color(0xFF1E3546), Color(0xFF2B5F64))),
        GradientDef("Juicy Pomegranate", listOf(Color(0xFFf072aa), Color(0xFF753392))),
        GradientDef("Amethyst", listOf(Color(0xFFCE78EF), Color(0xFF9D53BA), Color(0xFF964FB2), Color(0xFF8742A2), Color(0xFF3D216B))),
        GradientDef("Darkness", listOf(Color(0xFF383838), Color(0xFF131313))),
        GradientDef("Lollipop", listOf(Color(0xFFD38EC7), Color(0xFF679FD6), Color(0xFF89CD8C), Color(0xFFD6CF5A), Color(0xFFEBA556), Color(0xFFEB463F))),
    )

    fun at(index: Int) = all[index.coerceIn(0, all.lastIndex)]
}