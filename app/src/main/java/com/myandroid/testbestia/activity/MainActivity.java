package com.myandroid.testbestia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.myandroid.testbestia.AddViewToLayout;
import com.myandroid.testbestia.R;
import com.myandroid.testbestia.Utility;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class MainActivity extends AppCompatActivity {

    private RelativeLayout baseView;
    private ScrollView scrollView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        //start background thread for decoding resources to bitmaps
        new SetImageResourcesTask().execute();
    }

    private void initViews() {
        baseView = (RelativeLayout) findViewById(R.id.container);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        imageView1 = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
    }

    private void setOnNewsClickListener() {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNews = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intentNews);
            }
        });
    }

    private class SetImageResourcesTask extends AsyncTask<Void, Void, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(Void... params) {
            List<Bitmap> bitmaps = new ArrayList<>();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; //for using less memory
            bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.main_part_1, options));
            bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.main_part_2, options));
            bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.main_part_3, options));
            return bitmaps;
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            imageView1.setImageBitmap(bitmaps.get(0));
            imageView2.setImageBitmap(bitmaps.get(1));
            imageView3.setImageBitmap(bitmaps.get(2));
            setOnNewsClickListener();

            //posters from json:
            List<Bitmap> bitmapsFromJson = Utility.getPostersFromJson(getResources()
                    .openRawResource(R.raw.uk_main));

            AddViewToLayout viewToLayout = new AddViewToLayout(getApplicationContext());
            viewToLayout.addPosterToBaseView(baseView, bitmapsFromJson.get(0), 236, 790, 3);
            viewToLayout.addPosterToBaseView(baseView, bitmapsFromJson.get(1), 440, 850, -1);
            viewToLayout.addPosterToBaseView(baseView, bitmapsFromJson.get(2), 190, 3740, 2);
            viewToLayout.addPosterToBaseView(baseView, bitmapsFromJson.get(3), 478, 3738, 2.5f);

            //stickers from res/drawable:
            viewToLayout.addStickerToBaseView(baseView, BitmapFactory.decodeResource(getResources(),
                    R.drawable.main_sticks), 106, 610);
            viewToLayout.addStickerToBaseView(baseView, BitmapFactory.decodeResource(getResources(),
                    R.drawable.main_pin), 228, 2718);
            viewToLayout.addStickerToBaseView(baseView, BitmapFactory.decodeResource(getResources(),
                    R.drawable.main_light), 394, 3028);
        }
    }

}
