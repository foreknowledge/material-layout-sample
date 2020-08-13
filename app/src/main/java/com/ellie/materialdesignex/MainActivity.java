package com.ellie.materialdesignex;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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

    //----------------------------------------------------------
    // Constant definitions.
    //

    // 초기 Progress Bar 진행률. (%)
    private final static int INIT_PROGRESS = 65;

    // 앞/뒤로 Skip 할 수 있는 단위. (10초)
    private final static int SKIP_UNIT = 10;

    // 음악의 총 재생 시간. (5분 = 300초)
    private final static int TOTAL_PLAY_TIME = 5 * 60;

    //----------------------------------------------------------
    //  UI references.
    //
    private ImageView albumImage;

    private TextView musicTitle;
    private TextView albumTitle;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;

    private ProgressBar progressBar;
    private TextView textCurrentSeconds;
    private TextView textTotalSeconds;

    //----------------------------------------------------------
    // Instance data.
    //

    // Gesture Detectors.
    private GestureDetectorCompat imageGestureDetector;
    private GestureDetectorCompat textGestureDetector;
    private GestureDetectorCompat progressGestureDetector;

    // 디바이스 화면 가로 크기. (pixel)
    private int screenWidth;

    // 이미지 뷰에 보여줄 이미지 제공.
    private ImageProvider imageProvider;

    // 텍스트나 버튼 색상 제공.
    private ColorProvider colorProvider = new ColorProvider();

    // 진행률과 재생시간을 변환.
    private ProgressTimeConverter progressTimeConverter = new ProgressTimeConverter(TOTAL_PLAY_TIME);

    //----------------------------------------------------------
    // Public interface
    //

    /**
     * onCreate() callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_constraint3);

        // 화면 보여주기 위한 초기 세팅
        findViews();
        initViews();
        setScreenWidth();

        // 터치 이벤트 세팅
        setTouchEvent();
    }

    /**
     * MainActivity 터치 이벤트를 처리한다.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Progress Gesture Detector 에게 터치 이벤트 위임.
        progressGestureDetector.onTouchEvent(event);

        return true;
    }

    //----------------------------------------------------------
    // Internal support interface.
    //

    /**
     * xml에 정의한 UI reference를 찾는다.
     */
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

    /**
     * 초기 화면을 설정한다.
     */
    private void initViews() {
        // 첫 앨범 이미지 설정.
        imageProvider = new ImageProvider(this);
        albumImage.setImageDrawable(imageProvider.getFirstImage());

        // Progress Bar 상태 설정.
        progressTimeConverter.setProgress(INIT_PROGRESS);
        progressBar.setProgress(progressTimeConverter.getProgress());

        // 현재 재생 시간 설정.
        // 재생 시간(int)을 텍스트(String)로 변환해서 설정한다.
        textCurrentSeconds.setText(convertTimeToText(progressTimeConverter.getCurrentPlayTime()));
        textTotalSeconds.setText(convertTimeToText(TOTAL_PLAY_TIME));
    }

    /**
     * 디바이스의 화면 가로 크기를 설정한다.
     */
    private void setScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    /**
     * 모든 터치 이벤트를 처리한다.
     */
    private void setTouchEvent() {
        // Gesture Detector의 이벤트 처리 동작을 설정한다.
        initGestureDetectors();

        // 각 터치 이벤트 설정.
        setImageEvent();
        setTextEvent();
    }

    /**
     * ImageView 터치 이벤트를 처리한다.
     */
    private void setImageEvent() {
        albumImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Image Gesture Detector 에게 터치 이벤트 위임.
                imageGestureDetector.onTouchEvent(event);

                return true;
            }
        });
    }

    /**
     * TextView 터치 이벤트를 처리한다.
     */
    private void setTextEvent() {
        View.OnTouchListener textTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Text Gesture Detector 에게 터치 이벤트 위임.
                textGestureDetector.onTouchEvent(event);

                return true;
            }
        };

        // 노래 제목, 앨범 제목을 터치했을 때 같은 동작을 한다.
        musicTitle.setOnTouchListener(textTouchListener);
        albumTitle.setOnTouchListener(textTouchListener);
    }

    /**
     * Gesture Detector의 처리 동작을 초기화한다.
     */
    private void initGestureDetectors() {
        // 각 Gesture Detector 초기화.
        initImageGestureDetector();
        initTextGestureDetector();
        initProgressGestureDetector();
    }

    /**
     * ImageView 터치를 처리하는 Gesture Detector 동작을 초기화한다.
     */
    private void initImageGestureDetector() {
        imageGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // 튕기기 제스처를 하면 이미지를 바꾼다.
                //  x 속도가 양수면 (오른쪽 방향) - 다음 이미지로 변경.
                //  x 속도가 음수면 (왼쪽 방향) - 이전 이미지로 변경.
                changeAlbumImage(velocityX > 0);

                return false;
            }
        });
    }

    /**
     * 앨범 이미지를 바꾼다.
     * @param isNext true 이면 다음 이미지, false 이면 이전 이미지로 바꾼다.
     */
    private void changeAlbumImage(boolean isNext) {
        Drawable newAlbumImage;
        if (isNext) {
            newAlbumImage = imageProvider.getNextImage();
        } else {
            newAlbumImage = imageProvider.getPreviousImage();
        }

        // 다음 앨범 이미지를 가져와서 설정.
        albumImage.setImageDrawable(newAlbumImage);
    }

    /**
     * TextView 터치를 처리하는 Gesture Detector 동작을 초기화한다.
     */
    private void initTextGestureDetector() {
        textGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                // 텍스트 터치하면 랜덤한 색상으로 변경.
                changeTextColors(colorProvider.getRandomColor());

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // 텍스트를 길게 터치하면 색상을 선택할 수 있는 대화상자를 띄운다.
                showColorPickerDialog();
            }
        });
    }

    /**
     * 색상 선택할 수 있는 대화상자를 만들어 띄운다.
     */
    private void showColorPickerDialog() {
        final ColorPicker colorPicker = new ColorPicker(this);

        // 선택할 수 있는 색상 리스트를 설정해서
        //   한 줄에 5개짜리로 Color Picker를 보여준다.
        colorPicker.setColors(colorProvider.getColorArray())
                .setColumns(5)
                .setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        // 색상 선택했으면 그 색상으로 텍스트 색상 변경.
                        changeTextColors(color);
                    }

                    @Override
                    public void onCancel() {
                        // do nothing.
                    }
                }).show();
    }

    /**
     * Text의 색상을 지정한 color로 바꾼다.
     */
    private void changeTextColors(int color) {
        musicTitle.setTextColor(color);
        albumTitle.setTextColor(color);
        btnRepeat.setColorFilter(color);
        btnShuffle.setColorFilter(color);
    }

    /**
     * ProgressBar 터치를 처리하는 Gesture Detector 동작을 초기화한다.
     */
    private void initProgressGestureDetector() {
        progressGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // 화면을 반으로 나눠서 좌/우 터치로 구분.
                int halfScreenWidth = screenWidth / 2;
                if (e.getX() < halfScreenWidth) {
                    // 화면 좌측 터치 - 이전 몇 초로 되돌아간다.
                    progressTimeConverter.skipAgo(SKIP_UNIT);
                } else {
                    // 화면 우측 터치 - 이후 몇 초로 점프한다.
                    progressTimeConverter.skipLater(SKIP_UNIT);
                }

                // 변경사항을 UI에 적용.
                applyChangedProgress();

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float curX = e2.getX();

                // 진행률 = (이동한 위치의 x좌표) / (화면 사이즈) * 100
                int progress = (int) ((curX / screenWidth) * 100);

                // 현재 상태 계산한 진행률로 변경한 뒤 UI에 적용.
                progressTimeConverter.setProgress(progress);
                applyChangedProgress();

                return false;
            }

            /**
             * 변경된 Progress 상태를 UI에 적용한다.
             */
            private void applyChangedProgress() {
                // 변경된 progress와 time을 가져와 UI에 적용.
                progressBar.setProgress(progressTimeConverter.getProgress());
                textCurrentSeconds.setText(convertTimeToText(progressTimeConverter.getCurrentPlayTime()));
            }
        });
    }

    /**
     * 재생 시간(초)을 텍스트로 바꾼다.
     * Format - MM:SS
     */
    private String convertTimeToText(double time) {
        int minutes = (int) time / 60;
        int seconds = (int) time % 60;

        // 숫자를 텍스트로 바꿔서 10초보다 작으면 앞에 0을 붙인다
        //   ex) 5 -> "05"
        String txtSeconds = String.valueOf(seconds);
        if (seconds < 10) {
            txtSeconds = "0" + seconds;
        }

        return "0" + minutes + ":" + txtSeconds;
    }
}