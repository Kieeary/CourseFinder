package com.example.coursefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;

public class CultureGridAdapter extends BaseAdapter {
    Context context;

    String[] cultureCategoryNameId;
    int[] cultureCategoryImgId;

    public CultureGridAdapter(Context context, String[] cultureCategoryNameId, int[] cultureCategoryImgId) {
        this.context = context;
        this.cultureCategoryNameId = cultureCategoryNameId;
        this.cultureCategoryImgId = cultureCategoryImgId;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return cultureCategoryImgId.length;
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

        imageView.setImageResource(cultureCategoryImgId[i]);
        textView.setText(cultureCategoryNameId[i]);

        return convertView;
    }
}
