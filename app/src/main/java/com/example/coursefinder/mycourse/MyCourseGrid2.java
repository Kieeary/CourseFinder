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
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseListVo;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.SelectExCourseList;
import com.example.coursefinder.exercise.ExerciseDetailCourse;

import java.util.ArrayList;

public class MyCourseGrid2 extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private ArrayList<ExerciseListVo> exerciseListVos = new ArrayList<ExerciseListVo>();

    public MyCourseGrid2(Context c, String[] web, int[] Imageid, SelectExCourseList exCourseList) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.exerciseListVos = exCourseList.getExerciseLists();
        Log.d("TAG", exerciseListVos.size()+" << SIZE ");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public ExerciseListVo getItem(int position) {
        // TODO Auto-generated method stub
        return exerciseListVos.get(position);
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

            textView.setText(exerciseListVos.get(position).getWi_name());

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(), ExCourseReview.class);
                    // 해당 코스(즐겨찾기 뷰에서 가져오는)의 객체를 넘겨줌
                    intent.putExtra("Exinfo", exerciseListVos.get(position));
                    mContext.startActivity(intent);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext.getApplicationContext(), ExerciseDetailCourse.class);
                    intent.putExtra("wiid", exerciseListVos.get(position).getWi_idx());
                    mContext.startActivity(intent);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
