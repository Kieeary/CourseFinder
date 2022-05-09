package com.example.coursefinder.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.smallcategory.SmallCategoryOrder;


public class CourseList extends Activity {
    GridView grid;
    String[] buttonStr={
            "식사",
            "카페",
            "오락"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_courselist);

        CourseListGrid adapter = new CourseListGrid(CourseList.this, buttonStr);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CourseList.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        Button nextButton = (Button) findViewById(R.id.next_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourseListResult.class);
                startActivity(intent);
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SmallCategoryOrder.class);
                startActivity(intent);
            }

        });
    }

}
