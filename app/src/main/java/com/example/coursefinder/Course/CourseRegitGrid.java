package com.example.coursefinder.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseRegitGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();

    public CourseRegitGrid(Context mContext, String[] web, int[] imageid, ArrayList<PlaceList> placeLists) {
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
    public Object getItem(int i) {
        return null;
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

            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);

            textView.setText(placeLists.get(position).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));
            textView2.setText(placeLists.get(position).getAddress());
            if(placeLists.get(position).getImgLink()!= null){
                Glide.with(grid).load(placeLists.get(position).getImgLink()).into(imageView);
            }

        }else{
            grid = (View) convertView;
        }

        return grid;
    }
}
