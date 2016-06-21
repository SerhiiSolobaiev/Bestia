package com.myandroid.testbestia.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.myandroid.testbestia.R;

import java.util.ArrayList;
import java.util.List;

public class ListNewsAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private List<Bitmap> data = new ArrayList<>();

    public ListNewsAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();

            holder.imageFromJson = (ImageView) row.findViewById(R.id.imageViewFromJson);
            holder.imagePosterFrame = (ImageView) row.findViewById(R.id.imageViewPosterFrame);
            holder.imageSprt0 = (ImageView) row.findViewById(R.id.imageViewPosterSprt0);
            holder.imageSprt1 = (ImageView) row.findViewById(R.id.imageViewPosterSprt1);

            if (position % 2 == 0) {
                holder.imageFromJson.setRotation(1);
                holder.imagePosterFrame.setRotation(1);
                holder.imageSprt0.setVisibility(View.VISIBLE);
                holder.imageSprt1.setVisibility(View.GONE);
            } else {
                holder.imageFromJson.setRotation(-0.7f);
                holder.imagePosterFrame.setRotation(-0.7f);
                holder.imageSprt0.setVisibility(View.GONE);
                holder.imageSprt1.setVisibility(View.VISIBLE);
            }

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Bitmap item = data.get(position);
        if (item != null) {
            holder.imageFromJson.setImageBitmap(item);
        }
        return row;
    }

    static class ViewHolder {
        ImageView imageFromJson;
        ImageView imagePosterFrame;
        ImageView imageSprt0;
        ImageView imageSprt1;
    }
}
