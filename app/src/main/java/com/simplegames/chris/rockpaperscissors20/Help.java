package com.simplegames.chris.rockpaperscissors20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.DisplayMetrics;
import android.util.Half;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

public class Help extends AppCompatActivity {
    public int height;
    NestedScrollView helpScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Values.darkTheme){
            setTheme(R.style.DarkTheme);
        }else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_help_new);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels+100;
        helpScrollView = findViewById(R.id.helpScrollView);


        ImageView backButton = (ImageView)findViewById(R.id.backButton);
        ImageView infoButton = (ImageView)findViewById(R.id.infoButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpScrollView.smoothScrollTo(0,0);
                UIElements.slideAnimationScrollView(helpScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.mainMenuNew(Help.this);
                        finish();
                        Help.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed);

            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpScrollView.smoothScrollTo(0,0);
                UIElements.slideAnimationScrollView(helpScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.appInfo(Help.this);
                        finish();
                        Help.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed);
            }
        });

        determineBackground();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                helpScrollViewAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Values.currentActivity = "Help";
                    }
                }, 500);
            }
        });
    }

    private void determineBackground(){
        ConstraintLayout background = findViewById(R.id.background);
        UIElements.determineBackground(background, null, Help.this);
    }

    public void helpScrollViewAnimation(){
        helpScrollView.setY(height);
        UIElements.slideAnimationScrollView(helpScrollView, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Help.this.overridePendingTransition(0,0);
            }
        }, Values.animationSpeed);
    }
    @Override
    public void onBackPressed(){
    }
}
