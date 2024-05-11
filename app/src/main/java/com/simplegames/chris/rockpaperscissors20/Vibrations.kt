package com.simplegames.chris.rockpaperscissors20

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi

fun vibrate(context: Context, type: VibrationType) {
    //Create Vibrator
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    //Don't continue if Vibrations are disabled
    if (!ValuesNew.vibrationEnabled) return

    //Vibrate
    when (type) {
        VibrationType.WEAK -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(5L, 25)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(7L)
            }
        }
        VibrationType.MEDIUM -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(10L, 100)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(14L)
            }
        }
        VibrationType.STRONG -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(15L, 200)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(21L)
            }
        }
        VibrationType.ERROR -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(longArrayOf(0L, 25L, 45L, 25L), -1)
            }
        }
    }
}

enum class VibrationType {
    WEAK, MEDIUM, STRONG, ERROR
}