package com.simplegames.chris.rockpaperscissors20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
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

import java.lang.reflect.Field;
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
    ConstraintLayout buttonVibrate, buttonDarkTheme, buttonDeveloperMode, buttonResetScore, buttonAppInfo, buttonBackupRestore;
    ConstraintLayout background, foreground;
    ImageView backButton, vibrationIcon, darkThemeIcon, developerModeIcon;
    TextView textResetScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Values.darkTheme){
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
        foreground = findViewById(R.id.foreground);
        settingsScrollView = findViewById(R.id.settingsScrollView);
        buttonVibrate = findViewById(R.id.buttonVibrate);
        buttonDarkTheme = findViewById(R.id.buttonDarkTheme);
        buttonDeveloperMode = findViewById(R.id.buttonDeveloperMode);
        buttonResetScore = findViewById(R.id.buttonResetScore);
        buttonAppInfo = findViewById(R.id.buttonAppInfo);
        buttonBackupRestore = findViewById(R.id.buttonBackupRestore);
        backButton = findViewById(R.id.backButton);
        textResetScore = findViewById(R.id.textResetScore);
        vibrationIcon = findViewById(R.id.vibrationIcon);
        darkThemeIcon = findViewById(R.id.darkThemeIcon);
        developerModeIcon = findViewById(R.id.developerModeIcon);

        //Option Buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsScrollView.smoothScrollTo(0, 0);
                UIElements.slideAnimationScrollView(settingsScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.singlePlayer(Settings.this);
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
                    Vibrations.SettingsBGButtonVibration(getApplicationContext());
                }else {
                    Values.vibrationEnabled = true;
                    determineOptionsStates();
                    Vibrations.SettingsBGButtonVibration(getApplicationContext());
                }
            }
        });
        buttonDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Values.darkTheme){
                    Values.darkTheme = false;
                    resetLayout();
                    Vibrations.SettingsBGButtonVibration(getApplicationContext());
                }else {
                    Values.darkTheme = true;
                    resetLayout();
                    Vibrations.SettingsBGButtonVibration(getApplicationContext());
                }
            }
        });

        buttonResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doublePressReset){
                    textResetScore.setText("Reset Score");
                    Vibrations.ResetScoreVibration(getApplicationContext());
                    Values.resetScoreSP = true;
                    doublePressReset = false;
                    return;
                }
                textResetScore.setText("Press again to confirm");
                doublePressReset = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doublePressReset = false;
                        textResetScore.setText("Reset Score");
                    }
                }, 2000);
            }
        });
        buttonAppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsScrollView.smoothScrollTo(0, 0);
                UIElements.slideAnimationScrollView(settingsScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Goto.appInfo(Settings.this);
                        finish();
                        Settings.this.overridePendingTransition(0,0);
                    }
                }, Values.animationSpeed);
            }
        });
        determineBackground();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                settingsScrollViewAnimation();

            }
        });
        setBackgroundButtons();
        determineOptionsStates();
        buildBackgroundButtonRecyclerView();

    }

    private void determineOptionsStates(){
        if (!Values.vibrationEnabled){
            if (Values.darkTheme){
                buttonVibrate.setBackground(null);
                vibrationIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
            }else {
                buttonVibrate.setBackground(null);
                vibrationIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.black)));
            }
        }else {
            UIElements.twoPartGradient(buttonVibrate,null,Color.parseColor(getResources().getString(0+R.color.enabledOption)),
                    Color.parseColor(getResources().getString(0+R.color.enabledOption)), 50);
            vibrationIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
        }
        if (!Values.darkTheme){
            buttonDarkTheme.setBackground(null);
            darkThemeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.black)));
        }else {
            UIElements.twoPartGradient(buttonDarkTheme,null,Color.parseColor(getResources().getString(0+R.color.enabledOption)),
                    Color.parseColor(getResources().getString(0+R.color.enabledOption)), 50);
            darkThemeIcon.setColorFilter(Color.parseColor(getResources().getString(0+R.color.white)));
        }
        if (!Values.devMode){
            if (Values.darkTheme){
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

        UIElements.solidColours(buttonResetScore, ContextCompat.getColor(this, R.color.resetScore),
                new float[] {50,50,50,50,50,50,50,50});
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
            Vibrations.SettingsBGButtonVibration(Settings.this);
        }
        UIElements.determineBackground(background, foreground, Settings.this);
    }

    public void settingsScrollViewAnimation(){
        if (Values.currentActivity == "Settings"){
            settingsScrollView.scrollTo(0,0);
        }else {
            settingsScrollView.scrollTo(0,0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            settingsScrollView.setY(height);
            UIElements.slideAnimationScrollView(settingsScrollView, "translationY", 0, 0, Values.animationSpeed, new DecelerateInterpolator(3));
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
        settingsScrollView.smoothScrollTo(0,0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sp = new Intent(Settings.this, Settings.class);
                startActivity(sp);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }, 100);

    }


    @Override
    public void onBackPressed(){
    }
}
