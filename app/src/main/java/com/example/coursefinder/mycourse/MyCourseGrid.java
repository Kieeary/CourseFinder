package com.example.coursefinder.mycourse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.Course.CourseDetail;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.SelectCourseList;

import java.util.ArrayList;

public class MyCourseGrid extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    ArrayList<CourseListVo> courseInfo = new ArrayList<CourseListVo>();
    public MyCourseGrid(Context c,String[] web,int[] Imageid, SelectCourseList selectCourseList ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.courseInfo = selectCourseList.getCourseLists();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public CourseListVo getItem(int position) {
        // TODO Auto-generated method stub
        return courseInfo.get(position);
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
            grid = inflater.inflate(R.layout.my_course_grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);

            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);

            Button button1 = (Button) grid.findViewById(R.id.button1);
            Button button2 = (Button) grid.findViewById(R.id.button2);

            textView.setText(courseInfo.get(position).getCi_name());
            Glide.with(grid).load(courseInfo.get(position).getCi_img().replaceAll("\\\\", "")).into(imageView);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(), CourseReview.class);
                    intent.putExtra("courseName", textView.getText());

                    mContext.startActivity(intent);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.getApplicationContext(), CourseDetail.class);
                    intent.putExtra("courseId", courseInfo.get(position).getCi_idx());
                    mContext.startActivity(intent);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}