package com.simplegames.chris.rockpaperscissors20;

import android.content.Context;
import android.content.Intent;

public class Goto {

    public static void mainMenuNew(Context context){
        Intent menuTest = new Intent(context, MenuTest.class);
        context.startActivity(menuTest);
        Vibrations.mainMenuButtonVibration(context);
    }
    public static void singlePlayer(Context context){
        Intent sp = new Intent(context, SinglePlayer.class);
        context.startActivity(sp);
        Vibrations.openGameMode(context);
    }
    public static void settingsNew(Context context){
        Intent spn = new Intent(context, Settings.class);
        context.startActivity(spn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        Vibrations.openMenu(context);
    }
    public static void help(Context context){
        Intent sp = new Intent(context, Help.class);
        context.startActivity(sp.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        Vibrations.openMenu(context);
    }
    public static void appInfo(Context context){
        Intent appinfo = new Intent(context, AppInfo.class);
        context.startActivity(appinfo.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        Vibrations.mainMenuButtonVibration(context);
    }
    public static void permissionsScreen(Context context){
        Intent permissions = new Intent(context, PermissionsScreen.class);
        context.startActivity(permissions);
        Vibrations.SPAlreadyPlayingVibration(context);
    }
}
