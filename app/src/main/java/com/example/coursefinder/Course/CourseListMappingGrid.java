package com.example.coursefinder.Course;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class CourseListMappingGrid  extends BaseAdapter {

    private Context mContext;
    private final String[] courseOrder;
    private final String[] courseList;
    private final String[] categoryList;
    private final String[] result;
//     private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();
    String course="";


    public CourseListMappingGrid(Context c, String[] courseList, String[] courseOrder, String[] categoryList, String[] result) { //, ArrayList<CourseListVo> courseLists
        mContext = c;
        this.courseOrder = courseOrder;
        this.courseList = courseList;
        this.categoryList = categoryList;
        this.result = result;
        //      this.courseLists = courseLists;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return courseList.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
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
        int i=0;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView == null) {
//            grid = new View(mContext);
//            grid = inflater.inflate(R.layout.activity_courselist_mapping_grid_single, null);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_courselist_mapping_grid_single, null);
            }

//            ImageView img = (ImageView)grid.findViewById(R.id.Cimg);
//            TextView place = (TextView)grid.findViewById(R.id.categoryOrder);
//            ImageButton detail_btn = (ImageButton)grid.findViewById(R.id.detail_btn);
        ImageView img = (ImageView)convertView.findViewById(R.id.Cimg);
        TextView place = (TextView)convertView.findViewById(R.id.categoryOrder);
        ImageButton detail_btn = (ImageButton)convertView.findViewById(R.id.detail_btn);
            img.setImageResource(R.drawable.bakery);
            place.setText(courseOrder[0]);
            course = courseList[position];

            detail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext.getApplicationContext(), CourseListSelect.class);
                    intent.putExtra("course", courseList[position]);
                    intent.putExtra("category", categoryList);
                    intent.putExtra("results", result);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }

            });

//        }
//        else {
//            grid = (View) convertView;
//        }

//        return grid;
        return convertView;
    }
}
