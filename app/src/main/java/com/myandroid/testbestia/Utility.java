package com.myandroid.testbestia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    /**
     * Get posters from json file which located in values/raw folder
     *
     * @return Array of images
     */
    public static List<Bitmap> getPostersFromJson(InputStream inputStream) {
        final String nfo = "nfo";
        final String nws = "nws";
        final String pst = "pst";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<Bitmap> bitmaps = new ArrayList<>();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());
            JSONObject jObjectResult = jObject.getJSONObject(nfo);
            JSONArray jArray = jObjectResult.getJSONArray(nws);
            for (int i = 0; i < jArray.length(); i++) {
                String image = jArray.getJSONObject(i).getString(pst);
                bitmaps.add(getImageFromBase64(image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmaps;
    }

    /**
     * Convert string to image
     *
     * @param imageInBase64 Image in base64 format
     * @return Image
     */
    private static Bitmap getImageFromBase64(String imageInBase64) {

        byte[] decodedString = Base64.decode(imageInBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    /**
     * Get screen width
     *
     * @param context Context
     * @return Width in px
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }
}
