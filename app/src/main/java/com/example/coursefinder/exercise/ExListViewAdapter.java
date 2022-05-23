package com.example.coursefinder.exercise;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.ExerciseInfo;
import com.example.coursefinder.courseVo.ExerciseReviewDetail;

import java.util.ArrayList;

public class ExListViewAdapter extends BaseAdapter {

    Context context;

    private ArrayList<ExerciseInfo> exerciseInfos;

    public ExListViewAdapter(Context context,ArrayList<ExerciseInfo> exerciseInfos ) {
        this.context = context;
        this.exerciseInfos = exerciseInfos;
    }

    LayoutInflater inflater;


    @Override
    public int getCount() {
        return exerciseInfos.size();
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

        ImageView courseimgIv = convertView.findViewById(R.id.courseImg);
        TextView courseNameTv = convertView.findViewById(R.id.courseName);
        // TextView coursePlaceTv = convertView.findViewById(R.id.coursePlace);
        TextView courseScoreTv = convertView.findViewById(R.id.courseScore);

        Glide.with(convertView).load("http:10.0.2.2/uploads/test5d1e705b1-32ae-4692-a3f5-6a60a2294418.png.jpg").
                error(R.drawable.bakery)
                .fallback(R.drawable.lens)
                .into(courseimgIv);
        courseNameTv.setText(exerciseInfos.get(i).getWi_name());
        // coursePlaceTv.setText(exerciseInfos.get(i).getWi_info());
        courseScoreTv.setText(exerciseInfos.get(i).getWi_grade()+"");


        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.detailBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseDetailCourse.class);
                intent.putExtra("wiid", exerciseInfos.get(i).getWi_idx());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
