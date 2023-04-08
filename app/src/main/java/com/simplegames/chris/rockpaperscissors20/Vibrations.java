package com.simplegames.chris.rockpaperscissors20;

import android.content.Context;
import android.os.Vibrator;

public class Vibrations {

    public static void vibrate(Context context, String vibrationType) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (Values.vibrationEnabled) {
            switch (vibrationType) {
                case "low": {
                    v.vibrate(Values.vibrationLow);
                    break;
                }
                case "medium": {
                    v.vibrate(Values.vibrationMedium);
                    break;
                }
                case "high": {
                    v.vibrate(Values.vibrationHigh);
                    break;
                }
                case "reset": {
                    v.vibrate(Values.vibrationReset, -1);
                }
                default: {
                    v.vibrate(Values.vibrationError, -1);
                    break;
                }
            }
        }
    }
}
