package com.simplegames.chris.rockpaperscissors.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.SettingsButton
import com.simplegames.chris.rockpaperscissors.SettingsButtonAdapter
import com.simplegames.chris.rockpaperscissors.utils.CurrentScreen
import com.simplegames.chris.rockpaperscissors.utils.SharedPreferenceKeys
import com.simplegames.chris.rockpaperscissors.utils.UIElements
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities.ViewProperty
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew
import com.simplegames.chris.rockpaperscissors.utils.VibrationType
import com.simplegames.chris.rockpaperscissors.utils.vibrate

class SettingsActivity : AppCompatActivity() {
    // Create Screen Values
    private lateinit var scrollView: NestedScrollView
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonVibrate: MaterialCardView
    private lateinit var buttonDarkTheme: MaterialCardView
    private lateinit var buttonBack: MaterialCardView
    private lateinit var buttonAppInfo: MaterialCardView
    private lateinit var vibrationIcon: ImageView
    private lateinit var background: ImageView

    private lateinit var buttonArrayList: ArrayList<SettingsButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (ValuesNew.darkThemeEnabled) R.style.DarkTheme else R.style.LightTheme)
        setContentView(R.layout.activity_settings)

        initializeUI()
    }

    private fun initializeUI() {
        scrollView = findViewById(R.id.scrollView)
        recyclerView = findViewById(R.id.backgroundsRecyclerView)
        buttonVibrate = findViewById(R.id.buttonVibrate)
        buttonDarkTheme = findViewById(R.id.buttonDarkTheme)
        buttonAppInfo = findViewById(R.id.buttonAppInfo)
        buttonBack = findViewById(R.id.buttonBack)
        vibrationIcon = findViewById(R.id.iconVibrate)
        background = findViewById(R.id.background)

        setupListeners()
        setupBackgroundButtons()
        setSettingsBackground()
        enterAnimation()
    }

    private fun setupListeners() {
        buttonBack.setOnClickListener {
            vibrate(this, VibrationType.WEAK)
            onBackPressedDispatcher.onBackPressed()
        }

        buttonVibrate.setOnClickListener {
            ValuesNew.vibrationEnabled = !ValuesNew.vibrationEnabled
            ValuesNew.saveValue(this, SharedPreferenceKeys.KEY_SETTING_VIBRATIONS, ValuesNew.vibrationEnabled)
            updateOptionsStates()
            vibrate(this, VibrationType.WEAK)
        }

        buttonDarkTheme.setOnClickListener {
            ValuesNew.darkThemeEnabled = !ValuesNew.darkThemeEnabled
            ValuesNew.saveValue(this, SharedPreferenceKeys.KEY_SETTING_THEME, ValuesNew.darkThemeEnabled)
            resetLayout()
            vibrate(this, VibrationType.WEAK)
        }

        buttonAppInfo.setOnClickListener {
            vibrate(this, VibrationType.WEAK)
            scrollView.smoothScrollTo(0, 0, ValuesNew.ANIMATION_DURATION / 2)
            UIUtilities.animate(
                scrollView,
                ViewProperty.TRANSLATION_Y,
                0,
                ValuesNew.ANIMATION_DURATION,
                AccelerateInterpolator(3f),
                resources.displayMetrics.heightPixels.toFloat()
            )
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SettingsActivity, AboutActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                finish()
                this@SettingsActivity.overridePendingTransition(0, 0)
            }, ValuesNew.ANIMATION_DURATION.toLong())
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                buttonBack.isClickable = false

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
                    val intent = Intent(this@SettingsActivity, GameActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    finish()
                    this@SettingsActivity.overridePendingTransition(0, 0)
                }, ValuesNew.ANIMATION_DURATION.toLong())
            }
        })
    }

    private fun updateOptionsStates() {
        val (vibButtonColour, vibIconColour) = if (!ValuesNew.vibrationEnabled) {
            intArrayOf(
                ContextCompat.getColor(this, R.color.disabledOption),
                ContextCompat.getColor(this, R.color.disabledOption)
            ) to if (ValuesNew.darkThemeEnabled) R.color.white else R.color.black
        } else {
            intArrayOf(
                ContextCompat.getColor(this, R.color.enabledOption),
                ContextCompat.getColor(this, R.color.enabledOption)
            ) to R.color.white
        }

        UIElements.setBackground(buttonVibrate, vibButtonColour, 15f)
        vibrationIcon.setColorFilter(ContextCompat.getColor(this, vibIconColour))

        val themeButtonColour = if (!ValuesNew.darkThemeEnabled)
            R.color.disabledOption
        else
            R.color.enabledOption

        UIElements.setBackground(buttonDarkTheme, intArrayOf(
            ContextCompat.getColor(this, themeButtonColour),
            ContextCompat.getColor(this, themeButtonColour)
        ), 15f)


    }

    private fun setupBackgroundButtons() {
        val names = listOf<String>(
            "Snowfall",
            "Faded Red",
            "Sunset",
            "Hot Lava",
            "Cotton Candy",
            "Sunshine",
            "Traffic Lights",
            "Green Grass",
            "Coniferous",
            "Tropical Ocean",
            "Sunny Depths",
            "Orbit",
            "Juicy Pomegranate",
            "Amethyst",
            "Darkness",
            "Lollipop"
        )

        buttonArrayList = ArrayList()
        names.forEachIndexed { index, name ->
            buttonArrayList.add(
                SettingsButton(
                    UIUtilities.getColourArray(
                        this,
                        index
                    ), name
                )
            )
        }
        buildBackgroundRecyclerView()
    }

    fun setSettingsBackground() {
        UIUtilities.setBackground(background, UIElements.getBackgroundColours(this), 25f)
    }

    private fun buildBackgroundRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsButtonAdapter(buttonArrayList, this).apply {
            setOnItemClickListener { position -> {
                vibrate(this@SettingsActivity, VibrationType.WEAK)
            }}
        }
    }

    private fun enterAnimation() {
        scrollView.visibility = View.VISIBLE
        scrollView.scrollTo(0, 0)

        if (ValuesNew.currentScreen != CurrentScreen.SETTINGS) {
            scrollView.y = DisplayMetrics().also {
                windowManager.defaultDisplay.getMetrics(it)
            }.heightPixels.toFloat()

            UIUtilities.animate(
                scrollView,
                ViewProperty.TRANSLATION_Y,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f),
                0f
            )

            ValuesNew.currentScreen = CurrentScreen.SETTINGS
        }
    }

    private fun resetLayout() {
        scrollView.smoothScrollTo(0, 0, ValuesNew.ANIMATION_DURATION / 4)
        scrollView.postDelayed({
            val intent = Intent(this@SettingsActivity, SettingsActivity::class.java)
            startActivity(intent)
            this@SettingsActivity.overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            finish()
        }, ValuesNew.ANIMATION_DURATION / 4.toLong())
    }
}