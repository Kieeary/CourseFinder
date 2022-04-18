package com.example.coursefinder.Course;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;


public class CourseListResult extends Activity {

    GridView grid;
    String[] buttonStr={

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_result);

        CourseListResultGrid adapter = new CourseListResultGrid(CourseListResult.this, buttonStr);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CourseListResult.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}
