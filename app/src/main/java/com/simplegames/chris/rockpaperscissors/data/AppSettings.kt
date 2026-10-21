package com.simplegames.chris.rockpaperscissors.data

data class AppSettings(
    val vibration: Boolean = true,
    val darkMode: Boolean = true,
    val gradientIndex: Int = 7,
) {
    companion object { val Default = AppSettings() }
}