package com.example.coursefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;

public class GameGridAdapter extends BaseAdapter {

    Context context;

    String[] gameCategoryNameId;
    int[] gameCategoryImgId;

    public GameGridAdapter(Context context, String[] gameCategoryNameId, int[] gameCategoryImgId) {
        this.context = context;
        this.gameCategoryNameId = gameCategoryNameId;
        this.gameCategoryImgId = gameCategoryImgId;
    }

    LayoutInflater inflater;


    @Override
    public int getCount() {
        return gameCategoryImgId.length;
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

        imageView.setImageResource(gameCategoryImgId[i]);
        textView.setText(gameCategoryNameId[i]);

        return convertView;
    }
}
