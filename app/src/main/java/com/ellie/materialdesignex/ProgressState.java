package com.ellie.materialdesignex;

public class ProgressState {
    private final static int SKIP_UNIT = 10;
    private final static int TOTAL_PLAY_TIME = 5 * 60;
    private final static int DEFAULT_PROGRESS = 0;

    private double currentPlayTime;
    private int progress = DEFAULT_PROGRESS;

    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.currentPlayTime = convertProgressToTime(progress);
        this.progress = progress;
    }

    public int getTotalPlayTime() {
        return TOTAL_PLAY_TIME;
    }

    public void skipLater() {
        changeCurrentPlayTime(SKIP_UNIT);
    }

    public void skipAgo() {
        changeCurrentPlayTime((-1) * SKIP_UNIT);
    }

    public void changeProgress(int progress) {
        this.progress = progress;
        this.currentPlayTime = convertProgressToTime(progress);
    }

    private void changeCurrentPlayTime(int seconds) {
        currentPlayTime += seconds;
        progress = convertTimeToProgress(currentPlayTime);

        if (progress <= 0) {
            progress = 0;
            currentPlayTime = 0;
        } else if (progress >= 100) {
            progress = 100;
            currentPlayTime = TOTAL_PLAY_TIME;
        }
    }

    private double convertProgressToTime(int progress) {
        return TOTAL_PLAY_TIME * progress * 0.01;
    }

    private int convertTimeToProgress(double time) {
        return (int) Math.round((time / TOTAL_PLAY_TIME) * 100);
    }
}
