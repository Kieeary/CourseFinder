package com.example.coursefinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.exercise.ExerciseCourseRegit;
import com.example.coursefinder.playingRegister.PlayingRegister;
import com.example.coursefinder.ranking.Ranking;
import com.example.coursefinder.smallcategory.SmallCategory;
import com.google.gson.Gson;

public class Main extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Main.this, MainCategory.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
                finish();
            }
        }, 1500); //딜레이 타임 조절


    }
}
