package com.ellie.materialdesignex;

public class ProgressTimeConverter {
    private int totalPlayTime;
    private double currentPlayTime = 0;

    public ProgressTimeConverter(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    public int getProgress() {
        return convertTimeToProgress(currentPlayTime);
    }

    public void setProgress(int progress) {
        this.currentPlayTime = convertProgressToTime(progress);
    }

    public void skipLater(int skipUnit) {
        changeCurrentPlayTime(skipUnit);
    }

    public void skipAgo(int skipUnit) {
        changeCurrentPlayTime((-1) * skipUnit);
    }

    private void changeCurrentPlayTime(int seconds) {
        currentPlayTime += seconds;

        if (currentPlayTime <= 0) {
            currentPlayTime = 0;
        } else if (currentPlayTime >= totalPlayTime) {
            currentPlayTime = totalPlayTime;
        }
    }

    private double convertProgressToTime(int progress) {
        return totalPlayTime * progress * 0.01;
    }

    private int convertTimeToProgress(double time) {
        return (int) Math.round((time / totalPlayTime) * 100);
    }
}
