package com.ellie.materialdesignex;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorProvider {
    private List<String> colorList = Arrays.asList(
            "#E74C3C", "#E67E22", "#F5B041", "#F4D03F", "#2ECC71",
            "#27AE60", "#3498DB", "#2980B9", "#9B59B6", "#8E44AD",
            "#F7F9F9", "#979A9A", "#5D6D7E", "#34495E", "#283747"
    );

    private ArrayList<String> colorArray = new ArrayList<>(colorList);

    public ArrayList<String> getColorArray() {
        return colorArray;
    }

    public int getRandomColor() {
        int i = (int) (Math.random() * colorList.size());

        return convertColor(colorList.get(i));
    }

    private int convertColor(String colorString) {
        return Color.parseColor(colorString);
    }
}
