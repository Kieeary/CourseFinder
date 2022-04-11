package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.coursefinder.adapter.GridAdapter;


public class PlayingRegister extends AppCompatActivity {

    GridView gridView;
    GridView gridView2;
    GridView gridView3;
    GridView gridView4;

    int[] categoryImgId = {R.drawable.hansik, R.drawable.ilsik, R.drawable.yangsik, R.drawable.jungsik,
            R.drawable.bunsik, R.drawable.yasik};
    String[] categoryNameId = {"한식", "일식", "양식", "중식", "분식", "야식"};

    int[] cafeCategoryImgId = {R.drawable.cafe, R.drawable.bakery, R.drawable.makaron};
    String[] cafeCategoryNameId = {"카페", "베이커리", "마카롱"};

    int[] gameCategoryImgId = {R.drawable.pcroom, R.drawable.singingroom, R.drawable.escaperoom,
            R.drawable.dangguroom, R.drawable.bowlingroom, R.drawable.boardgameroom};
    String[] gameCategoryNameId = {"PC방", "노래방", "방탈출", "당구장", "볼링장", "보드게임방"};

    int[] cultureCategoryImgId ={R.drawable.movie, R.drawable.exhibition, R.drawable.reading};
    String[] cultureCategoryNameId ={"영화", "전시회", "독서"};

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

    }
}