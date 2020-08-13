package com.ellie.materialdesignex;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 외부에게 Image Source를 제공한다.
 */
public class ImageProvider {

    //----------------------------------------------------------
    // Instance data.
    //

    // Image list.
    private List<Drawable> imageList = new ArrayList<>();

    // Current image index.
    private int curIndex = 0;

    //----------------------------------------------------------
    // Public interface
    //

    /**
     * Constructor
     * @param context Drawable 리소스를 가져오기 위한 Context.
     */
    public ImageProvider(Context context) {
        // 이미지 리소스를 가져와 리스트에 추가.
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image1));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image2));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image3));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image4));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image5));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image6));
    }

    /**
     * 첫 이미지를 제공한다.
     */
    public Drawable getFirstImage() {
        return imageList.get(0);
    }

    /**
     * 다음 이미지를 제공한다.
     */
    public Drawable getNextImage() {
        // 현재 index를 1 증가시킨 뒤 해당 이미지 반환.
        // 증가한 index가 이미지 개수랑 같아지면 0으로 돌아간다.
        if (++curIndex == imageList.size()) {
            curIndex = 0;
        }

        return imageList.get(curIndex);
    }
}
