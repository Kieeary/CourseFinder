package com.example.coursefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CourseList extends AppCompatActivity {

    ImageButton addBtn;
    Button nextBtn;
    Button cancelBtn;

    //고른 카테고리 개수
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);

        addBtn = findViewById(R.id.add_btn);
        nextBtn = findViewById(R.id.next_btn);
        cancelBtn = findViewById(R.id.cancel_btn);

        //카테고리 순서대로 해당 카테고리당 장소보여준다
        //고른 카테고리 개수만큼 화면이동 후 최종화면 (activity_courselist_result)로 넘어감
        //취소버튼 누를경우 이전화면으로 넘어감

        //다음버튼
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseListResult.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });

        //취소버튼
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseListResult.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });
    }
}
