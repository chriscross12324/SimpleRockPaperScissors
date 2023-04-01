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

import com.google.android.material.card.MaterialCardView;

public class SinglePlayer extends AppCompatActivity {

    LinearLayout Arrow;
    MaterialCardView scissorsButton, rockButton, paperButton, buttonMenu, resultHolder;
    ConstraintLayout background;
    FrameLayout aiResult;
    ImageView aiChoice;
    TextView resultText, lossesScore, drawsScore, winsScore;
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
        buttonMenu = findViewById(R.id.buttonMenu);
        resultHolder = findViewById(R.id.resultHolder);
        aiChoice = findViewById(R.id.aiChoice);
        aiAnimation = (AnimationDrawable) aiChoice.getDrawable();
        aiAnimation.setEnterFadeDuration(0);
        aiAnimation.setExitFadeDuration(0);

        final Handler animPlay = new Handler();
        resultText = findViewById(R.id.resultText);
        lossesScore = findViewById(R.id.lossesScore);
        drawsScore = findViewById(R.id.drawsScore);
        winsScore = findViewById(R.id.winsScore);
        openGame();
        sendBackgroundRequest();
        resetScoreCounter();
        singlePlayerIN();

        /**
         *
         *          What happens when buttons are pressed.
         *
         */
        buttonMenu.setOnClickListener(v -> {
            if (!isPlaying){
                buttonMenu.setClickable(false);
                Vibrations.openGameMode(SinglePlayer.this);
                UIElements.animate(aiResult, "translationY", 60, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(aiResult, "alpha", 0, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(resultHolder, "translationY", -resultHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(buttonMenu, "alpha", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(paperButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.settingsNew(SinglePlayer.this);
                        finish();
                        SinglePlayer.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed);

            }else {
                Vibrations.UnavailableVibration(getApplicationContext());
                LayoutInflater layoutInflater = getLayoutInflater();
                View cantExitView = layoutInflater.inflate(R.layout.toast_in_game, (ViewGroup)findViewById(R.id.cantExitToast));
                Toast cantExitToast = Toast.makeText(SinglePlayer.this, "Can't Leave", Toast.LENGTH_SHORT);
                cantExitToast.setGravity(Gravity.CENTER,0,0);
                cantExitToast.setView(cantExitView);
                cantExitToast.show();
            }
        });

        rockButton.setOnClickListener(v -> {
            buttonHeight = rockButton.getHeight();
            if (!isPlaying){
                isPlaying = true;
                Vibrations.SPButtonSelectedVibration(getApplicationContext());
                playerReturn = "Rock";
                playAnimation();
                aiPick();
                winnerChosen();
                resultAnimation();
                //UIElements.animate(rockButton, "translationY", -50, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(paperButton, "translationY", buttonHeight, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                animPlay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aiAnimation.stop();
                        aiShow();
                        isPlaying = false;
                        //UIElements.animate(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                        UIElements.animate(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                        UIElements.animate(scissorsButton, "translationY", 0, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                    }
                },6050);
            } else {
                Vibrations.SPAlreadyPlayingVibration(getApplicationContext());
            }


        });
        paperButton.setOnClickListener(v -> {
            buttonHeight = rockButton.getHeight();
            if (!isPlaying){
                isPlaying = true;
                Vibrations.SPButtonSelectedVibration(getApplicationContext());
                playerReturn = "Paper";
                playAnimation();
                aiPick();
                winnerChosen();
                resultAnimation();
                UIElements.animate(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(scissorsButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                animPlay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        aiAnimation.stop();
                        aiShow();
                        isPlaying = false;
                        UIElements.animate(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                        UIElements.animate(scissorsButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    }
                },6050);
            } else {
                Vibrations.SPAlreadyPlayingVibration(getApplicationContext());
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
                    playAnimation();
                    aiPick();
                    winnerChosen();
                    resultAnimation();
                    UIElements.animate(rockButton, "translationY", buttonHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.animate(paperButton, "translationY", buttonHeight, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                    animPlay.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aiAnimation.stop();
                            aiShow();
                            isPlaying = false;
                            UIElements.animate(rockButton, "translationY", 0, 50, Values.animationSpeed, new DecelerateInterpolator(3));
                            UIElements.animate(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
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
            resultText.setText("Draw");
            Values.draws++;
            drawsScore.setText("Draws: "+ Values.draws);
            //aiSmarter();
        } else if (winnerReturn == "PlayerWin"){
            resultText.setText("You Win");
            Values.wins++;
            winsScore.setText("Wins: "+ Values.wins);
            //aiSmarter();
        } else if (winnerReturn == "AiWin"){
            resultText.setText("You Lose");
            Values.losses++;
            lossesScore.setText("Losses: "+ Values.losses);
            //aiSmarter();
        }
        if (Values.losses < 0 || Values.draws < 0 || Values.wins < 0){
        }
    }

    /**
     *          Plays the animation of the result box
     *          and displays the correct winner.
     *
     */
    public void resultAnimation(){
        Handler outDelay = new Handler();
        final Handler changeText = new Handler();
        final Handler inDelay = new Handler();
        //final Animation out = AnimationUtils.loadAnimation(this, R.anim.result_animation_out);
        //final Animation in = AnimationUtils.loadAnimation(this, R.anim.result_animation_in);
        outDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                UIElements.animate(resultHolder, "translationY", -resultHeight, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                changeText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        inDelay.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                winnerDisplay();
                                UIElements.animate(resultHolder, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
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
                resultHeight = resultHolder.getHeight() + resultHolder.getPaddingTop();
                Log.e("NOTE", ""+buttonHeight);
                aiResult.setTranslationY(60);
                aiResult.setAlpha(0f);
                resultHolder.setTranslationY(-resultHeight);
                buttonMenu.setAlpha(0f);
                rockButton.setTranslationY(buttonHeight);
                paperButton.setTranslationY(buttonHeight);
                scissorsButton.setTranslationY(buttonHeight);
                UIElements.animate(aiResult, "translationY", 0, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(aiResult, "alpha", 1, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(buttonMenu, "alpha", 1, 100, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(resultHolder, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(rockButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(paperButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                UIElements.animate(scissorsButton, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
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


    @Override
    public void onBackPressed(){
    }

}
