package com.simplegames.chris.rockpaperscissors20;

import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.simplegames.chris.rockpaperscissors20.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Values.darkTheme){
            setTheme(R.style.DarkTheme);
        }else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_app_info_new);

        try {
            determineBackground();
        }catch (Exception e){}
        try {
            appInfoScrollViewAnimation();
        }catch (Exception e){}

        TextView appVersion = findViewById(R.id.versionBody);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText(""+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    backButton.setClickable(false);
                    Vibrations.openMenu(AppInfo.this);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
                    appInfoScrollView.smoothScrollTo(0, 0);
                    UIElements.animate(appInfoScrollView, "translationY", height, 0, Values.animationSpeed, new AccelerateInterpolator(3));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Goto.settingsNew(AppInfo.this);
                            finish();
                            AppInfo.this.overridePendingTransition(0,0);
                        }
                    }, Values.animationSpeed);
                }catch (Exception e){
                    finish();
                }

            }
        });
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        TextView dateReleasedBody = findViewById(R.id.dateReleasedBody);
        //dateReleasedBody.setText(df.format(c));



    }

    private void determineBackground(){
        ConstraintLayout background = findViewById(R.id.background);
        UIElements.determineBackground(background, null, AppInfo.this);
    }

    public void appInfoScrollViewAnimation(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels+100;
        NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
        appInfoScrollView.setY(height);
        UIElements.animate(appInfoScrollView, "translationY", 0, 100, Values.animationSpeed, new DecelerateInterpolator(3));
        Values.currentActivity = "AppInfo";
    }

    @Override
    public void onBackPressed(){
    }
}
