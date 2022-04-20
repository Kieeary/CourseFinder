package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.mycourse.MyCourse;
import com.example.coursefinder.smallcategory.SmallCategory;

public class MainCategory extends AppCompatActivity {

    private Button courseRecommend;
    private Button courseRegister;
    private Button courseHistory;
    private Button exerciseCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        courseRecommend = findViewById(R.id.courseRecommend);
        courseRegister = findViewById(R.id.courseRegister);
        courseHistory = findViewById(R.id.courseHistory);
        exerciseCourse = findViewById(R.id.exerciseCourse);

        courseRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, SmallCategory.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });
        courseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, PlayingRegister.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });
        courseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, MyCourse.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });
        exerciseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, ExerciseCourseList.class);
                startActivity(intent);
            }
        });

    }
}