package com.simplegames.chris.rockpaperscissors20

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class StartingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {true}

        //Open Screen
        val sp = Intent(this, SinglePlayer::class.java)
        startActivity(sp)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


        //Load Value
        Values.loadAllValues(applicationContext)


        //Finish Screen
        finish()
    }
}