package com.example.coursefinder.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.coursefinder.R;


public class ExerciseCourseList extends AppCompatActivity {


    ListView listView;
    ImageButton imageButton;
    Button btn;

    // 나중에 db 내용을 받아오는 부분
    int[] courseImgId = {R.drawable.map, R.drawable.map};
    String[] courseNameId = {"김철수", "박철수"};
    String[] coursePlaceId = {"A B C", "C B A"};
    String[] courseScore = {"5점", "5점"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_course_list);

        listView = (ListView)findViewById(R.id.listView);
        imageButton = (ImageButton)findViewById(R.id.detailBtn);

        // img를 안넣어줘서 오류가 발생하는 것 같아서 일단 ListViewAdapter.java에서 img부분을 모두 주석 처리 하였음
        ListViewAdapter listViewAdapter = new ListViewAdapter(ExerciseCourseList.this, /*courseImgId,*/ courseNameId, coursePlaceId, courseScore);
        listView.setAdapter(listViewAdapter);

    }
}