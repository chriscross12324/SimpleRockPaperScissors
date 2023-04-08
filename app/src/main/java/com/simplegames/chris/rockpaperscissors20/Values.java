package com.simplegames.chris.rockpaperscissors20;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class Values {

    //App Essentials
    public static final String SAVE = "SavedGame";
    public static String currentActivity;
    public static int animationSpeed = 800;
    public static boolean devMode = false;

    //Vibration Strengths
    public static int vibrationLow = 7;
    public static int vibrationMedium = 14;
    public static int vibrationHigh = 21;
    public static long[] vibrationError = {0, 25, 45, 25};
    public static long[] vibrationReset = {50, 10, 70, 10};

    //Scoreboard
    public static int losses = 0;
    public static int draws = 0;
    public static int wins = 0;

    //User Settings
    public static boolean vibrationEnabled;
    public static boolean darkThemeEnabled;
    public static int selectedBackground;
    public static boolean resetScoreSP = false;

    //Experimental AI
    public static int aiRockPercent = 33;
    public static int aiPaperPercent = 33;
    public static int aiScissorsPercent = 33;


    public static void saveTimer(final Context context){
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            saveAllValues(context);
            saveTimer(context);
            if (true){
                Vibrations.vibrate(context, "high");
            }
        }, 1000);
    }
    public static void saveAllValues(Context context){
        SharedPreferences savedValues = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE);
        SharedPreferences.Editor savedValuesEditor = savedValues.edit();
        savedValuesEditor.putBoolean("vibrationEnabled", vibrationEnabled);
        savedValuesEditor.putBoolean("darkTheme", darkThemeEnabled);
        savedValuesEditor.putInt("losses", losses);
        savedValuesEditor.putInt("draws", draws);
        savedValuesEditor.putInt("wins", wins);
        savedValuesEditor.putInt("animationSpeed", animationSpeed);
        savedValuesEditor.putInt("SPBackgroundNumber", selectedBackground);
        savedValuesEditor.putBoolean("resetScoreSP", resetScoreSP);
        savedValuesEditor.apply();
    }

    public static void loadAllValues(Context context){
        saveTimer(context);
        SharedPreferences savedValues = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE);
        vibrationEnabled = savedValues.getBoolean("vibrationEnabled", true);
        darkThemeEnabled = savedValues.getBoolean("darkTheme", true);
        losses = savedValues.getInt("losses", 0);
        draws = savedValues.getInt("draws", 0);
        wins = savedValues.getInt("wins", 0);
        animationSpeed = savedValues.getInt("animationSpeed", 800);
        try {
            selectedBackground = savedValues.getInt("SPBackgroundNumber", 1);
        }catch (Exception e){
            selectedBackground = 1;
        }
        resetScoreSP = savedValues.getBoolean("resetScoreSP", false);
    }

}
