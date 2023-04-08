package com.simplegames.chris.rockpaperscissors20;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Open Screen
        Intent sp = new Intent(SplashScreen.this, SinglePlayerNew.class);
        startActivity(sp);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        //Load Value
        Values.loadAllValues(getApplicationContext());

        //Finish Screen
        finish();
    }
}
