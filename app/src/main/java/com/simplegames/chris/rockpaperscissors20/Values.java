package com.simplegames.chris.rockpaperscissors20;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class Values {

    public static final String SAVE = "SavedGame";
    public static String specialImageURL = "";
    public static boolean imageAlreadyDownloaded = false;
    public static boolean firstOpen;
    public static String currentActivity;

    //General Values
    public static boolean vibrationEnabled;
    public static int animationSpeed = 800;

    //Single Player Values
    public static int losses = 0;
    public static int draws = 0;
    public static int wins = 0;

    public static int aiRockPercent = 33;
    public static int aiPaperPercent = 33;
    public static int aiScissorsPercent = 33;

    public static int SPBackgroundNumber;
    public static int TLColour;
    public static int BRColour;
    public static String CustomBGTop = "#C85610";
    public static String CustomBGBottom = "#F8DD5A";
    public static boolean resetScoreSP = false;

    public static int SPVibrationAmount = 15;
    public static long[] SPAlreadyPlaying = {0, 25, 45, 25};

    //Settings Values
    public static int SettingsBGButtonVibrationAmount = 3;
    public static long[] ResetScoreVibrationAmount = {50, 11, 70, 11};
    public static boolean devMode = false;
    public static boolean darkTheme;


    //Vibration Strengths
    public static int vibrationLow = 7;
    public static int vibrationMedium = 14;
    public static int vibrationHigh = 21;
    public static long[] vibrationError = {0, 25, 45, 25};
    public static long[] vibrationReset = {50, 10, 70, 10};

    //Misc Values
    public static int mainMenuButtonVibration = 5;
    public static int openGM = 5;
    public static int openMenu = 3;

    public static long[] Unavailable = {0, 25, 45, 25};
    public static long[] Secret = {50, 10, 70, 10};

    public static void saveTimer(final Context context){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                saveAllValues(context);
                saveTimer(context);
                if (devMode){
                    //Vibrations.SPButtonSelectedVibration(context);
                }
            }
        }, 1000);
    }
    public static void saveAllValues(Context context){
        SharedPreferences savedValues = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE);
        SharedPreferences.Editor savedValuesEditor = savedValues.edit();
        savedValuesEditor.putBoolean("firstOpen", firstOpen);
        savedValuesEditor.putBoolean("vibrationEnabled", vibrationEnabled);
        savedValuesEditor.putBoolean("darkTheme", darkTheme);
        savedValuesEditor.putInt("losses", losses);
        savedValuesEditor.putInt("draws", draws);
        savedValuesEditor.putInt("wins", wins);
        savedValuesEditor.putInt("animationSpeed", animationSpeed);
        savedValuesEditor.putInt("SPBackgroundNumber", SPBackgroundNumber);
        savedValuesEditor.putString("CustomBGTop", CustomBGTop);
        savedValuesEditor.putString("CustomBGBottom", CustomBGBottom);
        savedValuesEditor.putBoolean("resetScoreSP", resetScoreSP);
        savedValuesEditor.apply();
    }
    public static void loadAllValues(Context context){
        saveTimer(context);
        SharedPreferences savedValues = context.getSharedPreferences(SAVE, Context.MODE_PRIVATE);
        firstOpen = savedValues.getBoolean("firstOpen", true);
        vibrationEnabled = savedValues.getBoolean("vibrationEnabled", true);
        darkTheme = savedValues.getBoolean("darkTheme", true);
        losses = savedValues.getInt("losses", 0);
        draws = savedValues.getInt("draws", 0);
        wins = savedValues.getInt("wins", 0);
        animationSpeed = savedValues.getInt("animationSpeed", 800);
        try {
            SPBackgroundNumber = savedValues.getInt("SPBackgroundNumber", 1);
        }catch (Exception e){
            SPBackgroundNumber = 1;
        }

        CustomBGTop = savedValues.getString("CustomBGTop", "#");
        CustomBGBottom = savedValues.getString("CustomBGBottom", "#");
        resetScoreSP = savedValues.getBoolean("resetScoreSP", false);
    }

}
