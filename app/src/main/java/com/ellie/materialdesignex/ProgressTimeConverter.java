package com.ellie.materialdesignex;

/**
 * Progress (진행률)에 따라 Time (재생시간)을 변환한다.
 * Progress를 외부에서 받지만, Time을 기준으로 동작한다.
 */
public class ProgressTimeConverter {

    //----------------------------------------------------------
    // Instance data.
    //

    // 음악의 총 재생 시간. (초)
    private int totalPlayTime;

    // 현재 재생 시간. (초)
    private double currentPlayTime = 0;

    //----------------------------------------------------------
    // Public interface
    //

    /**
     * Constructor
     * @param totalPlayTime 총 재생시간을 설정하기 위한 값. (초)
     */
    public ProgressTimeConverter(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    /**
     * 현재 재생 시간을 반환한다.
     */
    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    /**
     * 현재 진행률을 반환한다.
     */
    public int getProgress() {
        // 재생 시간을 진행률로 변환해서 반환.
        return convertTimeToProgress(currentPlayTime);
    }

    /**
     * 진행률을 설정한다.
     */
    public void setProgress(int progress) {
        // 진행률을 받아서 현재 재생 시간을 설정.
        this.currentPlayTime = convertProgressToTime(progress);
    }

    /**
     * 현재 재생 시간을 이후 몇 초로 점프한다.
     */
    public void skipLater(int skipUnit) {
        skipCurrentPlayTime(skipUnit);
    }

    /**
     * 현재 재생 시간을 이전 몇 초로 되돌아간다.
     */
    public void skipAgo(int skipUnit) {
        skipCurrentPlayTime((-1) * skipUnit);
    }

    //----------------------------------------------------------
    // Internal support interface.
    //

    /**
     * 현재 재생 시간을 이전 몇 초로 되돌아간다.
     * @param seconds skip할 초 단위.
     */
    private void skipCurrentPlayTime(int seconds) {
        currentPlayTime += seconds;

        // 0초보다 이전일 때는 0초로 설정.
        // 총 재생 시간보다 이후일 때는 총 재생시간으로 설정.
        if (currentPlayTime <= 0) {
            currentPlayTime = 0;
        } else if (currentPlayTime >= totalPlayTime) {
            currentPlayTime = totalPlayTime;
        }
    }

    /**
     * 진행률을 재생 시간으로 변환한다.
     */
    private double convertProgressToTime(int progress) {
        // 재생 시간 = ((총 재생 시간) * (진행률)) / 100;
        return totalPlayTime * progress * 0.01;
    }

    /**
     * 재생 시간을 진행률로 변환한다.
     */
    private int convertTimeToProgress(double time) {
        // 진행률 = ((현재 재생 시간) / (총 재생 시간)) * 100
        // 계산한 값을 반올림해서 반환한다.
        return (int) Math.round((time / totalPlayTime) * 100);
    }
}
