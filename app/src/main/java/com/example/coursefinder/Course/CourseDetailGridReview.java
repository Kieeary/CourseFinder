package com.example.coursefinder.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseReviewDetail;
import com.example.coursefinder.courseVo.ExerciseReviewDetail;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseDetailGridReview extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private ArrayList<CourseReviewDetail> reviews;

    public CourseDetailGridReview(Context mContext, String[] web, int[] imageid, ArrayList<CourseReviewDetail> reviews) {
        this.mContext = mContext;
        this.web = web;
        Imageid = imageid;
        this.reviews = reviews;
    }


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public CourseReviewDetail getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.course_detail_grid_single, null);

            TextView textView = (TextView) grid.findViewById(R.id.placeName);
            ImageView imageView = (ImageView)grid.findViewById(R.id.placeImg);
            TextView textView2 = (TextView) grid.findViewById(R.id.placeAddr);
            TextView grade = (TextView) grid.findViewById(R.id.grade);
            textView.setText(reviews.get(i).getCr_name());
            Glide.with(grid).load("http:10.0.2.2/uploads/" + reviews.get(i).getCr_img())
                    .placeholder(R.drawable.course_image)
                    .error(R.drawable.course_image)
                    .fallback(R.drawable.course_image)
                    .into(imageView);
            textView2.setText(reviews.get(i).getCr_content());
            grade.setText(reviews.get(i).getCr_grade()+"");
        }else{
            grid = (View) convertView;
        }
        return grid;
    }
}
