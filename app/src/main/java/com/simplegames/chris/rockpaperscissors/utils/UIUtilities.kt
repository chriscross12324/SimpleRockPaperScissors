package com.simplegames.chris.rockpaperscissors.utils

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.utils.Conversions.colourToInt

object UIUtilities {

    enum class ViewProperty(val propertyName: String) {
        TRANSLATION_X("translationX"),
        TRANSLATION_Y("translationY"),
        ALPHA("alpha"),
        SCALE_X("scaleX"),
        SCALE_Y("scaleY"),
        ROTATION("rotation")
    }

    fun getBackgroundColours(context: Context): IntArray =
        getColourArray(context, ValuesNew.backgroundGradient)

    fun getColourArray(context: Context, selectedBackground: Int): IntArray =
        when (selectedBackground) {
            0 -> colourToInt(context, intArrayOf(R.color.startSnowfall, R.color.endSnowfall))
            1 -> colourToInt(context, intArrayOf(R.color.startFadedRed, R.color.endFadedRed))
            2 -> colourToInt(context, intArrayOf(R.color.startSunset, R.color.endSunset))
            3 -> colourToInt(context, intArrayOf(R.color.startHotLava, R.color.endHotLava))
            4 -> colourToInt(context, intArrayOf(R.color.startCottonCandy, R.color.endCottonCandy))
            5 -> colourToInt(context, intArrayOf(R.color.startSunshine, R.color.endSunshine))
            6 -> colourToInt(
                context,
                intArrayOf(
                    R.color.firstTrafficLights,
                    R.color.secondTrafficLights,
                    R.color.thirdTrafficLights
                )
            )

            7 -> colourToInt(context, intArrayOf(R.color.startGreenGrass, R.color.endGreenGrass))
            8 -> colourToInt(context, intArrayOf(R.color.startConiferous, R.color.endConiferous))
            9 -> colourToInt(
                context,
                intArrayOf(R.color.startTropicalOcean, R.color.endTropicalOcean)
            )

            10 -> colourToInt(context, intArrayOf(R.color.startSunnyDepths, R.color.endSunnyDepths))
            11 -> colourToInt(
                context,
                intArrayOf(
                    R.color.firstOrbit,
                    R.color.secondOrbit,
                    R.color.thirdOrbit,
                    R.color.fourthOrbit
                )
            )

            12 -> colourToInt(
                context,
                intArrayOf(R.color.startJuicyPomegranate, R.color.endJuicyPomegranate)
            )

            13 -> colourToInt(
                context,
                intArrayOf(
                    R.color.firstAmethyst,
                    R.color.secondAmethyst,
                    R.color.thirdAmethyst,
                    R.color.fourthAmethyst,
                    R.color.fifthAmethyst
                )
            )

            14 -> colourToInt(context, intArrayOf(R.color.startDarkness, R.color.endDarkness))
            15 -> colourToInt(
                context,
                intArrayOf(
                    R.color.firstLollipop,
                    R.color.secondLollipop,
                    R.color.thirdLollipop,
                    R.color.fourthLollipop,
                    R.color.fifthLollipop,
                    R.color.sixthLollipop
                )
            )

            else -> intArrayOf()
        }

    fun setBackground(view: View, colours: IntArray, cornerRadius: Float) {
        view.background = GradientDrawable(GradientDrawable.Orientation.TL_BR, colours).apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadius = cornerRadius
        }
    }

    fun animate(
        view: View,
        property: ViewProperty,
        delay: Int,
        duration: Int,
        interpolator: TimeInterpolator = android.view.animation.DecelerateInterpolator(3f),
        values: Float,
    ) {
        ObjectAnimator.ofFloat(view, property.propertyName, values).apply {
            this.duration = duration.toLong()
            this.interpolator = interpolator
            startDelay = delay.toLong()
            start()
        }
    }

}