package com.ellie.materialdesignex;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ImageProvider {
    private List<Drawable> imageList = new ArrayList<>();
    private int curIndex = 0;

    public ImageProvider(Context context) {
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image1));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image2));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image3));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image4));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image5));
        imageList.add(ContextCompat.getDrawable(context, R.drawable.album_image6));
    }

    public Drawable getFirstImage() {
        return imageList.get(0);
    }

    public Drawable getNextImage() {
        if (++curIndex >= imageList.size()) {
            curIndex = 0;
        }

        return imageList.get(curIndex);
    }
}
