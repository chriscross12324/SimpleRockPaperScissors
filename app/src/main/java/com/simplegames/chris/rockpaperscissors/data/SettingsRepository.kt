package com.simplegames.chris.rockpaperscissors.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val LEGACY_PREFS_NAME = "SharedPreferences"

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings",
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context,
                LEGACY_PREFS_NAME
            )
        )
    },
)

class SettingsRepository(private val store: DataStore<Preferences>) {
    private object Keys {
        val isVibrationEnabled = booleanPreferencesKey("isVibrationsEnabled")
        val isDarkModeEnabled = booleanPreferencesKey("isDarkModeEnabled")
        val backgroundGradientIndex = intPreferencesKey("backgroundGradientIndex")
    }

    val settings: Flow<AppSettings> = store.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs ->
            val default = AppSettings.Default
            AppSettings(
                vibration = prefs[Keys.isVibrationEnabled] ?: default.vibration,
                darkMode = prefs[Keys.isDarkModeEnabled] ?: default.darkMode,
                gradientIndex = prefs[Keys.backgroundGradientIndex] ?: default.gradientIndex,
            )
        }

    suspend fun toggleVibration() {
        store.edit { it[Keys.isVibrationEnabled] = !(it[Keys.isVibrationEnabled] ?: AppSettings.Default.vibration) }
    }

    suspend fun toggleDarkMode() {
        store.edit { it[Keys.isDarkModeEnabled] = !(it[Keys.isDarkModeEnabled] ?: AppSettings.Default.darkMode) }
    }

    suspend fun setGradientIndex(index: Int) {
        store.edit { it[Keys.backgroundGradientIndex] = index }
    }
}