package com.example.coursefinder.Course;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.coursefinder.Course.CourseChangePlace;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseListSelectGrid extends BaseAdapter {
    private Context mContext;
//    private final String[] web;
    private final String[] course;
    private final String[] category_list;
    private final int[] Imageid;
    private Map<Integer, ArrayList<PlaceList>> orderschResults;
    private final int select_position;
    private final int[] index_arr;
//    ArrayList<CourseInfo> placeLists;

    public CourseListSelectGrid(Context mContext, int[] imageid, String[] course, String[] category_list, Map<Integer, ArrayList<PlaceList>> orderschResults, int position, int index_arr[]) {
        this.mContext = mContext;
        this.Imageid = imageid;
        this.course = course;
        this.category_list = category_list;
        this.orderschResults = orderschResults;
        this.select_position = position;
        this.index_arr = index_arr;
    }



    @Override
    public int getCount() {
        return course.length;
    }

    @Override
    public String getItem(int i) {
//        return placeLists.get(i).getCp_name();
        return "";
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.course_detail_grid_single, null);
            convertView = inflater.inflate(R.layout.result_item, null);

            TextView placeName = (TextView) grid.findViewById(R.id.placeName);
            ImageView imageView = (ImageView)grid.findViewById(R.id.placeImg);
            TextView textView2 = (TextView) grid.findViewById(R.id.placeAddr);
            ImageButton changeButton = (ImageButton) grid.findViewById(R.id.add_btn);

            imageView.setImageResource(Imageid[position]);
            placeName.setText(orderschResults.get(position+1).get(index_arr[position]).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));

            if(orderschResults.get(position+1).get(index_arr[position]).getImgLink()!= null){
                Glide.with(convertView).load(orderschResults.get(position+1).get(index_arr[position]).getImgLink()).into(imageView);
            }


            changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext.getApplicationContext(), CourseChangePlace.class);
                    intent.putExtra("category", category_list[position]);
                    mContext.startActivity(intent);
                }

            });

            //Log.d("TAG", "IN VIEW : "+  placeLists.get(position).getCp_name());



        }else{
            grid = (View) convertView;

        }


        return grid;
    }
}
