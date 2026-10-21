package com.simplegames.chris.rockpaperscissors.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simplegames.chris.rockpaperscissors.data.Score
import com.simplegames.chris.rockpaperscissors.data.ScoreRepository
import com.simplegames.chris.rockpaperscissors.data.scoreDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


private val FrameDelayMs = listOf(
    100L, 100, 100, 100, 100, 100, 100, 100, 100, 100,
    100, 100, 100, 100, 100, 110, 120, 130, 140, 150,
    200, 250, 300, 350, 400, 450, 500, 600, 700, 800
)

sealed interface Phase {
    data object Idle : Phase
    data class Playing(val choice: Hand) : Phase
}
private data class Round(
    val phase: Phase = Phase.Idle,
    val cpuImage: Hand = Hand.Rock,
    val result: Outcome? = null,
)

data class GameState(
    val phase: Phase = Phase.Idle,
    val cpuImage: Hand = Hand.Rock,
    val result: Outcome? = null,
    val score: Score = Score(),
)

class GameViewModel(app: Application) : AndroidViewModel(app) {
    private val scores = ScoreRepository(app.scoreDataStore)
    private val round = MutableStateFlow(Round())

    val state: StateFlow<GameState> = combine(round, scores.score) { r, score ->
        GameState(r.phase, r.cpuImage, r.result, score)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, GameState())

    fun play(player: Hand) {
        if (round.value.phase != Phase.Idle) {
            return
        }

        val cpu = Hand.entries.random()
        val frames = buildCpuFrames(start = round.value.cpuImage, finalChoice = cpu, steps = FrameDelayMs.size)
        round.update { it.copy(phase = Phase.Playing(player)) }

        viewModelScope.launch {
            frames.forEachIndexed { i, hand ->
                round.update { it.copy(cpuImage = hand) }
                delay(FrameDelayMs[i].milliseconds)
            }

            val outcome = resolve(player, cpu)
            round.update { it.copy(phase = Phase.Idle, result = outcome) }
            scores.record(outcome)
        }
    }

    fun resetScore() {
        viewModelScope.launch { scores.reset() }
    }
}