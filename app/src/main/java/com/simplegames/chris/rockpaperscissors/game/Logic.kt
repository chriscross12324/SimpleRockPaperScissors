package com.simplegames.chris.rockpaperscissors.game

enum class Hand(val label: String) {
    Rock("Rock"),
    Paper("Paper"),
    Scissors("Scissors");

    fun beats(other: Hand) =
        (this == Rock && other == Scissors) ||
                (this == Paper && other == Rock) ||
                (this == Scissors && other == Paper)
}

enum class Outcome {
    Win, Lose, Draw
}

fun resolve(player: Hand, cpu: Hand): Outcome = when {
    player == cpu -> Outcome.Draw
    player.beats(cpu) -> Outcome.Win
    else -> Outcome.Lose
}

fun buildCpuFrames(start: Hand, finalChoice: Hand, steps: Int): List<Hand> {
    val frames = ArrayList<Hand>(steps)
    var previous = start
    for (i in 0 until steps) {
        val next = when (i) {
            steps - 1 -> finalChoice
            steps - 2 -> Hand.entries.filter { it != previous && it != finalChoice }.random()
            else -> Hand.entries.filter { it != previous }.random()
        }
        frames += next
        previous = next
    }

    return frames
}

