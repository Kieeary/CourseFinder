package com.example.coursefinder.Course;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.exercise.ExerciseDetailCourse;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseListResultGrid2 extends BaseAdapter {

    private Context mContext;
    private final String[] buttonStr;
    private ArrayList<CourseListVo> courseLists = new ArrayList<CourseListVo>();

    public CourseListResultGrid2(Context mContext, String[] buttonStr, ArrayList<CourseListVo> courseLists) {
        this.mContext = mContext;
        this.buttonStr = buttonStr;
        this.courseLists = courseLists;
    }

    @Override
    public int getCount() {
        return courseLists.size();
    }

    @Override
    public CourseListVo getItem(int i) {
         return courseLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.course_dblist_grid_single, null);
            ImageView courseId = (ImageView) grid.findViewById(R.id.courseID);
            TextView courseName = (TextView) grid.findViewById(R.id.place_name);
            TextView courseGrade = (TextView) grid.findViewById(R.id.textView4);
            ImageView add_btn = (ImageView) grid.findViewById(R.id.add_btn);

            Glide.with(grid).load(courseLists.get(position).getCi_img().replaceAll("\\\\", ""))
            .error(R.drawable.bakery).fallback(R.drawable.lens).into(courseId);
            courseName.setText(courseLists.get(position).getCi_name());
            courseGrade.setText(courseLists.get(position).getCi_grade() + "");

            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CourseDetail.class);
                    intent.putExtra("courseId", courseLists.get(position).getCi_idx());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

        }else{
            grid = (View) convertView;
        }



        return grid;

    }
}

