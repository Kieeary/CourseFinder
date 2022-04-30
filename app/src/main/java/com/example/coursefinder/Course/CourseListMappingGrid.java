package com.example.coursefinder.Course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseListMappingGrid  extends BaseAdapter {

    private Context mContext;
    private final String[] buttonStr;
    ArrayList<PlaceList> placeName;
   private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();


    public CourseListMappingGrid(Context c, String[] buttonStr
            ,ArrayList<CourseListVo> courseLists) { //
        mContext = c;
        this.buttonStr = buttonStr;
        this.courseLists = courseLists;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return buttonStr.length;
    }

    @Override
    public CourseListVo getItem(int position) {
        // TODO Auto-generated method stub
        return courseLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        String course="";
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_courselist_mapping_grid_single, null);

            ImageView Cimg = (ImageView)grid.findViewById(R.id.Cimg);
            TextView place = (TextView)grid.findViewById(R.id.categoryOrder);
            TextView rate = (TextView)grid.findViewById(R.id.star_rating);
           //  ImageButton add_btn = (ImageButton)grid.findViewById(R.id.add_btn);

            Cimg.setImageResource(R.drawable.bakery);
            place.setText(courseLists.get(position).getCi_name());
            rate.setText(courseLists.get(position).getCi_grade()+"");
            Glide.with(grid).load(courseLists.get(position).getCi_img()).override(300).into(Cimg);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
