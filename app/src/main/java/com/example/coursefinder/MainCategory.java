package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.exercise.ExerciseCourseRegit;
import com.example.coursefinder.mycourse.MyCourse;
import com.example.coursefinder.playingRegister.PlayingRegister;
import com.example.coursefinder.smallcategory.SmallCategory;
import com.google.gson.Gson;

public class MainCategory extends AppCompatActivity {

    private Button courseRecommend;
    private Button courseRegister;
    private Button courseHistory;
    private Button courseBest;
    private Button exerciseCourse;
    private  SharedPreferences sharedPreferences;
    private MemberLogInResults loginMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        loginMember = gson.fromJson(member, MemberLogInResults.class);
        String miid = loginMember.getMemberInfo().get(0).getId();

        courseRecommend = findViewById(R.id.courseRecommend);
        courseRegister = findViewById(R.id.courseRegister);
        courseHistory = findViewById(R.id.courseHistory);
        courseBest = findViewById(R.id.courseBest);
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
                //  ExerciseCourseList test
                //Intent intent = new Intent(MainCategory.this, MyCourse.class);
                Intent intent = new Intent(MainCategory.this, ExerciseCourseList.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });
        courseBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(MainCategory.this, Ranking.class); //현재 액티비티, 이동하고 싶은 액티비티
//                Intent intent = new Intent(MainCategory.this, ImageUpload.class);
//                startActivity(intent); //액티비티 이동
            }
        });
        exerciseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, ExerciseCourseRegit.class);
//                intent.putExtra("miid", miid);
                startActivity(intent);
            }
        });

    }
}