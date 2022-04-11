package com.example.coursefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView courseName = null;
        TextView user = null;


        //코스 이름 클릭시 작성자가 지금까지 작성한 리뷰 보여줌
        courseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review.this, CourseDetail.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });


        //작성자 이름 클릭시 작성자가 지금까지 작성한 리뷰 보여줌
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review.this, ReviewDetail.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });
    }
}
