package com.simplegames.chris.rockpaperscissors20;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

public class UIElements {

    public static void determineBackground(ConstraintLayout background, ConstraintLayout foreground, Context context){
        switch (Values.SPBackgroundNumber){
            case 0:
                UIElements.twoPartGradient(background,foreground, ContextCompat.getColor(context, R.color.wintersDayTL),
                        ContextCompat.getColor(context, R.color.wintersDayBR),0f);
                return;
            case 1:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.shallowLakeTL),
                        ContextCompat.getColor(context, R.color.shallowLakeBR), 0f);
                return;
            case 2:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.tropicalOceanTL),
                        ContextCompat.getColor(context, R.color.tropicalOceanBR), 0f);
                return;
            case 3:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.greenGrassTL),
                        ContextCompat.getColor(context, R.color.greenGrassBR), 0f);
                return;
            case 4:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.sunshineTL),
                        ContextCompat.getColor(context, R.color.sunshineBR), 0f);
                return;
            case 5:
                UIElements.twoPartGradient(background, foreground,ContextCompat.getColor(context, R.color.forestTL),
                        ContextCompat.getColor(context, R.color.forestBR), 0f);
                return;
            case 6:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.atmosphereTL),
                        ContextCompat.getColor(context, R.color.atmosphereBR), 0f);
                return;
            case 7:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.purpleHazeTL),
                        ContextCompat.getColor(context, R.color.purpleHazeBR), 0f);
                return;
            case 8:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.coldNightTL),
                        ContextCompat.getColor(context, R.color.coldNightBR), 0f);
                return;
            case 9:
                UIElements.twoPartGradient(background,foreground,ContextCompat.getColor(context, R.color.eternalSpaceTL),
                        ContextCompat.getColor(context, R.color.eternalSpaceBR), 0f);
        }
    }
    public static void determineBackground2(ConstraintLayout background, ConstraintLayout foreground, Context context){
        UIElements.twoPartGradient(background, foreground, Values.TLColour, Values.BRColour, 0F);
    }
    public static void twoPartGradient(ConstraintLayout background, ConstraintLayout foreground, int TL, int BR, float corner){
        if (Values.currentActivity == "Settings"){
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = new GradientDrawable(
                                GradientDrawable.Orientation.TL_BR,
                                new int[] {TL, BR});
                        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                        gradientDrawable.setCornerRadius(corner);
                        background.setBackground(gradientDrawable);
                        foreground.setAlpha(0);
                    }
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

    public static void slideAnimation(ConstraintLayout layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }
    public static void slideAnimationLinear(LinearLayout layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }
    public static void slideAnimationText(TextView layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }
    public static void slideAnimationScrollView(NestedScrollView layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }
    public static void slideAnimationFrame(FrameLayout layout, String propertyName, int finishedPos, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, finishedPos);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }
    public static void fadeAnimationFrame(FrameLayout layout, String propertyName, int startOpacity, int endOpacity, int delay, int animationSpeed, TimeInterpolator interpolator){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layout, propertyName, startOpacity, endOpacity);
        objectAnimator.setDuration(animationSpeed);
        objectAnimator.setInterpolator(interpolator);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                objectAnimator.start();
            }
        }, delay);
    }



}
