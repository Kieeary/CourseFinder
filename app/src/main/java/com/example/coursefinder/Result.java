package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.exercise.ListViewAdapter;

public class Result extends AppCompatActivity {

    ListView listView;
    ImageButton imageButton;

    ListView listView2;

    ListView listView3;

    int[] image = {R.drawable.map};
    String[] placeName = {"가게이름"};

    int[] image2 = {R.drawable.map};
    String[] placeName2 = {"가게이름"};

    int[] image3 = {R.drawable.map};
    String[] placeName3 = {"가게이름"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (ListView)findViewById(R.id.listView);
        imageButton = (ImageButton)findViewById(R.id.detailBtn);

        listView2 = (ListView)findViewById(R.id.listView2);

        listView3 = (ListView)findViewById(R.id.listView3);

        ResultAdapter resultAdapter = new ResultAdapter(Result.this, image, placeName);
        listView.setAdapter(resultAdapter);

        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, placeName2);
        listView2.setAdapter(resultAdapter2);

        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, placeName3);
        listView3.setAdapter(resultAdapter3);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView1);
        textView.setText(intent.getStringExtra("category"));

    }
}