package com.simplegames.chris.rockpaperscissors20;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;

public class UIElements {

    public static int[] getBackgroundColours(Context context) {
        int selectedBackground = ValuesNew.INSTANCE.getBackgroundGradient();
        return getColourArray(context, selectedBackground);
    }

    public static int[] getColourArray(Context context, int selectedBackground) {
        return switch (selectedBackground) {
            case 0 ->
                    UIElements.colourToInt(context, new int[]{R.color.startSnowfall, R.color.endSnowfall});
            case 1 ->
                    UIElements.colourToInt(context, new int[]{R.color.startFadedRed, R.color.endFadedRed});
            case 2 ->
                    UIElements.colourToInt(context, new int[]{R.color.startSunset, R.color.endSunset});
            case 3 ->
                    UIElements.colourToInt(context, new int[]{R.color.startHotLava, R.color.endHotLava});
            case 4 ->
                    UIElements.colourToInt(context, new int[]{R.color.startCottonCandy, R.color.endCottonCandy});
            case 5 ->
                    UIElements.colourToInt(context, new int[]{R.color.startSunshine, R.color.endSunshine});
            case 6 ->
                    UIElements.colourToInt(context, new int[]{R.color.firstTrafficLights, R.color.secondTrafficLights, R.color.thirdTrafficLights});
            case 7 ->
                    UIElements.colourToInt(context, new int[]{R.color.startGreenGrass, R.color.endGreenGrass});
            case 8 ->
                    UIElements.colourToInt(context, new int[]{R.color.startConiferous, R.color.endConiferous});
            case 9 ->
                    UIElements.colourToInt(context, new int[]{R.color.startTropicalOcean, R.color.startTropicalOcean});
            case 10 ->
                    UIElements.colourToInt(context, new int[]{R.color.startSunnyDepths, R.color.endSunnyDepths});
            case 11 ->
                    UIElements.colourToInt(context, new int[]{R.color.firstOrbit, R.color.secondOrbit, R.color.thirdOrbit, R.color.fourthOrbit});
            case 12 ->
                    UIElements.colourToInt(context, new int[]{R.color.startJuicyPomegranate, R.color.endJuicyPomegranate});
            case 13 ->
                    UIElements.colourToInt(context, new int[]{R.color.firstAmethyst, R.color.secondAmethyst, R.color.thirdAmethyst, R.color.fourthAmethyst, R.color.fifthAmethyst});
            case 14 ->
                    UIElements.colourToInt(context, new int[]{R.color.startDarkness, R.color.endDarkness});
            case 15 ->
                    UIElements.colourToInt(context, new int[]{R.color.firstLollipop, R.color.secondLollipop, R.color.thirdLollipop, R.color.fourthLollipop, R.color.fifthLollipop, R.color.sixthLollipop});
            default -> new int[0];
        };
    }

    public static void setBackground(View view, int[] colours, float cornerRadius) {
        //Create the New Gradient
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colours);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(cornerRadius);

        //Set Background
        view.setBackground(gradientDrawable);
    }

    public static void animate(View layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(objectAnimator::start, delay);
    }

    public static int dpToFloat(float inputDP) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, inputDP, displayMetrics));
    }

    public static int[] colourToInt(Context context, int[] colourIDs) {
        int[] convertedColour = new int[colourIDs.length];

        for (int pos = 0; pos < colourIDs.length; pos++) {
            convertedColour[pos] = ContextCompat.getColor(context, colourIDs[pos]);
        }
        return convertedColour;
    }
}
