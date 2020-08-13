package com.ellie.materialdesignex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int INIT_PROGRESS = 65;

    private int screenWidth;
    private GestureDetectorCompat gestureDetector;
    private MusicPlayer musicPlayer;

    private ProgressBar progressBar;
    private TextView textCurrentSeconds;
    private TextView textTotalSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_constraint3);

        findViews();
        initViews();
        setHalfScreenWidth();

        setTouchEvent();
    }

    private void findViews() {
        progressBar = findViewById(R.id.progressBar);
        textCurrentSeconds = findViewById(R.id.currentSeconds);
        textTotalSeconds = findViewById(R.id.totalSeconds);
    }

    private void initViews() {
        musicPlayer = new MusicPlayer(INIT_PROGRESS);
        progressBar.setProgress(musicPlayer.getProgress());
        textCurrentSeconds.setText(convertTimeToText(musicPlayer.getCurrentPlayTime()));
        textTotalSeconds.setText(convertTimeToText(musicPlayer.getTotalPlayTime()));
    }

    private void setHalfScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    private void setTouchEvent() {
        initGestureDetector();
    }

    private void initGestureDetector() {
        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int halfScreenWidth = screenWidth / 2;
                if (e.getX() < halfScreenWidth) {
                    musicPlayer.skipAgo();
                } else {
                    musicPlayer.skipLater();
                }

                progressBar.setProgress(musicPlayer.getProgress());
                textCurrentSeconds.setText(convertTimeToText(musicPlayer.getCurrentPlayTime()));

                return false;
            }
        });
    }

    private String convertTimeToText(double time) {
        int minutes = (int) time / 60;
        int seconds = (int) time % 60;

        String txtSeconds = String.valueOf(seconds);
        if (seconds < 10) {
            txtSeconds = "0" + seconds;
        }

        return "0" + minutes + ":" + txtSeconds;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return false;
    }
}