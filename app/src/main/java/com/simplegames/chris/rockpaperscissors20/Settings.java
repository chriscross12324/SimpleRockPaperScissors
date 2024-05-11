package com.simplegames.chris.rockpaperscissors20;

import static com.simplegames.chris.rockpaperscissors20.VibrationsKt.vibrate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
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
            vibrate(Settings.this, VibrationType.WEAK);
            getOnBackPressedDispatcher().onBackPressed();
        });
        buttonVibrate.setOnClickListener(v -> {
            ValuesNew.INSTANCE.setVibrationEnabled(!ValuesNew.INSTANCE.getVibrationEnabled());
            ValuesNew.INSTANCE.saveValue(this, SharedPreferenceKeys.INSTANCE.getKEY_SETTING_VIBRATIONS(), ValuesNew.INSTANCE.getVibrationEnabled());
            determineOptionsStates();
            vibrate(getApplicationContext(), VibrationType.WEAK);
        });
        buttonDarkTheme.setOnClickListener(v -> {
            ValuesNew.INSTANCE.setDarkThemeEnabled(!ValuesNew.INSTANCE.getDarkThemeEnabled());
            ValuesNew.INSTANCE.saveValue(this, SharedPreferenceKeys.INSTANCE.getKEY_SETTING_THEME(), ValuesNew.INSTANCE.getDarkThemeEnabled());
            resetLayout();
            vibrate(getApplicationContext(), VibrationType.WEAK);
        });

        buttonAppInfo.setOnClickListener(v -> {
            vibrate(Settings.this, VibrationType.WEAK);
            settingsScrollView.smoothScrollTo(0, 0, ValuesNew.ANIMATION_DURATION / 2);
            UIElements.animate(settingsScrollView, "translationY", height, 0, ValuesNew.ANIMATION_DURATION, new AccelerateInterpolator(3));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent appInfo = new Intent(Settings.this, AppInfo.class);
                startActivity(appInfo.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                Settings.this.overridePendingTransition(0, 0);
            }, ValuesNew.ANIMATION_DURATION);
        });
        determineBackground();
        getWindow().getDecorView().post(this::settingsScrollViewAnimation);
        setBackgroundButtons();
        determineOptionsStates();
        buildBackgroundButtonRecyclerView();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (buttonBack.isClickable()) {
                    buttonBack.setClickable(false);
                    settingsScrollView.smoothScrollTo(0, 0, 500);
                    UIElements.animate(settingsScrollView, "translationY", height, 0, ValuesNew.ANIMATION_DURATION, new AccelerateInterpolator(3));
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent sp = new Intent(Settings.this, SinglePlayer.class);
                        startActivity(sp);
                        finish();
                        Settings.this.overridePendingTransition(0, 0);
                    }, ValuesNew.ANIMATION_DURATION);
                }
            }
        });
    }

    private void determineOptionsStates() {
        if (!ValuesNew.INSTANCE.getVibrationEnabled()) {
            UIElements.setBackground(buttonVibrate, new int[]{
                            ContextCompat.getColor(this, R.color.disabledOption),
                            ContextCompat.getColor(this, R.color.disabledOption)},
                    UIElements.dpToFloat(15f));
            if (ValuesNew.INSTANCE.getDarkThemeEnabled()) {
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
            } else {
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));
            }
        } else {
            UIElements.setBackground(buttonVibrate, new int[]{
                            ContextCompat.getColor(this, R.color.enabledOption),
                            ContextCompat.getColor(this, R.color.enabledOption)},
                    UIElements.dpToFloat(15f));
            vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
        }
        if (!ValuesNew.INSTANCE.getDarkThemeEnabled()) {
            UIElements.setBackground(buttonDarkTheme, new int[]{
                            ContextCompat.getColor(this, R.color.disabledOption),
                            ContextCompat.getColor(this, R.color.disabledOption)},
                    UIElements.dpToFloat(15f));
        } else {
            UIElements.setBackground(buttonDarkTheme, new int[]{
                            ContextCompat.getColor(this, R.color.enabledOption),
                            ContextCompat.getColor(this, R.color.enabledOption)},
                    UIElements.dpToFloat(15f));
        }
    }

    public void setBackgroundButtons() {
        buttonArrayList = new ArrayList<>();
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 0), "Snowfall"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 1), "Faded Red"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 2), "Sunset"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 3), "Hot Lava"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 4), "Cotton Candy"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 5), "Sunshine"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 6), "Traffic Lights"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 7), "Green Grass"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 8), "Coniferous"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 9), "Tropical Ocean"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 10), "Sunny Depths"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 11), "Orbit"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 12), "Juicy Pomegranate"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 13), "Amethyst"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 14), "Darkness"));
        buttonArrayList.add(new SettingsButton(UIElements.getColourArray(this, 15), "Lollipop"));
    }

    public void buildBackgroundButtonRecyclerView() {
        RecyclerView buttonRecyclerView = findViewById(R.id.backgroundRecyclerView);
        buttonRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager buttonLayoutManager = new LinearLayoutManager(this);
        SettingsButtonAdapter buttonAdapter = new SettingsButtonAdapter(buttonArrayList, this);

        buttonRecyclerView.setLayoutManager(buttonLayoutManager);
        buttonRecyclerView.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemClickListener(position -> {
        });
    }

    public void determineBackground() {
        if (Objects.equals(Values.currentActivity, "Settings")) {
            vibrate(getApplicationContext(), VibrationType.WEAK);
        }
        UIElements.setBackground(background, UIElements.getBackgroundColours(this), 0f);
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
            UIElements.animate(settingsScrollView, "translationY", 0, 0, ValuesNew.ANIMATION_DURATION, new DecelerateInterpolator(3));
            Values.currentActivity = "Settings";
        }
    }

    public void resetLayout() {
        NestedScrollView settingsScrollView = findViewById(R.id.settingsScrollView);
        settingsScrollView.smoothScrollTo(0, 0, ValuesNew.ANIMATION_DURATION / 4);
        new Handler().postDelayed(() -> {
            Intent sp = new Intent(Settings.this, Settings.class);
            startActivity(sp);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, ValuesNew.ANIMATION_DURATION / 4);
    }
}
