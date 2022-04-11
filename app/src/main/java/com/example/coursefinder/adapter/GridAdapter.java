package com.example.coursefinder.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;

    String[] categoryNameId;
    int[] categoryImgId;

    public GridAdapter(Context context, String[] categoryNameId, int[] categoryImgId) {
        this.context = context;
        this.categoryNameId = categoryNameId;
        this.categoryImgId = categoryImgId;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return categoryImgId.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.griditem, null);
        }

        ImageView imageView = convertView.findViewById(R.id.categoryImgId);
        TextView textView = convertView.findViewById(R.id.categoryNameId);

        imageView.setImageResource(categoryImgId[i]);
        textView.setText(categoryNameId[i]);

        return convertView;
    }
}
