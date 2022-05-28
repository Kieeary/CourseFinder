package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursefinder.R;
import com.example.coursefinder.smallcategory.SmallCategory;

// db, mapping 리스트를 볼건지 선택하기 위한 엑티비티
public class CourseSchSelect extends AppCompatActivity {
    
    private Button dbCourse;    // db에서 불러오는 코스 리스트
    private Button mapCourse;   // listmapping하는 코스 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_sch_select);

        dbCourse = (Button) findViewById(R.id.UserCourse);
        mapCourse = (Button) findViewById(R.id.mappingCourse);


        dbCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseSchSelect.this, SmallCategory.class);
                intent.putExtra("isDb", 1);
                startActivity(intent);
            }
        });

        mapCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseSchSelect.this, SmallCategory.class);
                intent.putExtra("isDb", -1);
                startActivity(intent);
            }
        });


    }
}