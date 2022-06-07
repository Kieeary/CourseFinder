package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coursefinder.PlayingRegister.PlayingRegister;
import com.example.coursefinder.exercise.ExerciseCourseRegit;

public class CourseRegitSelect extends AppCompatActivity {

    private Button play;
    private Button ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_regit_select);

        Intent intent = getIntent();
        String miid = intent.getStringExtra("miid");

        play = findViewById(R.id.playregit);
        ex = findViewById(R.id.exregit);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CourseRegitSelect.this, PlayingRegister.class);
                startActivity(intent1);
            }
        });

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CourseRegitSelect.this, ExerciseCourseRegit.class);
                intent1.putExtra("miid", miid);
                startActivity(intent1);
            }
        });
    }
}