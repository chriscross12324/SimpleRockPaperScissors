package com.simplegames.chris.rockpaperscissors20

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.card.MaterialCardView
import java.util.Random
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
    private var opponentChoice: String = ""
    private var playerChoice: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show()
        //Set Screen Theme
        if (Values.darkThemeEnabled) {
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
        UIElements.setBackground(this, background, UIElements.getBackgroundColours(this), 0f, 0)

        //Animate Elements In
        animateIn()
        Log.e("ERR RPS: ", "Created")
        //Button Presses
        buttonMenu.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
            } else {
                //Stop multiple clicks
                buttonMenu.isClickable = false

                //Vibrate
                vibrate(this, VibrationType.MEDIUM)

                //Animate UI Out
                UIElements.animate(
                    buttonMenu,
                    "alpha",
                    0,
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonRock.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    roundResultHolder,
                    "translationY",
                    -(roundResultHolder.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    opponentChoiceHolder,
                    "translationY",
                    60,
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    opponentChoiceHolder,
                    "alpha",
                    0,
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )

                //Open Settings
                Timer().schedule(timerTask {
                    val pageSettings = Intent(this@SinglePlayer, Settings::class.java)
                    startActivity(pageSettings.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    finish()
                }, Values.animationSpeed.toLong())
            }
        }

        buttonRock.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = "Rock"

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    50,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }

        buttonPaper.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = "Paper"

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonScissors,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }

        buttonScissors.setOnClickListener {
            if (isPlaying) {
                vibrate(this, VibrationType.ERROR)
            } else {
                //Set isPlaying
                isPlaying = true
                playerChoice = "Scissors"

                //Vibrate
                vibrate(this, VibrationType.WEAK)

                //Hide Other Options
                UIElements.animate(
                    buttonRock,
                    "translationY",
                    (buttonPaper.height + UIElements.dpToFloat(10f)),
                    0,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )
                UIElements.animate(
                    buttonPaper,
                    "translationY",
                    (buttonScissors.height + UIElements.dpToFloat(10f)),
                    50,
                    Values.animationSpeed,
                    DecelerateInterpolator(3f)
                )

                //Get Results
                opponentPick()
            }
        }
    }

    private fun playOpponentAnimation() {
        //Play Picking Animation
        opponentChoiceImage.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ai_picking, null))
        val pickingAnimation = opponentChoiceImage.drawable as AnimationDrawable
        pickingAnimation.start()

        //Hide Result Holder
        Handler().postDelayed({
            UIElements.animate(
                roundResultHolder,
                "translationY",
                -(roundResultHolder.height + UIElements.dpToFloat(10f)),
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
        }, 2000)

        //Display Winner
        Handler().postDelayed({
            displayWinner()
            pickingAnimation.stop()
            isPlaying = false
        }, 5900)
    }

    private fun opponentPick() {
        val random = Random()
        val choice = random.nextInt(3)

        opponentChoice = when (choice) {
            0 -> {
                "Rock"
            }

            1 -> {
                "Paper"
            }

            else -> {
                "Scissors"
            }
        }

        //Show Animation
        playOpponentAnimation()
    }

    private fun displayOpponentChoice() {
        when (opponentChoice) {
            "Rock" -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_rock)
            }

            "Paper" -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_paper)
            }

            "Scissors" -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_scissors)
            }

            else -> {
                opponentChoiceImage.setImageResource(R.drawable.icon_app)
            }
        }
    }

    private fun displayWinner() {
        displayOpponentChoice()
        if (opponentChoice == playerChoice) {
            roundResult.text = "Draw"
            Values.draws++
        } else if ((opponentChoice == "Rock" && playerChoice == "Scissors")
            || (opponentChoice == "Paper" && playerChoice == "Rock")
            || (opponentChoice == "Scissors" && playerChoice == "Paper")
        ) {
            roundResult.text = "You Lose"
            Values.losses++
        } else {
            roundResult.text = "You Win"
            Values.wins++
        }

        //Animate Back In
        UIElements.animate(
            buttonRock,
            "translationY",
            0,
            50,
            Values.animationSpeed,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            buttonPaper,
            "translationY",
            0,
            0,
            Values.animationSpeed,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            buttonScissors,
            "translationY",
            0,
            50,
            Values.animationSpeed,
            DecelerateInterpolator(3f)
        )
        UIElements.animate(
            roundResultHolder,
            "translationY",
            0,
            0,
            Values.animationSpeed,
            DecelerateInterpolator(3f)
        )
    }

    private fun animateIn() {
        Log.e("ERR RPS: ", "Animating In")
        background.post {
            Log.e("ERR RPS: ", "UI There")
            //Set Elements Initial Positions/Alphas
            buttonMenu.alpha = 0f
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
                "alpha",
                1,
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonRock,
                "translationY",
                0,
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonPaper,
                "translationY",
                0,
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                buttonScissors,
                "translationY",
                0,
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentChoiceHolder,
                "translationY",
                0,
                100,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                opponentChoiceHolder,
                "alpha",
                1,
                100,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )
            UIElements.animate(
                roundResultHolder,
                "translationY",
                0,
                0,
                Values.animationSpeed,
                DecelerateInterpolator(3f)
            )

            //Set currentActivity
            Values.currentActivity = "SinglePlayer"
        }
    }

}