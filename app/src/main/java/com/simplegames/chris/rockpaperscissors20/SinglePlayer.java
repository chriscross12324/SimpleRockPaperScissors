package com.simplegames.chris.rockpaperscissors20;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class SinglePlayer extends AppCompatActivity {

    LinearLayout rockButton, paperButton, scissorsButton, menuButtonHolder, Arrow;
    ConstraintLayout background;
    FrameLayout aiResult;
    ImageView aiChoice, menuButton;
    TextView result, lossesScore, drawsScore, winsScore;
    AnimationDrawable aiAnimation;
    int resetCounter = 0;
    boolean isPlaying = false;

    String aiReturn = "";
    String playerReturn = "";
    String winnerReturn = "";

    int buttonHeight;
    int resultHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Values.darkTheme){
            setTheme(R.style.DarkTheme);
        }else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_single_player);
        if (this.getTheme().equals(R.style.LightTheme)){
            aiChoice = findViewById(R.id.aiChoice);
            ImageViewCompat.setImageTintList(aiChoice, ColorStateList.valueOf(R.attr.GroupColour));
        }

        aiResult = findViewById(R.id.aiResult);

        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);

        background = findViewById(R.id.background);
        menuButton = findViewById(R.id.menuButton);
        menuButtonHolder = findViewById(R.id.menuButtonHolder);
        aiChoice = findViewById(R.id.aiChoice);
        aiAnimation = (AnimationDrawable) aiChoice.getDrawable();
        aiAnimation.setEnterFadeDuration(0);
        aiAnimation.setExitFadeDuration(0);

        final Handler animPlay = new Handler();
        result = findViewById(R.id.result);
        lossesScore = findViewById(R.id.lossesScore);
        drawsScore = findViewById(R.id.drawsScore);
        winsScore = findViewById(R.id.winsScore);
        openGame();
        sendBackgroundRequest();
        resetScoreCounter();
        singlePlayerIN();


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying){
                    UIElements.slideAnimationFrame(aiResult, "translationY", 60, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationFrame(aiResult, "alpha", 0, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationText(result, "translationY", 0-resultHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(menuButtonHolder, "alpha", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(paperButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Goto.settingsNew(SinglePlayer.this);
                            finish();
                            SinglePlayer.this.overridePendingTransition(0,0);
                        }
                    }, Values.animationSpeed+100);

                }else {
                    Vibrations.UnavailableVibration(getApplicationContext());
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View cantExitView = layoutInflater.inflate(R.layout.toast_in_game, (ViewGroup)findViewById(R.id.cantExitToast));
                    Toast cantExitToast = Toast.makeText(SinglePlayer.this, "Can't Leave", Toast.LENGTH_SHORT);
                    cantExitToast.setGravity(Gravity.CENTER,0,0);
                    cantExitToast.setView(cantExitView);
                    cantExitToast.show();
                }
            }
        });

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHeight = rockButton.getHeight();
                if (!isPlaying){
                    isPlaying = true;
                    Vibrations.SPButtonSelectedVibration(getApplicationContext());
                    playerReturn = "Rock";
                    setRockArrow();
                    playAnimation();
                    aiPick();
                    winnerChosen();
                    resultAnimation();
                    //UIElements.slideAnimationLinear(rockButton, "translationY", -50, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(paperButton, "translationY", buttonHeight, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    animPlay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aiAnimation.stop();
                            aiShow();
                            isPlaying = false;
                            //UIElements.slideAnimationLinear(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                            UIElements.slideAnimationLinear(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                            UIElements.slideAnimationLinear(scissorsButton, "translationY", 0, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                        }
                    },6050);
                } else {
                    Vibrations.SPAlreadyPlayingVibration(getApplicationContext());
                }


            }
        });
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHeight = rockButton.getHeight();
                if (!isPlaying){
                    isPlaying = true;
                    Vibrations.SPButtonSelectedVibration(getApplicationContext());
                    playerReturn = "Paper";
                    setPaperArrow();
                    playAnimation();
                    aiPick();
                    winnerChosen();
                    resultAnimation();
                    UIElements.slideAnimationLinear(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    animPlay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aiAnimation.stop();
                            aiShow();
                            isPlaying = false;
                            UIElements.slideAnimationLinear(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                            UIElements.slideAnimationLinear(scissorsButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                        }
                    },6050);
                } else {
                    Vibrations.SPAlreadyPlayingVibration(getApplicationContext());
                }

            }
        });
        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHeight = rockButton.getHeight();
                if (!isPlaying){
                    isPlaying = true;
                    Vibrations.SPButtonSelectedVibration(getApplicationContext());
                    playerReturn = "Scissors";
                    setScissorsArrow();
                    playAnimation();
                    aiPick();
                    winnerChosen();
                    resultAnimation();
                    UIElements.slideAnimationLinear(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimationLinear(paperButton, "translationY", buttonHeight, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                    animPlay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aiAnimation.stop();
                            aiShow();
                            isPlaying = false;
                            UIElements.slideAnimationLinear(rockButton, "translationY", 0, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                            UIElements.slideAnimationLinear(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                        }
                    },6050);
                } else {
                    Vibrations.SPAlreadyPlayingVibration(getApplicationContext());
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Values.currentActivity = "SinglePlayer";
            }
        }, 2000);
    }

    public void playAnimation(){
        aiChoice.setImageDrawable(getResources().getDrawable(R.drawable.ai_picking));
        AnimationDrawable pickingAnimation = (AnimationDrawable)aiChoice.getDrawable();
        pickingAnimation.start();
    }
    public void aiPick(){
        Random r = new Random();
        int choice = r.nextInt(100)+1;
        if (choice < Values.aiRockPercent){
            aiReturn = "Rock";
        }else if (choice < Values.aiRockPercent + Values.aiPaperPercent){
            aiReturn = "Paper";
        }else if (choice < Values.aiRockPercent + Values.aiPaperPercent + Values.aiScissorsPercent){
            aiReturn = "Scissors";
        }else{aiPick();}
    }
    public void aiShow(){
        if (aiReturn == "Rock"){
            aiChoice.setImageResource(R.drawable.icon_rock);
        } else if (aiReturn == "Paper"){
            aiChoice.setImageResource(R.drawable.icon_paper);
        } else if (aiReturn == "Scissors"){
            aiChoice.setImageResource(R.drawable.icon_scissors);
        }
    }
    public void winnerChosen(){
        if (aiReturn == "Rock" && playerReturn == "Rock"){
            winnerReturn = "Draw";
        } else if (aiReturn == "Rock" && playerReturn == "Paper"){
            winnerReturn = "PlayerWin";
        } else if (aiReturn == "Rock" && playerReturn == "Scissors"){
            winnerReturn = "AiWin";
        }

        if (aiReturn == "Paper" && playerReturn == "Rock"){
            winnerReturn = "AiWin";
        } else if (aiReturn == "Paper" && playerReturn == "Paper"){
            winnerReturn = "Draw";
        } else if (aiReturn == "Paper" && playerReturn == "Scissors"){
            winnerReturn = "PlayerWin";
        }

        if (aiReturn == "Scissors" && playerReturn == "Rock"){
            winnerReturn = "PlayerWin";
        } else if (aiReturn == "Scissors" && playerReturn == "Paper"){
            winnerReturn = "AiWin";
        } else if (aiReturn == "Scissors" && playerReturn == "Scissors"){
            winnerReturn = "Draw";
        }
    }
    public void winnerDisplay(){
        if (winnerReturn == "Draw"){
            result.setText("  Draw  ");
            Values.draws++;
            drawsScore.setText("Draws: "+ Values.draws);
            //aiSmarter();
        } else if (winnerReturn == "PlayerWin"){
            result.setText("  You Win  ");
            Values.wins++;
            winsScore.setText("Wins: "+ Values.wins);
            //aiSmarter();
        } else if (winnerReturn == "AiWin"){
            result.setText("  You Lose  ");
            Values.losses++;
            lossesScore.setText("Losses: "+ Values.losses);
            //aiSmarter();
        }
        if (Values.losses < 0 || Values.draws < 0 || Values.wins < 0){
        }
    }
    public void resultAnimation(){
        Handler outDelay = new Handler();
        final Handler changeText = new Handler();
        final Handler inDelay = new Handler();
        //final Animation out = AnimationUtils.loadAnimation(this, R.anim.result_animation_out);
        //final Animation in = AnimationUtils.loadAnimation(this, R.anim.result_animation_in);
        outDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                UIElements.slideAnimationText(result, "translationY", 0-resultHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                changeText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inDelay.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                winnerDisplay();
                                UIElements.slideAnimationText(result, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                            }
                        }, 3634);
                    }
                }, 400);
            }
        }, 2016);


    }

    public void openGame(){
        try{
            lossesScore.setText("Losses: "+ Values.losses);
            drawsScore.setText("Draws: "+ Values.draws);
            winsScore.setText("Wins: "+ Values.wins);
        }catch (Exception loadFail){
            Toast.makeText(this, "Failed to Load save", Toast.LENGTH_SHORT).show();
        }
    }
    public void sendBackgroundRequest(){
        ConstraintLayout background = findViewById(R.id.background);
        switch (Values.SPBackgroundNumber){
            case 0:
                UIElements.twoPartGradient(background,null, ContextCompat.getColor(this, R.color.wintersDayTL),
                        ContextCompat.getColor(this, R.color.wintersDayBR),0f);
                return;
            case 1:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.shallowLakeTL),
                        ContextCompat.getColor(this, R.color.shallowLakeBR), 0f);
                return;
            case 2:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.tropicalOceanTL),
                        ContextCompat.getColor(this, R.color.tropicalOceanBR), 0f);
                return;
            case 3:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.greenGrassTL),
                        ContextCompat.getColor(this, R.color.greenGrassBR), 0f);
                return;
            case 4:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.sunshineTL),
                        ContextCompat.getColor(this, R.color.sunshineBR), 0f);
                return;
            case 5:
                UIElements.twoPartGradient(background, null,ContextCompat.getColor(this, R.color.forestTL),
                        ContextCompat.getColor(this, R.color.forestBR), 0f);
                return;
            case 6:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.atmosphereTL),
                        ContextCompat.getColor(this, R.color.atmosphereBR), 0f);
                return;
            case 7:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.purpleHazeTL),
                        ContextCompat.getColor(this, R.color.purpleHazeBR), 0f);
                return;
            case 8:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.coldNightTL),
                        ContextCompat.getColor(this, R.color.coldNightBR), 0f);
                return;
            case 9:
                UIElements.twoPartGradient(background,null,ContextCompat.getColor(this, R.color.eternalSpaceTL),
                        ContextCompat.getColor(this, R.color.eternalSpaceBR), 0f);
        }
    }
    public void resetScoreCounter(){
        if (Values.resetScoreSP){
            Values.wins = resetCounter;
            Values.draws = resetCounter;
            Values.losses = resetCounter;
            lossesScore.setText("Losses: " + Values.losses);
            drawsScore.setText("Draws: " + Values.draws);
            winsScore.setText("Wins: " + Values.wins);
            Values.resetScoreSP = false;
        }
    }

    public void singlePlayerIN(){
        rockButton.post(new Runnable() {
            @Override
            public void run() {
                buttonHeight = rockButton.getHeight();
                resultHeight = result.getHeight();
                Log.e("NOTE", ""+buttonHeight);
                aiResult.setTranslationY(60);
                aiResult.setAlpha(0f);
                result.setTranslationY(0-resultHeight);
                menuButtonHolder.setAlpha(0f);
                rockButton.setTranslationY(buttonHeight);
                paperButton.setTranslationY(buttonHeight);
                scissorsButton.setTranslationY(buttonHeight);
                UIElements.slideAnimationFrame(aiResult, "translationY", 0, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationFrame(aiResult, "alpha", 1, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationLinear(menuButtonHolder, "alpha", 1, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationText(result, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationLinear(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationLinear(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.slideAnimationLinear(scissorsButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                Values.currentActivity = "SinglePlayer";
            }
        });

    }

    /*public void aiSmarter(){
        if (playerReturn == "Rock"){
            aiRockPercent = aiRockPercent - 11;
            aiPaperPercent = aiPaperPercent + 22;
            aiScissorsPercent = aiScissorsPercent - 11;
            aiSmarterValueCheck();
        }else if (playerReturn == "Paper"){
            aiRockPercent = aiRockPercent - 11;
            aiPaperPercent = aiPaperPercent - 11;
            aiScissorsPercent = aiScissorsPercent + 22;
            aiSmarterValueCheck();
        }else if (playerReturn == "Scissors"){
            aiRockPercent = aiRockPercent + 22;
            aiPaperPercent = aiPaperPercent - 11;
            aiScissorsPercent = aiScissorsPercent - 11;
            aiSmarterValueCheck();
        }else{
            Toast.makeText(this,"'AI Smarter' Failed",Toast.LENGTH_SHORT).show();
            aiSmarterValueCheck();
        }
        if (winnerReturn == "PlayerWin" || winnerReturn == "Draw" || winnerReturn == "AIWin"){

        }
    }

    public void aiSmarterValueCheck(){
        if (aiRockPercent > 100){
            aiRockPercent = 100;
            aiPaperPercent = 0;
            aiScissorsPercent = 0;
        } else if (aiPaperPercent > 100){
            aiRockPercent = 0;
            aiPaperPercent = 100;
            aiScissorsPercent = 0;
        } else if (aiScissorsPercent > 100){
            aiRockPercent = 0;
            aiPaperPercent = 0;
            aiScissorsPercent = 100;
        }else if (aiRockPercent < 0){
            aiRockPercent = 0;
        }else if (aiPaperPercent < 0){
            aiPaperPercent = 0;
        }else if (aiScissorsPercent < 0){
            aiScissorsPercent = 0;
        }
        else{
            //aiRockPercent = 33;
            //aiPaperPercent = 33;
            //aiScissorsPercent = 33;
        }
    }*/


    public void setRockArrow(){
        Arrow = findViewById(R.id.Arrow);
        rockButton = findViewById(R.id.rockButton);

        Arrow.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Arrow, "x", rockButton.getX());
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.start();
        Arrow.getLayoutParams().width = rockButton.getWidth();
        Arrow.requestLayout();
    }
    public void setPaperArrow(){
        Arrow = findViewById(R.id.Arrow);
        paperButton = findViewById(R.id.paperButton);

        Arrow.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Arrow, "x", paperButton.getX());
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.start();
        Arrow.getLayoutParams().width = paperButton.getWidth();
        Arrow.requestLayout();
    }
    public void setScissorsArrow(){
        Arrow = findViewById(R.id.Arrow);
        scissorsButton = findViewById(R.id.scissorsButton);

        Arrow.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Arrow, "x", scissorsButton.getX());
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.setDuration(600);
        objectAnimator.start();
        Arrow.getLayoutParams().width = scissorsButton.getWidth();
        Arrow.requestLayout();
    }


    @Override
    public void onBackPressed(){
    }

}
