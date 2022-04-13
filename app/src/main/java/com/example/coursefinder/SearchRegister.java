package com.example.coursefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchRegister extends AppCompatActivity {

    private Button cancelBtn;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_register);

        cancelBtn = findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchRegister.this, CourseListResult.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });
        nextBtn = findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchRegister.this, CourseListResult.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });


    }
}
