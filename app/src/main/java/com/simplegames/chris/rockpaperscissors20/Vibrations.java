package com.simplegames.chris.rockpaperscissors20;

import android.content.Context;
import android.os.Vibrator;

public class Vibrations {

    public static void mainMenuButtonVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.mainMenuButtonVibration);
        }
    }
    public static void openGameMode(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.openGM);
        }
    }
    public static void openMenu(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.openMenu);
        }
    }

    public static void SPButtonSelectedVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.SPVibrationAmount);
        }
    }
    public static void SPAlreadyPlayingVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.SPAlreadyPlaying, -1);
        }

    }
    public static void UnavailableVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.Unavailable, -1);
        }
    }
    public static void SecretVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.Secret, -1);
        }
    }
    public static void SettingsBGButtonVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.SettingsBGButtonVibrationAmount);
        }
    }
    public static void ResetScoreVibration(Context context){
        if (Values.vibrationEnabled){
            Vibrator t = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            t.vibrate(Values.ResetScoreVibrationAmount, -1);
        }
    }
}
