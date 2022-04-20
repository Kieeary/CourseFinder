package com.example.coursefinder.ranking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;

public class Ranking extends Activity {
    GridView grid;
    String[] description={
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5",
            "코스: A->B->C\n비용: 70000원\n별점: 4.5"
    };
    int[] courseImage = {
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,

    };
    int[] detailImage = {
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,
            R.drawable.lens,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        com.example.coursefinder.ranking.RankingGrid adapter = new com.example.coursefinder.ranking.RankingGrid(Ranking.this, description, courseImage, detailImage);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Ranking.this, "You Clicked at " +description[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}