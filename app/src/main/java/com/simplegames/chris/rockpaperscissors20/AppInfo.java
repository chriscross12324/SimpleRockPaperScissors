package com.simplegames.chris.rockpaperscissors20;

import static com.simplegames.chris.rockpaperscissors20.VibrationsKt.vibrate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ValuesNew.INSTANCE.getDarkThemeEnabled()) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_app_info);

        determineBackground();
        appInfoScrollViewAnimation();

        TextView appVersion = findViewById(R.id.versionBody);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        MaterialCardView backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            try {
                backButton.setClickable(false);
                vibrate(AppInfo.this, VibrationType.WEAK);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
                appInfoScrollView.smoothScrollTo(0, 0, 500);
                UIElements.animate(appInfoScrollView, "translationY", height, 0, ValuesNew.INSTANCE.getAnimationDuration(), new AccelerateInterpolator(3));
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent spn = new Intent(AppInfo.this, Settings.class);
                    startActivity(spn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    finish();
                    AppInfo.this.overridePendingTransition(0, 0);
                }, ValuesNew.INSTANCE.getAnimationDuration());
            } catch (Exception e) {
                finish();
            }

        });
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        TextView dateReleasedBody = findViewById(R.id.dateReleasedBody);
        //dateReleasedBody.setText(df.format(c));


    }

    private void determineBackground() {
        ImageView background = findViewById(R.id.background);
        //UIElements.determineBackground(background, null, AppInfo.this);
        UIElements.setBackground(getApplicationContext(), background, UIElements.getBackgroundColours(getApplicationContext()), 0f, 0);
    }

    public void appInfoScrollViewAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels + 100;
        NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
        appInfoScrollView.setY(height);
        UIElements.animate(appInfoScrollView, "translationY", 0, 100, ValuesNew.INSTANCE.getAnimationDuration(), new DecelerateInterpolator(3));
        appInfoScrollView.setVisibility(View.VISIBLE);
        Values.currentActivity = "AppInfo";
        //ValuesNew.INSTANCE
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
    }
}
