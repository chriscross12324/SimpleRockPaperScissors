package com.simplegames.chris.rockpaperscissors.utils

import android.content.Context
import android.content.SharedPreferences

object ValuesNew {
    //App Essentials
    val currentScreen = CurrentScreen.GAME
    const val ANIMATION_DURATION = 800

    //User Settings
    var vibrationEnabled: Boolean = true
    var darkThemeEnabled: Boolean = true
    var backgroundGradient: Int = 0

    //Scoreboard
    var userWins: Int = 0
    var userDraws: Int = 0
    var userLosses: Int = 0

    fun saveValue(context: Context, key: String, value: Any) {
        //Initialize Shared Preferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferenceKeys.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        //Save Value
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }

        //Apply Changes
        editor.apply()
    }

    fun loadValues(context: Context) {
        //Initialize Shared Preferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferenceKeys.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

        //Load Values
        vibrationEnabled = sharedPreferences.getBoolean(SharedPreferenceKeys.KEY_SETTING_VIBRATIONS, true)
        darkThemeEnabled = sharedPreferences.getBoolean(SharedPreferenceKeys.KEY_SETTING_THEME, true)
        backgroundGradient = sharedPreferences.getInt(SharedPreferenceKeys.KEY_SETTING_BACKGROUND_GRADIENT, 0)
        userWins = sharedPreferences.getInt(SharedPreferenceKeys.KEY_SCOREBOARD_WINS, 0)
        userDraws = sharedPreferences.getInt(SharedPreferenceKeys.KEY_SCOREBOARD_DRAWS, 0)
        userLosses = sharedPreferences.getInt(SharedPreferenceKeys.KEY_SCOREBOARD_LOSSES, 0)
    }
}

enum class CurrentScreen {
    GAME, SETTINGS, ABOUT
}