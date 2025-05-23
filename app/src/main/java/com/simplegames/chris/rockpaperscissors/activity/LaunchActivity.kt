package com.simplegames.chris.rockpaperscissors.activity

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {true}

        //Load Value
        ValuesNew.loadValues(context = applicationContext)

        //Open Screen
        startActivity(Intent(this, GameActivity::class.java))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.fade_in, R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        //Finish Screen
        finish()
    }
}