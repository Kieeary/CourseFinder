package com.example.coursefinder.mycourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coursefinder.R;

public class CourseReview extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_review);
        Intent courseIntent = getIntent();
        String courseName = courseIntent.getExtras().getString("courseName");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(courseName);
        Button cancelButton = (Button) findViewById(R.id.button3);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCourse.class);
                startActivity(intent);
            }

        });
    }
}
