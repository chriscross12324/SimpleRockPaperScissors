package com.simplegames.chris.rockpaperscissors.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.simplegames.chris.rockpaperscissors.game.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class Score(
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
)

val Context.scoreDataStore: DataStore<Preferences> by preferencesDataStore(name = "score")

class ScoreRepository(private val store: DataStore<Preferences>) {
    private object Keys {
        val totalWins = intPreferencesKey("totalWins")
        val totalLosses = intPreferencesKey("totalLosses")
        val totalDraws = intPreferencesKey("totalDraws")
    }

    val score: Flow<Score> = store.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { prefs ->
            Score(
                wins = prefs[Keys.totalWins] ?: 0,
                losses = prefs[Keys.totalLosses] ?: 0,
                draws = prefs[Keys.totalDraws] ?: 0,
            )
        }

    suspend fun record(outcome: Outcome) {
        store.edit { prefs ->
            val key = when (outcome)
            {
                Outcome.Win -> Keys.totalWins
                Outcome.Lose -> Keys.totalLosses
                Outcome.Draw -> Keys.totalDraws
            }
            prefs[key] = (prefs[key] ?: 0) + 1
        }
    }

    suspend fun reset() {
        store.edit { it.clear() }
    }
}