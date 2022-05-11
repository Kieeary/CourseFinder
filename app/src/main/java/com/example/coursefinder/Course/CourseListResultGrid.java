package com.example.coursefinder.Course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchVo.PlaceList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class CourseListResultGrid  extends BaseAdapter {

    private Context mContext;
    private final String[] buttonStr;
    Map<Integer, ArrayList<PlaceList>> placeList;
//     private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();


    public CourseListResultGrid(Context c, String[] buttonStr,  Map<Integer, ArrayList<PlaceList>> placeList ) { //, ArrayList<CourseListVo> courseLists
        mContext = c;
        this.buttonStr = buttonStr;
        this.placeList = placeList;
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
        int place_count = placeList.get(position).size();
        int i=0, j=0;
        // 각각의 포지션에 장소들 넣어주기
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.courselist_result_grid_single, null);

            for(; j<place_count; j++){

            }


            Button button1 = (Button)grid.findViewById(R.id.button1);
            Button button2 = (Button)grid.findViewById(R.id.button2);
            Button button3 = (Button)grid.findViewById(R.id.button3);
            button1.setText(buttonStr[position]);
            button2.setText(buttonStr[position]);
            button3.setText(buttonStr[position]);

        } else {
            grid = (View) convertView;
        }


        /* 코스 번호 이름 평점을 불러온다
        courseId.setText(courseLists.get(position).getCi_img().replaceAll("\\\\", ""));
        courseName.setText(courseLists.get(position).getCi_name());
        courseGrade.setText(courseLists.get(position).getCi_grade()+"");


         */
        return grid;
    }
}
