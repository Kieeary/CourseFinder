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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseListResultGrid  extends BaseAdapter {

    private Context mContext;
    private final String[] buttonStr;
    private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();


    public CourseListResultGrid(Context c, String[] buttonStr, ArrayList<CourseListVo> courseLists ) {
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
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.courselist_result_grid_single, null);

            Button button1 = (Button)grid.findViewById(R.id.button1);
            Button button2 = (Button)grid.findViewById(R.id.button2);
            Button button3 = (Button)grid.findViewById(R.id.button3);
            button1.setText(buttonStr[position]);
            button2.setText(buttonStr[position]);
            button3.setText(buttonStr[position]);

            TextView couId = (TextView)grid.findViewById(R.id.courseID);
            TextView couName = (TextView)grid.findViewById(R.id.textView10);
            TextView couInfo = (TextView)grid.findViewById(R.id.textView4);

            couId.setText(courseLists.get(position).getCi_idx());
            couName.setText(courseLists.get(position).getCi_name());
            couInfo.setText(courseLists.get(position).getCi_info());

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
