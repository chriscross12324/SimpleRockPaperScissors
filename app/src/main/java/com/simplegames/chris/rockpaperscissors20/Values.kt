package com.simplegames.chris.rockpaperscissors20

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException

object ValuesNew {
    //Singleton Instance
    private var instance: ValuesNew? = null

    //App Essentials
    val currentScreen = CurrentScreen.GAME
    val animationDuration = 800

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
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferenceKeys.sharedPreferencesKey, Context.MODE_PRIVATE)
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
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SharedPreferenceKeys.sharedPreferencesKey, Context.MODE_PRIVATE)

        //Load Values
        vibrationEnabled = sharedPreferences.getBoolean(SharedPreferenceKeys.keySettingVibrations, true)
        darkThemeEnabled = sharedPreferences.getBoolean(SharedPreferenceKeys.keySettingTheme, true)
        backgroundGradient = sharedPreferences.getInt(SharedPreferenceKeys.keySettingBackgroundGradient, 0)
        userWins = sharedPreferences.getInt(SharedPreferenceKeys.keyScoreboardWins, 0)
        userDraws = sharedPreferences.getInt(SharedPreferenceKeys.keyScoreboardDraws, 0)
        userLosses = sharedPreferences.getInt(SharedPreferenceKeys.keyScoreboardLosses, 0)
    }

    fun getInstance(): ValuesNew {
        if (instance == null) {
            instance = ValuesNew
        }
        return instance!!
    }
}

enum class CurrentScreen {
    GAME, SETTINGS, ABOUT
}