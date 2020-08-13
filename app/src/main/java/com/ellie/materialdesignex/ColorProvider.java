package com.ellie.materialdesignex;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 외부에게 Color Source를 제공한다.
 */
public class ColorProvider {

    //----------------------------------------------------------
    // Instance data.
    //

    // Color 리스트.
    private List<String> colorList = Arrays.asList(
            "#E74C3C", "#E67E22", "#F5B041", "#F4D03F", "#2ECC71",
            "#27AE60", "#3498DB", "#2980B9", "#9B59B6", "#8E44AD",
            "#FFFFFF", "#979A9A", "#5D6D7E", "#34495E", "#283747"
    );

    // Color 리스트에 대한 ArrayList.
    private ArrayList<String> colorArray = new ArrayList<>(colorList);

    //----------------------------------------------------------
    // Public interface
    //

    /**
     * Color ArrayList를 반환한다.
     */
    public ArrayList<String> getColorArray() {
        return colorArray;
    }

    /**
     * Color 리스트에서 랜덤한 색상을 반환한다.
     */
    public int getRandomColor() {
        // Math.random()은 0 ~ 1 사이에 소수 생성.
        // x(리스트 크기)를 곱하면 0 ~ x 사이에 소수로 변환.
        // 계산한 값을 정수로 변환하면 내림 연산이 적용되므로 0 ~ x-1 사이에 정수가 된다.
        int i = (int) (Math.random() * colorList.size());

        return convertColor(colorList.get(i));
    }

    //----------------------------------------------------------
    // Internal support interface.
    //

    /**
     * 문자열 색상 코드를 받아서 int 로 변환한다.
     * @param colorString hexCode에 대한 문자열.
     * @return int로 변환한 colorCode 값.
     */
    private int convertColor(String colorString) {
        return Color.parseColor(colorString);
    }
}
