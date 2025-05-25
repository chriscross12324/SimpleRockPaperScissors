package com.simplegames.chris.rockpaperscissors.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.card.MaterialCardView
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.utils.CurrentScreen
import com.simplegames.chris.rockpaperscissors.utils.UIElements
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities.ViewProperty
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew
import com.simplegames.chris.rockpaperscissors.utils.VibrationType
import com.simplegames.chris.rockpaperscissors.utils.vibrate

class AboutActivity : AppCompatActivity() {
    // Create Screen Values
    private lateinit var scrollView: NestedScrollView
    private lateinit var appVersion: TextView
    private lateinit var backButton: MaterialCardView
    private lateinit var background: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (ValuesNew.darkThemeEnabled) R.style.DarkTheme else R.style.LightTheme)
        setContentView(R.layout.activity_app_info)

        initializeUI()
    }

    private fun initializeUI() {
        scrollView = findViewById(R.id.appInfoScrollView)
        appVersion = findViewById(R.id.versionBody)
        backButton = findViewById(R.id.buttonBack)
        background = findViewById(R.id.background)

        setupListeners()
        displayAppVersion()
        UIUtilities.setBackground(background, UIElements.getBackgroundColours(this), 0f)
        enterAnimation()
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            vibrate(this, VibrationType.WEAK)
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backButton.isClickable = false

                scrollView.smoothScrollTo(0, 0, 500)

                val height = resources.displayMetrics.heightPixels.toFloat()
                UIUtilities.animate(
                    scrollView,
                    ViewProperty.TRANSLATION_Y,
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    AccelerateInterpolator(3f),
                    height
                )

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@AboutActivity, SettingsActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    finish()
                    this@AboutActivity.overridePendingTransition(0, 0)
                }, ValuesNew.ANIMATION_DURATION.toLong())
            }
        })
    }

    private fun displayAppVersion() {
        try {
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            appVersion.text = versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun enterAnimation() {
        val height = resources.displayMetrics.heightPixels + 100f

        scrollView.y = height

        UIUtilities.animate(
            scrollView, ViewProperty.TRANSLATION_Y, 100, ValuesNew.ANIMATION_DURATION,
            DecelerateInterpolator(3f), 0f
        )

        scrollView.visibility = View.VISIBLE
        ValuesNew.currentScreen = CurrentScreen.ABOUT
    }
}
