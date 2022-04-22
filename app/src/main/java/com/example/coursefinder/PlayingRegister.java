package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.Course.CourseList;
import com.example.coursefinder.adapter.GridAdapter;


public class PlayingRegister extends AppCompatActivity {

    GridView gridView;
    GridView gridView2;
    GridView gridView3;
    GridView gridView4;

    int[] categoryImgId = {R.drawable.hansik, R.drawable.ilsik, R.drawable.yangsik, R.drawable.jungsik,
            R.drawable.meat, R.drawable.bunsik, R.drawable.yasik, R.drawable.seafood, R.drawable.salad};
    String[] categoryNameId = {"한식", "일식", "양식", "중식", "고기", "분식", "야식", "해산물", "샐러드"};

    int[] cafeCategoryImgId = {R.drawable.cafe, R.drawable.bakery, R.drawable.dessert, R.drawable.beer, R.drawable.wine, R.drawable.cocktail};
    String[] cafeCategoryNameId = {"카페", "베이커리", "디저트", "맥주", "와인", "칵테일"};

    int[] gameCategoryImgId = {R.drawable.pcroom, R.drawable.singingroom, R.drawable.escaperoom,
            R.drawable.dangguroom, R.drawable.bowlingroom, R.drawable.boardgameroom};
    String[] gameCategoryNameId = {"PC방", "노래방", "방탈출", "당구장", "볼링장", "보드게임방"};

    int[] cultureCategoryImgId ={R.drawable.movie, R.drawable.exhibition, R.drawable.reading};
    String[] cultureCategoryNameId ={"영화", "전시회", "독서", "공연"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_register);

        gridView = findViewById(R.id.gridViewId);
        gridView2 = findViewById(R.id.gridViewId2);
        gridView3 = findViewById(R.id.gridViewId3);
        gridView4 = findViewById(R.id.gridViewId4);

        GridAdapter gridAdapter = new GridAdapter(PlayingRegister.this, categoryNameId, categoryImgId);
        gridView.setAdapter(gridAdapter);

        GridAdapter cafeGridAdapter = new GridAdapter(PlayingRegister.this,cafeCategoryNameId, cafeCategoryImgId);
        gridView2.setAdapter(cafeGridAdapter);

        GridAdapter gameGridAdapter = new GridAdapter(PlayingRegister.this,gameCategoryNameId, gameCategoryImgId);
        gridView3.setAdapter(gameGridAdapter);

        GridAdapter cultureGridAdapter = new GridAdapter(PlayingRegister.this, cultureCategoryNameId, cultureCategoryImgId);
        gridView4.setAdapter(cultureGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(PlayingRegister.this, "You Clicked on " + categoryNameId[i], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PlayingRegister.this, Result.class);

                intent.putExtra("category", categoryNameId[i].toString());
                startActivity(intent);
            }
        });
    }
}