package com.simplegames.chris.rockpaperscissors.activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.TRANSLATION_Y
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.card.MaterialCardView
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.utils.UIElements
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities.ViewProperty
import com.simplegames.chris.rockpaperscissors.utils.Values
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew
import com.simplegames.chris.rockpaperscissors.utils.VibrationType
import com.simplegames.chris.rockpaperscissors.utils.vibrate
import java.util.Timer
import kotlin.concurrent.timerTask

enum class Choice(val value: Int) {
    ROCK(0), PAPER(1), SCISSORS(2);

    companion object {
        fun fromInt(value: Int) = Choice.entries.first { it.value == value }
        fun getRandom(exclude: Choice? = null): Choice {
            val choices = Choice.entries.filter { it != exclude }
            return choices.random()
        }
    }
}

class GameActivity : AppCompatActivity() {

    //Create Screen Values
    private lateinit var btnRock: MaterialCardView
    private lateinit var btnPaper: MaterialCardView
    private lateinit var btnScissors: MaterialCardView
    private lateinit var btnMenu: MaterialCardView

    private lateinit var background: CardView
    private lateinit var opponentImage: ImageView
    private lateinit var opponentContainer: ConstraintLayout
    private lateinit var resultText: TextView
    private lateinit var resultContainer: MaterialCardView

    private lateinit var choiceDrawables: Map<Choice, Drawable?>

    private var isPlaying: Boolean = false
    private lateinit var playerChoice: Choice
    private lateinit var opponentChoice: Choice;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (ValuesNew.darkThemeEnabled) R.style.DarkTheme else R.style.LightTheme)
        setContentView(R.layout.activity_single_player)

        initializeUI()
        setupListeners()
    }

    private fun initializeUI() {
        btnMenu = findViewById(R.id.buttonMenu)
        btnRock = findViewById(R.id.rockButton)
        btnPaper = findViewById(R.id.paperButton)
        btnScissors = findViewById(R.id.scissorsButton)

        background = findViewById(R.id.background)
        opponentImage = findViewById(R.id.aiChoice)
        opponentContainer = findViewById(R.id.aiResult)
        resultText = findViewById(R.id.resultText)
        resultContainer = findViewById(R.id.resultHolder)

        UIElements.setBackground(background, UIElements.getBackgroundColours(this), 20f)

        choiceDrawables = mapOf<Choice, Drawable?>(
            Choice.ROCK to ResourcesCompat.getDrawable(resources, R.drawable.icon_rock, null),
            Choice.PAPER to ResourcesCompat.getDrawable(resources, R.drawable.icon_paper, null),
            Choice.SCISSORS to ResourcesCompat.getDrawable(
                resources,
                R.drawable.icon_scissors,
                null
            )
        )

        animateIn()
    }

    private fun setupListeners() {
        btnMenu.setOnClickListener { handleMenuClick() }
        btnRock.setOnClickListener { handlePlayerChoice(Choice.ROCK, btnRock) }
        btnPaper.setOnClickListener { handlePlayerChoice(Choice.PAPER, btnPaper) }
        btnScissors.setOnClickListener { handlePlayerChoice(Choice.SCISSORS, btnScissors) }
    }

    private fun handleMenuClick() {
        if (isPlaying) {
            vibrate(this, VibrationType.ERROR)
            YoYo.with(Techniques.Shake)
                .duration(250)
                .playOn(btnMenu)
            return
        }

        btnMenu.isClickable = false
        vibrate(this, VibrationType.MEDIUM)

        listOf(btnMenu, resultContainer).forEach {
            UIUtilities.animate(
                it,
                ViewProperty.TRANSLATION_Y,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f),
                -(it.height + UIElements.dpToFloat(10f)).toFloat()
            )
        }
        listOf(btnRock, btnPaper, btnScissors).forEach {
            UIUtilities.animate(
                it,
                ViewProperty.TRANSLATION_Y,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f),
                (it.height + UIElements.dpToFloat(10f)).toFloat()
            )
        }
        UIElements.animate(
            opponentContainer,
            "translationY",
            60,
            0, ValuesNew.ANIMATION_DURATION, DecelerateInterpolator(3f)
        )
        UIElements.animate(
            opponentContainer,
            "alpha",
            0,
            0, ValuesNew.ANIMATION_DURATION / 2, DecelerateInterpolator(3f)
        )

        Timer().schedule(timerTask {
            startActivity(
                Intent(
                    this@GameActivity,
                    SettingsActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            )
            finish()
            this@GameActivity.overridePendingTransition(0, 0)
        }, ValuesNew.ANIMATION_DURATION.toLong())
    }

    private fun handlePlayerChoice(choice: Choice, view: View) {
        if (isPlaying) {
            vibrate(this, VibrationType.ERROR)
            YoYo.with(Techniques.Shake)
                .duration(250)
                .playOn(view)
            return
        }

        isPlaying = true
        playerChoice = choice
        vibrate(this, VibrationType.WEAK)

        listOf(btnRock, btnPaper, btnScissors).forEach {
            if (it != view) {
                UIElements.animate(
                    it,
                    "translationY",
                    (it.height + UIElements.dpToFloat(10f)),
                    0, ValuesNew.ANIMATION_DURATION, DecelerateInterpolator(3f)
                )
            }
        }

        opponentChoice = generateOpponentChoice()
        animateOpponentChoiceSequence()
    }

    private fun generateOpponentChoice(): Choice {
        val winMap = mapOf(
            Choice.ROCK to Choice.SCISSORS,
            Choice.PAPER to Choice.ROCK,
            Choice.SCISSORS to Choice.PAPER
        )
        val choices = Choice.entries.toMutableList()
        return if ((0..1).random() == 1) {
            if ((0..1).random() == 1) choices.remove(winMap[playerChoice]) else choices.remove(
                playerChoice
            )
            choices.random()
        } else {
            Choice.entries.random()
        }
    }

    private fun animateOpponentChoiceSequence() {
        Handler(Looper.getMainLooper()).postDelayed({
            UIElements.animate(
                resultContainer,
                "translationY",
                -(resultContainer.height + UIElements.dpToFloat(10f)),
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
        }, 2000L)
        playAnimationStep(0, null)
    }

    private fun playAnimationStep(step: Int, lastChoice: Choice?) {
        val totalSteps = 30
        val delay = getFrameDelay(step).toLong()

        Handler(Looper.getMainLooper()).postDelayed({
            if (step < totalSteps) {
                vibrate(this, VibrationType.WEAK)
                val newChoice = Choice.getRandom(lastChoice)
                opponentImage.setImageDrawable(choiceDrawables[newChoice])
                playAnimationStep(step + 1, newChoice)
            } else {
                vibrate(this, VibrationType.MEDIUM)
                showResult()
                Handler(Looper.getMainLooper()).postDelayed(
                    { isPlaying = false },
                    ValuesNew.ANIMATION_DURATION / 2L
                )
            }
        }, delay)
    }

    private fun getFrameDelay(step: Int): Int {
        val durations = listOf(
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
            100, 100, 100, 100, 100, 110, 120, 130, 140, 150,
            200, 250, 300, 350, 400, 450, 500, 600, 700, 800
        )

        return durations.getOrElse(step) { 800 }
    }

    private fun showResult() {
        opponentImage.setImageDrawable(choiceDrawables[opponentChoice])
        val resultTextString = when {
            playerChoice == opponentChoice -> {
                ValuesNew.userDraws++
                getString(R.string.game_result_draw)
            }

            mapOf(
                Choice.ROCK to Choice.SCISSORS,
                Choice.PAPER to Choice.ROCK,
                Choice.SCISSORS to Choice.PAPER
            )[playerChoice] == opponentChoice -> {
                ValuesNew.userWins++
                getString(R.string.game_result_win)
            }

            else -> {
                ValuesNew.userLosses++
                getString(R.string.game_result_lose)
            }
        }

        resultText.text = resultTextString

        listOf(btnRock, btnPaper, btnScissors, resultContainer).forEach {
            UIElements.animate(
                it,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
        }
    }

    private fun displayOpponentChoice() {
        when (opponentChoice) {
            Choice.ROCK -> {
                opponentImage.setImageResource(R.drawable.icon_rock)
            }

            Choice.PAPER -> {
                opponentImage.setImageResource(R.drawable.icon_paper)
            }

            Choice.SCISSORS -> {
                opponentImage.setImageResource(R.drawable.icon_scissors)
            }
        }
    }

    private fun animateIn() {
        Log.e("ERR RPS: ", "Animating In")
        background.post {
            Log.e("ERR RPS: ", "UI There")
            //Set Elements Initial Positions/Alphas
            btnMenu.translationY = (-(btnMenu.height + UIElements.dpToFloat(10f)).toFloat())
            btnRock.translationY = ((btnRock.height + UIElements.dpToFloat(10f)).toFloat())
            btnPaper.translationY = ((btnPaper.height + UIElements.dpToFloat(10f)).toFloat())
            btnScissors.translationY =
                ((btnScissors.height + UIElements.dpToFloat(10f)).toFloat())
            opponentContainer.translationY = UIElements.dpToFloat(60f).toFloat()
            opponentContainer.alpha = 0f
            resultContainer.translationY =
                (-(resultContainer.height + UIElements.dpToFloat(10f)).toFloat())

            //Set Elements VISIBLE
            btnMenu.visibility = View.VISIBLE
            btnRock.visibility = View.VISIBLE
            btnPaper.visibility = View.VISIBLE
            btnScissors.visibility = View.VISIBLE
            opponentContainer.visibility = View.VISIBLE
            resultContainer.visibility = View.VISIBLE

            //Animate Elements
            UIElements.animate(
                btnMenu,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                btnRock,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                btnPaper,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                btnScissors,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentContainer,
                "translationY",
                0,
                100,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentContainer,
                "alpha",
                1,
                100,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                resultContainer,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )

            //Set currentActivity
            Values.currentActivity = "SinglePlayer"
        }
    }

}