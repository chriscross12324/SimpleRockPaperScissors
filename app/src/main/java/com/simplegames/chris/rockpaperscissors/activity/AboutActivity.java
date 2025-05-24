package com.simplegames.chris.rockpaperscissors.activity;

import static com.simplegames.chris.rockpaperscissors.utils.VibrationsKt.vibrate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.card.MaterialCardView;
import com.simplegames.chris.rockpaperscissors.R;
import com.simplegames.chris.rockpaperscissors.utils.UIElements;
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities;
import com.simplegames.chris.rockpaperscissors.utils.Values;
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew;
import com.simplegames.chris.rockpaperscissors.utils.VibrationType;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ValuesNew.INSTANCE.getDarkThemeEnabled()) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_app_info);

        NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
        TextView appVersion = findViewById(R.id.versionBody);
        MaterialCardView backButton = findViewById(R.id.buttonBack);

        determineBackground();
        appInfoScrollViewAnimation();


        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.getLocalizedMessage();
        }

        backButton.setOnClickListener(v -> {
            vibrate(AboutActivity.this, VibrationType.WEAK);
            getOnBackPressedDispatcher().onBackPressed();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backButton.isClickable()) {
                    backButton.setClickable(false);
                    appInfoScrollView.smoothScrollTo(0, 0, 500);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    UIUtilities.INSTANCE.animate(appInfoScrollView, UIUtilities.ViewProperty.TRANSLATION_Y, 0, ValuesNew.ANIMATION_DURATION, new AccelerateInterpolator(3f), height);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent spn = new Intent(AboutActivity.this, SettingsActivity.class);
                        startActivity(spn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                        finish();
                        AboutActivity.this.overridePendingTransition(0, 0);
                    }, ValuesNew.ANIMATION_DURATION);
                }
            }
        });
    }

    private void determineBackground() {
        ImageView background = findViewById(R.id.background);
        //UIElements.determineBackground(background, null, AppInfo.this);
        UIElements.setBackground(background, UIElements.getBackgroundColours(getApplicationContext()), 0f);
    }

    public void appInfoScrollViewAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels + 100;
        NestedScrollView appInfoScrollView = findViewById(R.id.appInfoScrollView);
        appInfoScrollView.setY(height);
        UIUtilities.INSTANCE.animate(appInfoScrollView, UIUtilities.ViewProperty.TRANSLATION_Y, 100, ValuesNew.ANIMATION_DURATION, new DecelerateInterpolator(3f), 0);
        appInfoScrollView.setVisibility(View.VISIBLE);
        Values.currentActivity = "AppInfo";
        //ValuesNew.INSTANCE
    }
}
