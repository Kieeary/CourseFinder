package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainCategory extends AppCompatActivity {

    private Button courseRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        courseRegister = findViewById(R.id.courseRegister);
        courseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainCategory.this, PlayingRegister.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동
            }
        });

    }
}