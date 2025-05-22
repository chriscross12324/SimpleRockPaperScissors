package com.simplegames.chris.rockpaperscissors

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.card.MaterialCardView
import java.util.Timer
import kotlin.concurrent.timerTask

class SinglePlayer : AppCompatActivity() {

    //Create Screen Values
    private lateinit var buttonRock: MaterialCardView
    private lateinit var buttonPaper: MaterialCardView
    private lateinit var buttonScissors: MaterialCardView
    private lateinit var buttonMenu: MaterialCardView

    private lateinit var background: ImageView
    private lateinit var opponentChoiceImage: ImageView
    private lateinit var opponentChoiceHolder: ConstraintLayout
    private lateinit var roundResult: TextView
    private lateinit var roundResultHolder: MaterialCardView

    private lateinit var opponentAnimation: AnimationDrawable
    private var isPlaying: Boolean = false
    private var opponentChoice: Int = -1
    private var playerChoice: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show()
        //Set Screen Theme
        if (ValuesNew.darkThemeEnabled) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }

        //Set Content View
        setContentView(R.layout.activity_single_player)

        //Set Screen Values
        buttonMenu = findViewById(R.id.buttonMenu)
        buttonRock = findViewById(R.id.rockButton)
        buttonPaper = findViewById(R.id.paperButton)
        buttonScissors = findViewById(R.id.scissorsButton)

        background = findViewById(R.id.background)
        opponentChoiceImage = findViewById(R.id.aiChoice)
        opponentChoiceHolder = findViewById(R.id.aiResult)
        roundResult = findViewById(R.id.resultText)
        roundResultHolder = findViewById(R.id.resultHolder)

        opponentAnimation = opponentChoiceImage.drawable as AnimationDrawable
        UIElements.setBackground(background, UIElements.getBackgroundColours(this), 0f)

        //Animate Elements In
        animateIn()
        Log.e("ERR RPS: ", "Created")
        //Button Presses
        buttonMenu.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
                YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(buttonMenu)
            } else {
                //Stop multiple clicks
                buttonMenu.isClickable = false

                //Vibrate
                vibrate(this, VibrationType.MEDIUM)

                //Animate UI Out
                UIElements.animate(
                    buttonMenu,
                    "translationY",
                    -(buttonMenu.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonRock.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    roundResultHolder,
                    "translationY",
                    -(roundResultHolder.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    opponentChoiceHolder,
                    "translationY",
                    60,
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    opponentChoiceHolder,
                    "alpha",
                    0,
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )

                //Open Settings
                Timer().schedule(timerTask {
                    val pageSettings = Intent(this@SinglePlayer, Settings::class.java)
                    startActivity(pageSettings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    finish()
                    this@SinglePlayer.overridePendingTransition(0, 0)
                }, ValuesNew.ANIMATION_DURATION.toLong())
            }
        }

        buttonRock.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)

                YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(buttonRock)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = 0 //Rock

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    50,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }

        buttonPaper.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
                YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(buttonPaper)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = 1 //Paper

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }

        buttonScissors.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)

                YoYo.with(Techniques.Shake)
                    .duration(250)
                    .playOn(buttonScissors)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = 2 //Scissors

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    50,
                    ValuesNew.ANIMATION_DURATION,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }
    }

    private fun playOpponentAnimation() {
        //Play Picking Animation
        /*opponentChoiceImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ai_picking, null))
        val pickingAnimation = opponentChoiceImage.drawable as AnimationDrawable
        pickingAnimation.start()*/

        //Hide Result Holder
        Handler(Looper.getMainLooper()).postDelayed({
            UIElements.animate(
                roundResultHolder,
                "translationY",
                -(roundResultHolder.height + UIElements.dpToFloat(10f)),
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
        }, 2000)

        var accumulatedDelay = 0L
        var previousImage = 1

        for (i in 1..30) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (i < 30) {
                    vibrate(this, VibrationType.WEAK)

                    var rand = (0..2).random()
                    if (rand == previousImage) rand++
                    if (rand > 2) rand = 0
                    previousImage = rand
                    when (rand) {
                        0 -> opponentChoiceImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_rock, null))
                        1 -> opponentChoiceImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_paper, null))
                        2 -> opponentChoiceImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_scissors, null))
                    }

                } else {
                    vibrate(this, VibrationType.MEDIUM)

                    //Display Winner
                    displayWinner()
                    //pickingAnimation.stop()
                    Handler(Looper.getMainLooper()).postDelayed({
                        isPlaying = false
                    }, ValuesNew.ANIMATION_DURATION.toLong() / 2)
                }
            }, accumulatedDelay)
            accumulatedDelay += calculateDuration(i).toLong()
        }
    }

    private fun calculateDuration(loopCount: Int): Int {
        val listOfDurations = listOf(
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
            100, 100, 100, 100, 100, 110, 120, 130, 140, 150,
            200, 250, 300, 350, 400, 450, 500, 600, 700, 800
        )

        return listOfDurations.elementAt(loopCount - 1)
    }

    private fun opponentPick() {
        val possibleChoices = arrayListOf(0, 1, 2)
        val rules = mapOf(0 to 2, 1 to 0, 2 to 1)

        opponentChoice = if ((0..1).random() == 1) {
            if ((0..1).random() == 1) {
                //Remove chance of AI Loss
                rules[playerChoice]?.let { possibleChoices.removeAt(it) }
            } else {
                //Remove chance of tie
                possibleChoices.removeAt(playerChoice)
            }
            possibleChoices.random()
        } else {
            //Have full range of choices
            (0..2).random()
        }

        //Show Animation
        playOpponentAnimation()
    }

    private fun displayOpponentChoice() {
        when (opponentChoice) {
            0 -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_rock)
            }

            1 -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_paper)
            }

            2 -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_scissors)
            }

            else -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_info)
            }
        }
    }

    private fun displayWinner() {
        displayOpponentChoice()
        val rules = mapOf(0 to 2, 1 to 0, 2 to 1)
        if (playerChoice == opponentChoice) {
            roundResult.text = getString(R.string.game_result_draw)
            ValuesNew.userDraws++
        } else if (rules[playerChoice] == opponentChoice) {
            roundResult.text = getString(R.string.game_result_win)
            ValuesNew.userWins++
        } else {
            roundResult.text = getString(R.string.game_result_lose)
            ValuesNew.userLosses++
        }

        //Animate Back In
        UIElements.animate(
            buttonRock,
            "translationY",
            0,
            0,
            ValuesNew.ANIMATION_DURATION,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            buttonPaper,
            "translationY",
            0,
            0,
            ValuesNew.ANIMATION_DURATION,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            buttonScissors,
            "translationY",
            0,
            50,
            ValuesNew.ANIMATION_DURATION,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            roundResultHolder,
            "translationY",
            0,
            0,
            ValuesNew.ANIMATION_DURATION,
            DecelerateInterpolator(3f)
        )
    }

    private fun animateIn() {
        Log.e("ERR RPS: ", "Animating In")
        background.post {
            Log.e("ERR RPS: ", "UI There")
            //Set Elements Initial Positions/Alphas
            buttonMenu.translationY = (-(buttonMenu.height + UIElements.dpToFloat(10f)).toFloat())
            buttonRock.translationY = ((buttonRock.height + UIElements.dpToFloat(10f)).toFloat())
            buttonPaper.translationY = ((buttonPaper.height + UIElements.dpToFloat(10f)).toFloat())
            buttonScissors.translationY =
                ((buttonScissors.height + UIElements.dpToFloat(10f)).toFloat())
            opponentChoiceHolder.translationY = UIElements.dpToFloat(60f).toFloat()
            opponentChoiceHolder.alpha = 0f
            roundResultHolder.translationY =
                (-(roundResultHolder.height + UIElements.dpToFloat(10f)).toFloat())

            //Set Elements VISIBLE
            buttonMenu.visibility = View.VISIBLE
            buttonRock.visibility = View.VISIBLE
            buttonPaper.visibility = View.VISIBLE
            buttonScissors.visibility = View.VISIBLE
            opponentChoiceHolder.visibility = View.VISIBLE
            roundResultHolder.visibility = View.VISIBLE

            //Animate Elements
            UIElements.animate(
                buttonMenu,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonRock,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonPaper,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonScissors,
                "translationY",
                0,
                0,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentChoiceHolder,
                "translationY",
                0,
                100,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentChoiceHolder,
                "alpha",
                1,
                100,
                ValuesNew.ANIMATION_DURATION,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                roundResultHolder,
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