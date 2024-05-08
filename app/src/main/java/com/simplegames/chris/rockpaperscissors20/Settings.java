package com.simplegames.chris.rockpaperscissors20;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    private ArrayList<SettingsButton> buttonArrayList;
    private RecyclerView buttonRecyclerView;
    private SettingsButtonAdapter buttonAdapter;
    private RecyclerView.LayoutManager buttonLayoutManager;

    boolean doublePressReset;
    int height;

    SwipeRefreshLayout swipeToBack;
    NestedScrollView settingsScrollView;
    MaterialCardView buttonVibrate, buttonDarkTheme, buttonBack;
    ConstraintLayout buttonDeveloperMode, buttonResetScore, buttonAppInfo, buttonBackupRestore;
    ImageView vibrationIcon, darkThemeIcon, developerModeIcon, background;
    TextView textResetScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Values.darkThemeEnabled){
            setTheme(R.style.DarkTheme);
        }else {
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
        buttonDeveloperMode = findViewById(R.id.buttonDeveloperMode);
        buttonResetScore = findViewById(R.id.buttonResetScore);
        buttonAppInfo = findViewById(R.id.buttonAppInfo);
        buttonBackupRestore = findViewById(R.id.buttonBackupRestore);
        buttonBack = findViewById(R.id.buttonBack);
        textResetScore = findViewById(R.id.textResetScore);
        vibrationIcon = findViewById(R.id.vibrationIcon);
        //darkThemeIcon = findViewById(R.id.darkThemeIcon);
        developerModeIcon = findViewById(R.id.developerModeIcon);

        //Option Buttons
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBack.setClickable(false);
                Vibrations.vibrate(Settings.this, "low");
                settingsScrollView.smoothScrollTo(0, 0, 500);
                UIElements.animate(settingsScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent sp = new Intent(Settings.this, SinglePlayer.class);
                        startActivity(sp);
                        finish();
                        Settings.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed);
            }
        });
        buttonVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Values.vibrationEnabled){
                    Values.vibrationEnabled = false;
                    determineOptionsStates();
                    Vibrations.vibrate(getApplicationContext(), "low");
                }else {
                    Values.vibrationEnabled = true;
                    determineOptionsStates();
                    Vibrations.vibrate(getApplicationContext(), "low");
                }
            }
        });
        buttonDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Values.darkThemeEnabled){
                    Values.darkThemeEnabled = false;
                    resetLayout();
                    Vibrations.vibrate(getApplicationContext(), "low");
                }else {
                    Values.darkThemeEnabled = true;
                    resetLayout();
                    Vibrations.vibrate(getApplicationContext(), "low");
                }
            }
        });

        buttonResetScore.setOnClickListener(v -> {
            Handler handler = new Handler();
            Runnable runnable = () -> {
                doublePressReset = false;
                textResetScore.setText("Reset Score");
            };

            if (doublePressReset){
                textResetScore.setText("Reset Score");
                Vibrations.vibrate(getApplicationContext(), "reset");
                Values.resetScoreSP = true;
                doublePressReset = false;
                handler.removeCallbacks(runnable);
                return;
            }
            textResetScore.setText("Press again to confirm");
            doublePressReset = true;
            handler.postDelayed(runnable, 2000);
        });
        buttonAppInfo.setOnClickListener(v -> {
            Vibrations.vibrate(Settings.this, "low");
            settingsScrollView.smoothScrollTo(0, 0, 500);
            UIElements.animate(settingsScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent appinfo = new Intent(Settings.this, AppInfo.class);
                startActivity(appinfo.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
                Settings.this.overridePendingTransition(0,0);
            }, Values.animationSpeed);
        });
        determineBackground();
        getWindow().getDecorView().post(this::settingsScrollViewAnimation);
        setBackgroundButtons();
        determineOptionsStates();
        buildBackgroundButtonRecyclerView();

    }

    private void determineOptionsStates(){
        if (!Values.vibrationEnabled){
            UIElements.setBackground(this, buttonVibrate, new int[] {
                            ContextCompat.getColor(this, R.color.transparent),
                            ContextCompat.getColor(this, R.color.transparent)},
                    UIElements.dpToFloat(15f), 0);
            if (Values.darkThemeEnabled){
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.white));
            }else {
                vibrationIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));
            }
        }else {
            UIElements.setBackground(this, buttonVibrate, new int[] {
                    ContextCompat.getColor(this, R.color.enabledOption),
                    ContextCompat.getColor(this, R.color.enabledOption)},
                    UIElements.dpToFloat(15f), 0);
            vibrationIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
        }
        if (!Values.darkThemeEnabled){
            buttonDarkTheme.setBackground(null);
            //darkThemeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.black)));
        }else {
            UIElements.twoPartGradient(buttonDarkTheme,null,Color.parseColor(getResources().getString(0+R.color.enabledOption)),
                    Color.parseColor(getResources().getString(0+R.color.enabledOption)), 50);
            //darkThemeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
        }
        if (!Values.devMode){
            if (Values.darkThemeEnabled){
                buttonDeveloperMode.setBackground(null);
                developerModeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
            }else {
                buttonDeveloperMode.setBackground(null);
                developerModeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.black)));
            }
        }else {
            UIElements.twoPartGradient(buttonDeveloperMode,null,Color.parseColor(getResources().getString(0+R.color.enabledOption)),
                    Color.parseColor(getResources().getString(0+R.color.enabledOption)), 50);
            developerModeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
        }

        //UIElements.setBackground(this, buttonResetScore, new int[] {ContextCompat.getColor(this, R.color.resetScore)}, 50f, 0);
        UIElements.twoPartGradient(buttonAppInfo, null, ContextCompat.getColor(this, R.color.backupRestoreTopLeft),
                ContextCompat.getColor(this, R.color.backupRestoreBottomRight), 50);

    }

    public void setBackgroundButtons(){
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
    public void buildBackgroundButtonRecyclerView(){
        buttonRecyclerView = findViewById(R.id.backgroundRecyclerView);
        buttonRecyclerView.setHasFixedSize(true);
        buttonLayoutManager = new LinearLayoutManager(this);
        buttonAdapter = new SettingsButtonAdapter(buttonArrayList, this);

        buttonRecyclerView.setLayoutManager(buttonLayoutManager);
        buttonRecyclerView.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemClickListener(new SettingsButtonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) { }
        });
    }
    public void determineBackground(){
        if (Values.currentActivity == "Settings") {
            Vibrations.vibrate(getApplicationContext(), "low");
        }
        UIElements.setBackground(this ,background, UIElements.getBackgroundColours(this), 0f, 5000);
    }

    public void settingsScrollViewAnimation(){
        if (Values.currentActivity == "Settings"){
            settingsScrollView.scrollTo(0,0);
        } else {
            settingsScrollView.scrollTo(0,0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            settingsScrollView.setY(height);
            UIElements.animate(settingsScrollView, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
            Values.currentActivity = "Settings";
        }
    }

    public Bitmap setShadow(int resourceId){
        Bitmap bmp = null;
        try {
            int margin = 30;
            int halfMargin = margin/2;

            int shadowRadius = 15;

            int shadowColor = Color.rgb(0, 0, 0);

            Bitmap src = BitmapFactory.decodeResource(getResources(), resourceId);

            //Bitmap alpha = src.extractAlpha();

            bmp = Bitmap.createBitmap(src.getWidth() + margin, src.getHeight()
            + margin, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bmp);

            Paint paint = new Paint();
            paint.setColor(shadowColor);

            paint.setMaskFilter(new BlurMaskFilter(shadowRadius, BlurMaskFilter.Blur.OUTER));
            //canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

            canvas.drawBitmap(src, halfMargin, halfMargin, null);
            //Toast.makeText(this, "Shadow"+alpha, Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed: "+e, Toast.LENGTH_SHORT).show();
        }
        return bmp;
    }

    public void resetLayout(){
        NestedScrollView settingsScrollView = findViewById(R.id.settingsScrollView);
        settingsScrollView.smoothScrollTo(0,0, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sp = new Intent(Settings.this, Settings.class);
                startActivity(sp);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }, Values.animationSpeed);

    }


    @Override
    public void onBackPressed(){
    }
}
