package com.example.coursefinder.ranking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseReviewDetail;

import java.util.ArrayList;

public class RankingGrid extends BaseAdapter{
    Context mContext;

    private ArrayList<CourseListVo> courseListvos;

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
    public RankingGrid(Context context, ArrayList<CourseListVo> courseListVos ) {
        this.mContext = context;
        this.courseListvos = courseListVos;
    }

    LayoutInflater inflater;
/*
    public RankingGrid(Context context){
        this.mContext = context;
    }*/

    @Override
    public int getCount() {
        return courseListvos.size();
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

        courseName.setText(courseListvos.get(position).getCi_name());
        courseInfo.setText(courseListvos.get(position).getCi_info());
        // int는 어떻게 처리해야하지
        // courseScore.setText((int) courseListvos.get(position).getCi_grade());

        ImageButton detailImg = convertView.findViewById(R.id.detail_image);
        /*
        detailImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), )
            }
        });*/

        return convertView;
    }

}