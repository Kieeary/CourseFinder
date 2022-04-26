package com.example.coursefinder.smallcategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.Course.CourseListMapping;
import com.example.coursefinder.MainCategory;
import com.example.coursefinder.R;
import com.example.coursefinder.mycourse.MyCourse;

public class SmallCategory extends Activity {
    GridView grid;
    String[] buttonStr={
            "일식",
            "한식",
            "한식",
            "일식",
            "한식",
            "일식",
            "한식",
            "일식",
            "한식",
            "일식",
            "한식",
            "일식"
    };
    int[] count = {
            2,
            3,
            4,
            1,
            2,
            3,
            2,
            3,
            1,
            2,
            4,
            2
    }; // db에서 갖고와야할 정보
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.small_category);

        SmallCategoryGrid adapter = new SmallCategoryGrid(SmallCategory.this, buttonStr, count);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SmallCategory.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        Button nextButton = (Button) findViewById(R.id.next_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourseListMapping.class);
                startActivity(intent);
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainCategory.class);
                startActivity(intent);
            }

        });
    }

}