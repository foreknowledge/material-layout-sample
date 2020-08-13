package com.ellie.materialdesignex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import petrov.kristiyan.colorpicker.ColorPicker;

@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int INIT_PROGRESS = 65;

    private int screenWidth;
    private GestureDetectorCompat progressGestureDetector;
    private GestureDetectorCompat textGestureDetector;

    private MusicPlayer musicPlayer;
    private ColorChanger colorChanger = new ColorChanger();

    private TextView musicTitle;
    private TextView albumTitle;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;

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
        musicTitle = findViewById(R.id.musicTitle);
        albumTitle = findViewById(R.id.albumTitle);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnShuffle = findViewById(R.id.btnShuffle);

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
        setTextEvent();
        initGestureDetectors();
    }

    private void setTextEvent() {
        View.OnTouchListener textTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textGestureDetector.onTouchEvent(event);
                return true;
            }
        };

        musicTitle.setOnTouchListener(textTouchListener);
        albumTitle.setOnTouchListener(textTouchListener);
    }

    private void initGestureDetectors() {
        progressGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
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

        textGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                changeTextColors(colorChanger.getRandomColor());
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                showColorDialog();
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

    private void showColorDialog() {
        final ColorPicker colorPicker = new ColorPicker(this);

        colorPicker.setColors(colorChanger.getColorArray())
                .setColumns(5)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        changeTextColors(color);
                    }

                    @Override
                    public void onCancel() { }
                }).show();
    }

    private void changeTextColors(int color) {
        musicTitle.setTextColor(color);
        albumTitle.setTextColor(color);
        btnRepeat.setColorFilter(color);
        btnShuffle.setColorFilter(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        progressGestureDetector.onTouchEvent(event);
        return false;
    }
}