package com.myandroid.testbestia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.myandroid.testbestia.AddViewToLayout;
import com.myandroid.testbestia.R;
import com.myandroid.testbestia.Utility;
import com.myandroid.testbestia.adapter.ListNewsAdapter;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class NewsActivity extends AppCompatActivity {

    private FrameLayout backToMainScreen;
    private RelativeLayout baseView;
    private ListView listNews;
    private ProgressBar progressBar;

    private List<Bitmap> bitmapsFromJson;
    private ListNewsAdapter listNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();

        setOnBackClickListener();

        bitmapsFromJson = new ArrayList<>();
        listNewsAdapter = new ListNewsAdapter(this, R.layout.list_news_item, bitmapsFromJson);
        listNews.setAdapter(listNewsAdapter);

        AddViewToLayout viewToLayout = new AddViewToLayout(this);
        viewToLayout.addStickerToBaseView(baseView, BitmapFactory.decodeResource(getResources(),
                R.drawable.news_best), 0, 18);

        //get images from json and put them to list
        new AddBitmapsToListViewTask().execute();
    }

    private void setOnBackClickListener() {
        backToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsActivity.this, MainActivity.class));
            }
        });
    }

    private void initViews() {
        backToMainScreen = (FrameLayout) findViewById(R.id.backToMainScreen);
        baseView = (RelativeLayout) findViewById(R.id.baseView);
        listNews = (ListView) findViewById(R.id.listView);
        OverScrollDecoratorHelper.setUpOverScroll(listNews);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private class AddBitmapsToListViewTask extends AsyncTask<Void, Void, List<Bitmap>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected List<Bitmap> doInBackground(Void... params) {
            return Utility.getPostersFromJson(getResources().openRawResource(R.raw.uk_news));
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            showProgress(false);
            bitmapsFromJson.addAll(bitmaps);
            listNewsAdapter.notifyDataSetChanged();
        }
    }
}
