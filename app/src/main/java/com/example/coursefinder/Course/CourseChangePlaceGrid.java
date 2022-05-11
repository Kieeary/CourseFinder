package com.example.coursefinder.Course;

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

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.searchVo.ImageList;
import com.example.coursefinder.searchVo.PlaceList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseChangePlaceGrid extends BaseAdapter {

    Context context;
    int [] image;
    ArrayList<PlaceList> placeName = new ArrayList<PlaceList>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    public CourseChangePlaceGrid(Context context, int [] image, ArrayList<PlaceList> placeName, Map<Integer, ArrayList<PlaceList>> orderschResults) {
        this.context = context;
        this.image = image;
        this.placeName = placeName;
        this.orderschResults = orderschResults;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return placeName.size();
    }

    @Override
    public PlaceList getItem(int i) {
        return placeName.get(i);
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
            convertView = inflater.inflate(R.layout.change_item, null);
        }

        ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
        TextView courseNameTv = convertView.findViewById(R.id.courseName);
        ImageButton changeButton = convertView.findViewById(R.id.change_btn);

        courseimgIv.setImageResource(image[0]);
        courseNameTv.setText(placeName.get(i).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));
        if(placeName.get(i).getImgLink()!= null){
            Glide.with(convertView).load(placeName.get(i).getImgLink()).into(courseimgIv);
        }
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getApplicationContext(), CourseChangePlace.class);
                intent.putExtra("place", placeName.get(i));

                context.startActivity(intent);
            }

        });

//        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.detailBtn);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        return convertView;
    }
}

