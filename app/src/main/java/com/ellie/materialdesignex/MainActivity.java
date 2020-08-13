package com.ellie.materialdesignex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private GestureDetectorCompat imageGestureDetector;
    private GestureDetectorCompat textGestureDetector;
    private GestureDetectorCompat progressGestureDetector;

    private ProgressState progressState;
    private ColorProvider colorProvider = new ColorProvider();

    private ImageView albumImage;

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
        albumImage = findViewById(R.id.albumImage);

        musicTitle = findViewById(R.id.musicTitle);
        albumTitle = findViewById(R.id.albumTitle);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnShuffle = findViewById(R.id.btnShuffle);

        progressBar = findViewById(R.id.progressBar);
        textCurrentSeconds = findViewById(R.id.currentSeconds);
        textTotalSeconds = findViewById(R.id.totalSeconds);
    }

    private void initViews() {
        progressState = new ProgressState(INIT_PROGRESS);
        progressBar.setProgress(progressState.getProgress());
        textCurrentSeconds.setText(convertTimeToText(progressState.getCurrentPlayTime()));
        textTotalSeconds.setText(convertTimeToText(progressState.getTotalPlayTime()));
    }

    private void setHalfScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    private void setTouchEvent() {
        initGestureDetectors();
        setImageEvent();
        setTextEvent();
    }

    private void setImageEvent() {
        albumImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !imageGestureDetector.onTouchEvent(event);
            }
        });
    }

    private void setTextEvent() {
        View.OnTouchListener textTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !textGestureDetector.onTouchEvent(event);
            }
        };

        musicTitle.setOnTouchListener(textTouchListener);
        albumTitle.setOnTouchListener(textTouchListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !progressGestureDetector.onTouchEvent(event);
    }

    private void initGestureDetectors() {
        initImageGestureDetector();
        initTextGestureDetector();
        initProgressGestureDetector();
    }

    private void initImageGestureDetector() {
        imageGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                changeAlbumImage();
                return false;
            }
        });
    }

    private void changeAlbumImage() {
        
    }

    private void initTextGestureDetector() {
        textGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                changeTextColors(colorProvider.getRandomColor());
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                showColorDialog();
            }
        });
    }

    private void showColorDialog() {
        final ColorPicker colorPicker = new ColorPicker(this);

        colorPicker.setColors(colorProvider.getColorArray())
                .setColumns(5)
                .setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
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

    private void initProgressGestureDetector() {
        progressGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int halfScreenWidth = screenWidth / 2;
                if (e.getX() < halfScreenWidth) {
                    progressState.skipAgo();
                } else {
                    progressState.skipLater();
                }

                applyChangedProgress();

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float curX = e2.getX();
                int progress = (int) ((curX / screenWidth) * 100);

                progressState.changeProgress(progress);
                applyChangedProgress();

                return false;
            }

            private void applyChangedProgress() {
                progressBar.setProgress(progressState.getProgress());
                textCurrentSeconds.setText(convertTimeToText(progressState.getCurrentPlayTime()));
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
}