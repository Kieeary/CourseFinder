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

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseListMappingGrid  extends BaseAdapter {

    private Context mContext;
    private final String[] buttonStr;
    ArrayList<PlaceList> placeName;
//     private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();


    public CourseListMappingGrid(Context c, String[] buttonStr, ArrayList<PlaceList> placeName ) { //, ArrayList<CourseListVo> courseLists
        mContext = c;
        this.buttonStr = buttonStr;
        this.placeName = placeName;
        //      this.courseLists = courseLists;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return buttonStr.length;
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
        String course="";
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_courselist_mapping_grid_single, null);
            for(; i<placeName.size()-1; i++) {
                course += placeName.get(i).getTitle() +"->";
            }
            course += placeName.get(i).getTitle();

            ImageView img = (ImageView)grid.findViewById(R.id.img);
            TextView place = (TextView)grid.findViewById(R.id.categoryOrder);
            ImageButton add_btn = (ImageButton)grid.findViewById(R.id.add_btn);

            img.setImageResource(R.drawable.bakery);
            place.setText(course);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
