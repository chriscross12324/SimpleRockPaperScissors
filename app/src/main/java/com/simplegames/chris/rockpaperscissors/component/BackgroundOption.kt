package com.simplegames.chris.rockpaperscissors.component

data class BackgroundOption(
    val colours: IntArray,
    val text: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BackgroundOption

        if (!colours.contentEquals(other.colours)) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colours.contentHashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}
