package com.simplegames.chris.rockpaperscissors20;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SplashScreen";
    boolean clicked = false;
    String versions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMenu();
        Values.loadAllValues(getApplicationContext());
        /*setContentView(R.layout.activity_splash_screen);
        getSpecialImageURL();
        ImageView appLogo = findViewById(R.id.appLogo);
        determineBackground();
        appLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto.mainMenuNew(SplashScreen.this);
                clicked = true;
                finish();
            }
        });*/

        /*if (Build.VERSION.SDK_INT >= 26){
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }*/

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Values.firstOpen){
                    if (clicked){

                    }else {
                        loadMenu();
                    }
                }else {
                    if (clicked){

                    }else {
                        loadMenu();
                    }
                }
            }
        }, 500);


        TextView appVersion = findViewById(R.id.versionBody);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText("Version: "+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void setLogoSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        ImageView appLogo = findViewById(R.id.appLogo);
        if (width/1.3 <= 200){
            appLogo.getLayoutParams().width = width;
            appLogo.getLayoutParams().height = width;
        }else {
            appLogo.getLayoutParams().width = 200;
            appLogo.getLayoutParams().height = 200;
        }
    }

    public void getSpecialImageURL(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Thread Started");
                try {
                    URL url = new URL("https://pastebin.com/raw/bm84dbrr"); //Text File Location
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Open Connection with File
                    connection.setConnectTimeout(30000); //Timeout after 30 seconds

                    BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                    Values.specialImageURL = reader.readLine();
                    /*if (!Values.specialImageURL.equals(reader.readLine())){
                        Values.specialImageURL = reader.readLine();
                        Values.imageAlreadyDownloaded = false;
                    }*/
                    reader.close();
                    Log.d(TAG, "run: Running");

                }catch (MalformedURLException m){
                    Log.d(TAG, "run: Error 1");
                }catch (IOException i){
                    Log.d(TAG, "run: Error 2");
                }
            }
        }).start();
        new SplashScreen().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(SplashScreen.this, ""+Values.specialImageURL, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void determineBackground(){
        ConstraintLayout background = findViewById(R.id.background);
        ConstraintLayout foreground = findViewById(R.id.foreground);
        //UIElements.twoPartGradient(background, foreground, );
    }
    public void loadMenu(){
        Goto.singlePlayer(SplashScreen.this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    public void loadPermissions(){

    }


}
