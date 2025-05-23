package com.simplegames.chris.rockpaperscissors.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.ContextCompat

object Conversions {
    fun colourToInt(context: Context, colourIDs: IntArray): IntArray =
        colourIDs.map { ContextCompat.getColor(context, it) }.toIntArray()

    fun dpToPx(dp: Float): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().displayMetrics
    ).toInt()
}