package com.simplegames.chris.rockpaperscissors20;

import static com.simplegames.chris.rockpaperscissors20.VibrationsKt.vibrate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Objects;

public class Settings extends AppCompatActivity {
    private ArrayList<SettingsButton> buttonArrayList;
    private RecyclerView buttonRecyclerView;
    private SettingsButtonAdapter buttonAdapter;
    private RecyclerView.LayoutManager buttonLayoutManager;

    boolean doublePressReset;
    int height;

    NestedScrollView settingsScrollView;
    MaterialCardView buttonVibrate, buttonDarkTheme, buttonBack, buttonAppInfo;
    ImageView vibrationIcon, background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ValuesNew.INSTANCE.getDarkThemeEnabled()) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_settings);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

        //Set Views
        background = findViewById(R.id.background);
        settingsScrollView = findViewById(R.id.settingsScrollView);
        buttonVibrate = findViewById(R.id.buttonVibrate);
        buttonDarkTheme = findViewById(R.id.buttonDarkTheme);
        buttonAppInfo = findViewById(R.id.buttonAppInfo);
        buttonBack = findViewById(R.id.buttonBack);
        vibrationIcon = findViewById(R.id.vibrationIcon);

        //Option Buttons
        buttonBack.setOnClickListener(v -> {
            buttonBack.setClickable(false);
            vibrate(Settings.this, VibrationType.WEAK);
            settingsScrollView.smoothScrollTo(0, 0, 500);
            UIElements.animate(settingsScrollView, "translationY", height, 0, ValuesNew.INSTANCE.getAnimationDuration(), new AccelerateInterpolator(3));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent sp = new Intent(Settings.this, SinglePlayer.class);
                startActivity(sp);
                finish();
                Settings.this.overridePendingTransition(0, 0);
            }, ValuesNew.INSTANCE.getAnimationDuration());
        });
        buttonVibrate.setOnClickListener(v -> {
            ValuesNew.INSTANCE.setVibrationEnabled(!ValuesNew.INSTANCE.getVibrationEnabled());
            ValuesNew.INSTANCE.saveValue(this, SharedPreferenceKeys.INSTANCE.getKeySettingVibrations(), ValuesNew.INSTANCE.getVibrationEnabled());
            determineOptionsStates();
            vibrate(getApplicationContext(), VibrationType.WEAK);
        });
        buttonDarkTheme.setOnClickListener(v -> {
            ValuesNew.INSTANCE.setDarkThemeEnabled(!ValuesNew.INSTANCE.getDarkThemeEnabled());
            ValuesNew.INSTANCE.saveValue(this, SharedPreferenceKeys.INSTANCE.getKeySettingTheme(), ValuesNew.INSTANCE.getDarkThemeEnabled());
            resetLayout();
            vibrate(getApplicationContext(), VibrationType.WEAK);
        });

        buttonAppInfo.setOnClickListener(v -> {
            vibrate(Settings.this, VibrationType.WEAK);
            settingsScrollView.smoothScrollTo(0, 0, 500);
            UIElements.animate(settingsScrollView, "translationY", height, 0, ValuesNew.INSTANCE.getAnimationDuration(), new AccelerateInterpolator(3));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent appInfo = new Intent(Settings.this, AppInfo.class);
                startActivity(appInfo.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                Settings.this.overridePendingTransition(0, 0);
            }, ValuesNew.INSTANCE.getAnimationDuration());
        });
        determineBackground();
        getWindow().getDecorView().post(this::settingsScrollViewAnimation);
        setBackgroundButtons();
        determineOptionsStates();
        buildBackgroundButtonRecyclerView();

    }

    private void determineOptionsStates() {
        if (!ValuesNew.INSTANCE.getVibrationEnabled()) {
            UIElements.setBackground(this, buttonVibrate, new int[]{
                            ContextCompat.getColor(this, R.color.disabledOption),
                            ContextCompat.getColor(this, R.color.disabledOption)},
                    UIElements.dpToFloat(15f), 0);
            if (ValuesNew.INSTANCE.getDarkThemeEnabled()) {
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
            } else {
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));
            }
        } else {
            UIElements.setBackground(this, buttonVibrate, new int[]{
                            ContextCompat.getColor(this, R.color.enabledOption),
                            ContextCompat.getColor(this, R.color.enabledOption)},
                    UIElements.dpToFloat(15f), 0);
            vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }
        if (!ValuesNew.INSTANCE.getDarkThemeEnabled()) {
            UIElements.setBackground(this, buttonDarkTheme, new int[]{
                            ContextCompat.getColor(this, R.color.disabledOption),
                            ContextCompat.getColor(this, R.color.disabledOption)},
                    UIElements.dpToFloat(15f), 0);
        } else {
            UIElements.setBackground(this, buttonDarkTheme, new int[]{
                            ContextCompat.getColor(this, R.color.enabledOption),
                            ContextCompat.getColor(this, R.color.enabledOption)},
                    UIElements.dpToFloat(15f), 0);
        }
    }

    public void setBackgroundButtons() {
        buttonArrayList = new ArrayList<>();
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.wintersDayTL),
                ContextCompat.getColor(this, R.color.wintersDayBR), "Winters Day"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.shallowLakeTL),
                ContextCompat.getColor(this, R.color.shallowLakeBR), "Shallow Lake"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.tropicalOceanTL),
                ContextCompat.getColor(this, R.color.tropicalOceanBR), "Tropical Ocean"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.greenGrassTL),
                ContextCompat.getColor(this, R.color.greenGrassBR), "Green Grass"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.sunshineTL),
                ContextCompat.getColor(this, R.color.sunshineBR), "Sunshine"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.forestTL),
                ContextCompat.getColor(this, R.color.forestBR), "Forest"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.atmosphereTL),
                ContextCompat.getColor(this, R.color.atmosphereBR), "Atmosphere"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.purpleHazeTL),
                ContextCompat.getColor(this, R.color.purpleHazeBR), "Purple Haze"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.coldNightTL),
                ContextCompat.getColor(this, R.color.coldNightBR), "Cold Night"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.eternalSpaceTL),
                ContextCompat.getColor(this, R.color.eternalSpaceBR), "Eternal Space"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.juicyPomegranateTL),
                ContextCompat.getColor(this, R.color.juicyPomegranateBR), "Juicy Pomegranate"));
        buttonArrayList.add(new SettingsButton(ContextCompat.getColor(this, R.color.sunnyDepthsTL),
                ContextCompat.getColor(this, R.color.sunnyDepthsBR), "Sunny Depths"));


    }

    public void buildBackgroundButtonRecyclerView() {
        buttonRecyclerView = findViewById(R.id.backgroundRecyclerView);
        buttonRecyclerView.setHasFixedSize(true);
        buttonLayoutManager = new LinearLayoutManager(this);
        buttonAdapter = new SettingsButtonAdapter(buttonArrayList, this);

        buttonRecyclerView.setLayoutManager(buttonLayoutManager);
        buttonRecyclerView.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemClickListener(position -> {
        });
    }

    public void determineBackground() {
        if (Objects.equals(Values.currentActivity, "Settings")) {
            vibrate(getApplicationContext(), VibrationType.WEAK);
        }
        UIElements.setBackground(this, background, UIElements.getBackgroundColours(this), 0f, 5000);
    }

    public void settingsScrollViewAnimation() {
        settingsScrollView.setVisibility(View.VISIBLE);
        if (Objects.equals(Values.currentActivity, "Settings")) {
            settingsScrollView.scrollTo(0, 0);
        } else {
            settingsScrollView.scrollTo(0, 0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            settingsScrollView.setY(height);
            UIElements.animate(settingsScrollView, "translationY", 0, 0, ValuesNew.INSTANCE.getAnimationDuration(), new DecelerateInterpolator(3));
            Values.currentActivity = "Settings";
        }
    }

    public void resetLayout() {
        NestedScrollView settingsScrollView = findViewById(R.id.settingsScrollView);
        settingsScrollView.smoothScrollTo(0, 0, ValuesNew.INSTANCE.getAnimationDuration() / 4);
        new Handler().postDelayed(() -> {
            Intent sp = new Intent(Settings.this, Settings.class);
            startActivity(sp);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, ValuesNew.INSTANCE.getAnimationDuration() / 4);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
