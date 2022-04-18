package com.example.coursefinder.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursefinder.R;
import com.example.coursefinder.Review.Review;
import com.example.coursefinder.Review.ReviewDetail;

public class CourseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        TextView user = null;
        TextView review = null;

        //작성자 이름 클릭시 작성자가 지금까지 작성한 리뷰로 넘어감
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewDetail.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });
        //리뷰 제목 클릭시 해당 코스 리뷰로 넘어감
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Review.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });
    }
}
