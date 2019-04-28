package com.simplegames.chris.rockpaperscissors20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MenuTest extends AppCompatActivity {

    float cornerRadius = 50f;
    int buttonWidths;
    int delaySpeed = 200;
    int animationDuration = 600;

    ConstraintLayout SPB, ONMPB, OFFMPB, SB, HB;
    LinearLayout SPBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_new);
        determineButtonGradient();
        determineBackground();

        SPB = findViewById(R.id.singlePlayerButton);
        ONMPB = findViewById(R.id.onlineMultiplayerButton);
        OFFMPB = findViewById(R.id.offlineMultiplayerButton);
        SB = findViewById(R.id.settingsButton);
        HB = findViewById(R.id.helpButton);

        SPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPBOut(delaySpeed);
                ONMPBOut(0);
                OFFMPBOut(0);
                SBOut(0);
                HBOut(0);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.singlePlayer(MenuTest.this);
                        finish();
                        MenuTest.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed + 200);
            }
        });
        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPBOut(0);
                ONMPBOut(0);
                OFFMPBOut(0);
                SBOut(delaySpeed);
                HBOut(0);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.settingsNew(MenuTest.this);
                        finish();
                        MenuTest.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed + 200);
            }
        });
        HB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPBOut(0);
                ONMPBOut(0);
                OFFMPBOut(0);
                SBOut(0);
                HBOut(delaySpeed);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.help(MenuTest.this);
                        finish();
                        MenuTest.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed + 200);
            }
        });

        mainMenuInAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Values.currentActivity = "MainMenu";
            }
        }, 2000);
    }

    public void determineButtonGradient(){
        ConstraintLayout SPB = findViewById(R.id.singlePlayerButton);
        ConstraintLayout ONMPB = findViewById(R.id.onlineMultiplayerButton);
        ConstraintLayout OFFMPB = findViewById(R.id.offlineMultiplayerButton);
        ConstraintLayout SB = findViewById(R.id.settingsButton);
        ConstraintLayout HB = findViewById(R.id.helpButton);
        setButtonGradient(null, SPB, Color.parseColor(getResources().getString(0+R.color.SPGradientTopLeft)),
                Color.parseColor(getResources().getString(0+R.color.SPGradientBottomRight)), new float[]{cornerRadius, cornerRadius, 0, 0, 0, 0, cornerRadius, cornerRadius});
        setButtonGradient(null, ONMPB, Color.parseColor(getResources().getString(0+R.color.ONMPGradientTopLeft)),
                Color.parseColor(getResources().getString(0+R.color.ONMPGradientBottomRight)), new float[]{0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0});
        setButtonGradient(null, OFFMPB, Color.parseColor(getResources().getString(0+R.color.OFFMPGradientTopLeft)),
                Color.parseColor(getResources().getString(0+R.color.OFFMPGradientBottomRight)), new float[]{0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0});
        setButtonGradient(null, SB, Color.parseColor(getResources().getString(0+R.color.settingsBBLeft)),
                Color.parseColor(getResources().getString(0+R.color.settingsBBRight)), new float[]{cornerRadius, cornerRadius, 0, 0, 0, 0, cornerRadius, cornerRadius});
        setButtonGradient(null, HB, Color.parseColor(getResources().getString(0+R.color.helpBBLeft)),
                Color.parseColor(getResources().getString(0+R.color.helpBBRight)), new float[]{0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0});
    }
    public void setButtonGradient(LinearLayout layout, ConstraintLayout layoutC, int TL, int BR, float[] corner){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {TL, BR});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(corner);
        try {
            layout.setBackground(gradientDrawable);
        } catch (Exception e){
        }
        try {
            layoutC.setBackground(gradientDrawable);
        } catch (Exception e){
        }

    }

    private void determineBackground(){
        ConstraintLayout background = findViewById(R.id.background);
        UIElements.determineBackground(background, null, MenuTest.this);
    }
    public void mainMenuInAnimation(){
        SPB.post(new Runnable() {
            @Override
            public void run() {
                if (Values.currentActivity == "MainMenu"){
                }else {
                    buttonWidths = SPB.getWidth();
                    SPB.setTranslationX(buttonWidths);
                    SB.setTranslationX(buttonWidths);
                    ONMPB.setTranslationX(0 - buttonWidths);
                    OFFMPB.setTranslationX(0 - buttonWidths);
                    HB.setTranslationX(0 - buttonWidths);
                    UIElements.slideAnimation(SPB, "translationX", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimation(ONMPB, "translationX", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimation(OFFMPB, "translationX", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimation(SB, "translationX", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                    UIElements.slideAnimation(HB, "translationX", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
                }
            }
        });
    }

    public void SPBOut(int delay){
        UIElements.slideAnimation(SPB, "translationX", buttonWidths, delay, Values.animationSpeed, new AccelerateInterpolator(3));
    }
    public void SBOut(int delay){
        UIElements.slideAnimation(SB, "translationX", buttonWidths, delay, Values.animationSpeed, new AccelerateInterpolator(3));
    }
    public void ONMPBOut(int delay){
        UIElements.slideAnimation(ONMPB, "translationX", 0 - buttonWidths, delay, Values.animationSpeed, new AccelerateInterpolator(3));
    }
    public void OFFMPBOut(int delay){
        UIElements.slideAnimation(OFFMPB, "translationX", 0 - buttonWidths, delay, Values.animationSpeed, new AccelerateInterpolator(3));
    }
    public void HBOut(int delay){
        UIElements.slideAnimation(HB, "translationX", 0 - buttonWidths, delay, Values.animationSpeed, new AccelerateInterpolator(3));
    }
}
