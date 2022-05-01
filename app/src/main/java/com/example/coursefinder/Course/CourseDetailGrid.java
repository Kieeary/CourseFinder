package com.example.coursefinder.Course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    ArrayList<CourseInfo> placeLists;

    public CourseDetailGrid(Context mContext, String[] web, int[] imageid, ArrayList<CourseInfo> placeLists) {
        this.mContext = mContext;
        this.web = web;
        Imageid = imageid;
        this.placeLists = placeLists;
    }



    @Override
    public int getCount() {
        return placeLists.size();
    }

    @Override
    public String getItem(int i) {
        return placeLists.get(i).getCp_name();
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

           TextView textView = (TextView) grid.findViewById(R.id.placeName);
            ImageView imageView = (ImageView)grid.findViewById(R.id.placeImg);
            TextView textView2 = (TextView) grid.findViewById(R.id.placeAddr);


            imageView.setImageResource(Imageid[position]);

            textView.setText(placeLists.get(position).getCp_name().replaceAll("<b>", " ").replaceAll("</b>", " "));
            if(placeLists.get(position).getCp_img()!= null && !(placeLists.get(position).getCp_img().equals("")) ){
                Glide.with(grid).load(placeLists.get(position).getCp_img()).into(imageView);
            }
            textView2.setText(placeLists.get(position).getCp_addr());

            Log.d("TAG", "IN VIEW : "+  placeLists.get(position).getCp_name());



        }else{
            grid = (View) convertView;

        }


        return grid;
    }
}
