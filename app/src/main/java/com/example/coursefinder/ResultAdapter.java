package com.example.coursefinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursefinder.searchVo.ImageList;
import com.example.coursefinder.searchVo.PlaceList;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {

    Context context;
    int [] image;
    ArrayList<PlaceList> placeName;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();

    public ResultAdapter(Context context, int [] image, ArrayList<PlaceList> placeName) {
        this.context = context;
        this.image = image;
        this.placeName = placeName;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return placeName.size();
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
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exercise_list_single_item, null);
        }

        ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
        TextView courseNameTv = convertView.findViewById(R.id.courseName);

        courseimgIv.setImageResource(image[0]);
        courseNameTv.setText(placeName.get(i).getTitle());


        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.detailBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return convertView;
    }
}

