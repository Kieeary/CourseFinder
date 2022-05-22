package com.example.coursefinder.exercise;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.Course.CourseListResult;
import com.example.coursefinder.MainCategory;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.ExerciseReviewDetail;
import com.example.coursefinder.courseVo.SelectFromExReview;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Context context;

    private ArrayList<ExerciseReviewDetail> exerciseReviews;

    public ListViewAdapter(Context context,ArrayList<ExerciseReviewDetail> exerciseReviews ) {
        this.context = context;
        this.exerciseReviews = exerciseReviews;
    }

    LayoutInflater inflater;


    @Override
    public int getCount() {
        return exerciseReviews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.exercise_list_single_item, null);
        }

        //ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
        TextView courseNameTv = convertView.findViewById(R.id.courseName);
        TextView coursePlaceTv = convertView.findViewById(R.id.courseReview);
        TextView courseScoreTv = convertView.findViewById(R.id.courseScore);

        //Glide.with(convertView).load("http:10.0.2.2/uploads/test5d1e705b1-32ae-4692-a3f5-6a60a2294418.png.jpg").error(R.drawable.bakery).into(courseimgIv);
        courseNameTv.setText(exerciseReviews.get(i).getWcr_id());
        coursePlaceTv.setText(exerciseReviews.get(i).getWcr_content());
        courseScoreTv.setText(exerciseReviews.get(i).getWcr_grade()+"");


        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.detailBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 리뷰 상세보기 페이지가 따로 필요할 것 같지않아서 주석처리
                //Intent intent = new Intent(view.getContext(), ExerciseDetailReview.class);
                //   intent.putExtra("wiidx", exerciseInfos.get(i).getWi_idx());
                //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
