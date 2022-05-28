package com.example.coursefinder.ranking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.coursefinder.Course.CourseDetail;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseListVo;
import com.example.coursefinder.exercise.ExerciseDetailCourse;

import java.util.ArrayList;

public class RankingGrid2 extends BaseAdapter{
    Context mContext;

    private ArrayList<ExerciseListVo> exerciseListVos;

    /*
        private final String[] description;
        private final int[] courseImage;
        private final int[] deilImage;

        public RankingGrid(Context c,String[] description, int[] courseImage, int[] detailImage ) {
            mContext = c;
            this.description = description;
            this.courseImage = courseImage;
            this.detailImage = detailImage;
        }
    */
    public RankingGrid2(Context context, ArrayList<ExerciseListVo> exerciseListVos ) {
        this.mContext = context;
        this.exerciseListVos = exerciseListVos;
    }

    LayoutInflater inflater;
/*
    public RankingGrid(Context context){
        this.mContext = context;
    }*/

    @Override
    public int getCount() {
        return exerciseListVos.size();
        //return description.length;
    }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        /*View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_ranking_grid_single, null);

            TextView rankText = (TextView) grid.findViewById(R.id.rank_text);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView representImage = (ImageView)grid.findViewById(R.id.grid_image);
            ImageView lensImage = (ImageView)grid.findViewById(R.id.detail_image);

            rankText.setText("1");
            textView.setText(courseListvos.get(position).getCi_name());

//            rankText.setText(Integer.toString(position+1));
//            textView.setText(description[position]);
//            representImage.setImageResource(courseImage[position]);
//            lensImage.setImageResource(detailImage[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;*/

        if(inflater == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_ranking_grid_single, null);
        }


        TextView courseName = convertView.findViewById(R.id.courseName);
        TextView courseInfo = convertView.findViewById(R.id.courseInfo);
        TextView courseScore = convertView.findViewById(R.id.courseScore);

        courseName.setText(exerciseListVos.get(position).getWi_name());
        courseInfo.setText(exerciseListVos.get(position).getWi_info());
        // int는 어떻게 처리해야하지 CourseListVo에서 String으로 바꿈
        courseScore.setText(exerciseListVos.get(position).getWi_grade());

        ImageButton detailImg = convertView.findViewById(R.id.detail_image);

        detailImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseDetailCourse.class);
                intent.putExtra("wiid", exerciseListVos.get(position).getWi_idx());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}