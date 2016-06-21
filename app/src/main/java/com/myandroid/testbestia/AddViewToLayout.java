package com.myandroid.testbestia;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AddViewToLayout {
    private final String LOG_TAG = AddViewToLayout.class.getSimpleName();

    //compression ratio of the image (depends on screen width)
    private double compressionRatio;
    private Context context;

    public AddViewToLayout(Context context) {
        this.context = context;
        calculateCompressionRatio();
    }

    private void calculateCompressionRatio() {
        int backgroundImageWidth = 750;
        //getResources().getDrawable(R.drawable.main_part_1).getMinimumWidth() - return 1125!!!

        compressionRatio = Utility.getScreenWidth(context) / (double) backgroundImageWidth;
        Log.v(LOG_TAG, "compressionRatio = " + compressionRatio);
    }

    /**
     * Add poster from json to main view
     *
     * @param baseView On what add image
     * @param bitmap   Image from json
     * @param centerX  x coordinate of the center
     * @param centerY  y coordinate of the center
     * @param angle    image rotation angle
     */
    public void addPosterToBaseView(ViewGroup baseView, Bitmap bitmap, int centerX, int centerY, float angle) {
        ImageView imageView = new ImageView(context);

        int width = (int) (bitmap.getWidth() * compressionRatio);
        int height = (int) (bitmap.getHeight() * compressionRatio);

        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setImageBitmap(bitmap);
        imageView.setRotation(angle);
        imageView.setAdjustViewBounds(true);

        //calculate top/left position:
        params.leftMargin = (int) (centerX * compressionRatio - width / 2);
        params.topMargin = (int) (centerY * compressionRatio - height / 2);

        baseView.addView(imageView, params);
    }

    /**
     * Add sticker from res/drawable
     *
     * @param baseView   On what add image
     * @param bitmap     Sticker from resources
     * @param leftMargin x coordinate
     * @param topMargin  y coordinate
     */
    public void addStickerToBaseView(ViewGroup baseView, Bitmap bitmap, int leftMargin, int topMargin) {
        ImageView imageView = new ImageView(context);
        double k = 0.48;//width coefficient
        int width = (int) (bitmap.getWidth() * k);

        RelativeLayout.LayoutParams params = new RelativeLayout
                .LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setImageBitmap(bitmap);
        imageView.setAdjustViewBounds(true);

        //calculate top/left position:
        params.leftMargin = (int) (leftMargin * compressionRatio);
        params.topMargin = (int) (topMargin * compressionRatio);

        baseView.addView(imageView, params);
    }
}
