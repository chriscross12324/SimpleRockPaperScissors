package com.simplegames.chris.rockpaperscissors20;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class UIElements {

    public static int[] getBackgroundColours(Context context) {
        int selectedBackground = ValuesNew.INSTANCE.getBackgroundGradient();
        return getColourArray(context, selectedBackground);
    }

    public static int[] getBackgroundColours(Context context, int selectedBackground) {
        return getColourArray(context, selectedBackground);
    }

    public static int[] getColourArray(Context context, int selectedBackground) {
        return switch (selectedBackground) {
            case 0 ->
                    new int[]{ContextCompat.getColor(context, R.color.wintersDayTL), ContextCompat.getColor(context, R.color.wintersDayBR)};
            case 1 ->
                    new int[]{ContextCompat.getColor(context, R.color.shallowLakeTL), ContextCompat.getColor(context, R.color.shallowLakeBR)};
            case 2 ->
                    new int[]{ContextCompat.getColor(context, R.color.tropicalOceanTL), ContextCompat.getColor(context, R.color.tropicalOceanBR)};
            case 3 ->
                    new int[]{ContextCompat.getColor(context, R.color.greenGrassTL), ContextCompat.getColor(context, R.color.greenGrassBR)};
            case 4 ->
                    new int[]{ContextCompat.getColor(context, R.color.sunshineTL), ContextCompat.getColor(context, R.color.sunshineBR)};
            case 5 ->
                    new int[]{ContextCompat.getColor(context, R.color.forestTL), ContextCompat.getColor(context, R.color.forestBR)};
            case 6 ->
                    new int[]{ContextCompat.getColor(context, R.color.atmosphereTL), ContextCompat.getColor(context, R.color.atmosphereBR)};
            case 7 ->
                    new int[]{ContextCompat.getColor(context, R.color.purpleHazeTL), ContextCompat.getColor(context, R.color.purpleHazeBR)};
            case 8 ->
                    new int[]{ContextCompat.getColor(context, R.color.coldNightTL), ContextCompat.getColor(context, R.color.coldNightBR)};
            case 9 ->
                    new int[]{ContextCompat.getColor(context, R.color.eternalSpaceTL), ContextCompat.getColor(context, R.color.eternalSpaceBR)};
            case 10 ->
                    new int[]{ContextCompat.getColor(context, R.color.juicyPomegranateTL), ContextCompat.getColor(context, R.color.juicyPomegranateBR)};
            case 11 ->
                    new int[]{ContextCompat.getColor(context, R.color.sunnyDepthsTL), ContextCompat.getColor(context, R.color.sunnyDepthsBR)};
            default -> new int[0];
        };
    }

    public static void setBackground(Context context, View view, int[] colours, float cornerRadius, long animationDuration) {
        //Get the Current Gradient
        //GradientDrawable currentGradient = (GradientDrawable) view.getBackground();

        //Create the New Gradient
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colours);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(cornerRadius);

        //Create Animation
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(5000L);
        valueAnimator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();

            //Blend Colours
            int[] blendedColours = new int[colours.length];
            for (int endColour : colours) {
                //int startColour = currentGradient == null ? colours[i] : Objects.requireNonNull(currentGradient.getColors())[i];
                //blendedColours[i] = blendColours(context, startColour, endColour, fraction);
            }

            Log.e("MSG", "" + blendedColours[0]);

            //Set Blended Colours
            gradientDrawable.setColors(colours);
            view.setBackground(gradientDrawable);
        });
        valueAnimator.start();
        //Toast.makeText(view.getContext(), "Animating", Toast.LENGTH_SHORT).show();
    }
    
    private static int blendColours(Context context, int startColour, int endColour, float fraction) {
        int red = (int) ((Color.red(endColour) * fraction) + (Color.red(startColour) * (1 - fraction)));
        int green = (int) ((Color.green(endColour) * fraction) + (Color.green(startColour) * (1 - fraction)));
        int blue = (int) ((Color.blue(endColour) * fraction) + (Color.blue(startColour) * (1 - fraction)));

        return Color.argb(255, red, green, blue);
    }

    public static void twoPartGradient(View background, ConstraintLayout foreground, int TL, int BR, float corner){
        if (Objects.equals(Values.currentActivity, "Settings")){
            if (foreground != null){
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[] {TL, BR});
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(corner);
                foreground.setBackground(gradientDrawable);
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(foreground, "alpha", 0, 1);
                fadeIn.setDuration(500);
                fadeIn.start();
                new Handler().postDelayed(() -> {
                    GradientDrawable gradientDrawable1 = new GradientDrawable(
                            GradientDrawable.Orientation.TL_BR,
                            new int[] {TL, BR});
                    gradientDrawable1.setShape(GradientDrawable.RECTANGLE);
                    gradientDrawable1.setCornerRadius(corner);
                    background.setBackground(gradientDrawable1);
                    foreground.setAlpha(0);
                }, 500);
            }else {
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[] {TL, BR});
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(corner);
                background.setBackground(gradientDrawable);
            }
        }else {
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[] {TL, BR});
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(corner);
            background.setBackground(gradientDrawable);
        }
    }

    public static void solidColours(ConstraintLayout layout, int colour, float[] corner){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[] {colour, colour});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(corner);
        try {
            layout.setBackground(gradientDrawable);
        }catch (Exception e){}
    }

    public static void animate(View layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
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
}
