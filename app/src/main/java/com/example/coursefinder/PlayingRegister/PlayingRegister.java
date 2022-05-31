package com.example.coursefinder.PlayingRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.coursefinder.R;

import java.util.HashMap;
import java.util.Iterator;


public class PlayingRegister extends AppCompatActivity {

    GridView gridView;
    GridView gridView2;
    GridView gridView3;
    GridView gridView4;

    View prevView;
    View prevView2;
    View prevView3;
    View prevView4;

    Button nextButton;
    HashMap<Integer, Integer> selectInfo; // 선택된 카테고리 정보를 담기 위한 hashmap

    int[] categoryImgId = {R.drawable.hansik, R.drawable.ilsik, R.drawable.yangsik, R.drawable.jungsik,
            R.drawable.meat, R.drawable.bunsik, R.drawable.yasik, R.drawable.seafood, R.drawable.salad};
    public static String[] categoryNameId = {"한식", "일식", "양식", "중식", "고기", "분식", "야식", "해산물", "샐러드"};

    int[] cafeCategoryImgId = {R.drawable.cafe, R.drawable.bakery, R.drawable.dessert, R.drawable.beer, R.drawable.wine, R.drawable.cocktail};
    public static String[] cafeCategoryNameId = {"카페", "베이커리", "디저트", "맥주", "와인", "칵테일"};

    int[] gameCategoryImgId = {R.drawable.pcroom, R.drawable.singingroom, R.drawable.escaperoom,
            R.drawable.dangguroom, R.drawable.bowlingroom, R.drawable.boardgameroom};
    public static String[] gameCategoryNameId = {"PC방", "노래방", "방탈출", "당구장", "볼링장", "보드게임방"};

    int[] cultureCategoryImgId ={R.drawable.movie, R.drawable.exhibition, R.drawable.reading};
    public static String[] cultureCategoryNameId ={"영화", "전시회", "독서", "공연"};

    Boolean colorset = false;
    Boolean colorset2 = false;
    Boolean colorset3 = false;
    Boolean colorset4 = false;
    int prev=-1;
    int prev2=-1;
    int prev3=-1;
    int prev4=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_register);

        selectInfo = new HashMap<Integer, Integer>(); // 선택된 정보를 담을 맵 객체

        gridView = findViewById(R.id.gridViewId);
        gridView2 = findViewById(R.id.gridViewId2);
        gridView3 = findViewById(R.id.gridViewId3);
        gridView4 = findViewById(R.id.gridViewId4);

        nextButton = findViewById(R.id.nextButton);

        GridAdapter gridAdapter = new GridAdapter(PlayingRegister.this, categoryNameId, categoryImgId);
        gridView.setAdapter(gridAdapter);

        GridAdapter cafeGridAdapter = new GridAdapter(PlayingRegister.this,cafeCategoryNameId, cafeCategoryImgId);
        gridView2.setAdapter(cafeGridAdapter);

        GridAdapter gameGridAdapter = new GridAdapter(PlayingRegister.this,gameCategoryNameId, gameCategoryImgId);
        gridView3.setAdapter(gameGridAdapter);

        GridAdapter cultureGridAdapter = new GridAdapter(PlayingRegister.this, cultureCategoryNameId, cultureCategoryImgId);
        gridView4.setAdapter(cultureGridAdapter);

        // 각 그리드 뷰에 클릭 리스너 설정
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(colorset) {
                    if(prev==i) {
                        view.setBackgroundColor(Color.parseColor("#00000000"));
                        colorset = false;
                        prev=i;
                        prevView=view;
                    }
                    else{
                        prevView.setBackgroundColor(Color.parseColor("#00000000"));
                        view.setBackgroundColor(Color.parseColor("#6650BCDF"));
                        colorset = true;
                        prev=i;
                        prevView=view;
                    }
                }
                else {
                    prev=i;
                    view.setBackgroundColor(Color.parseColor("#6650BCDF"));
//                    adapterView.setBackgroundColor(Color.parseColor("#F12312"));
                    colorset=true;
                    prevView=view;
                }
                selectInfo.put(0, i);
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(colorset2) {
                    if(prev2==i) {
                        view.setBackgroundColor(Color.parseColor("#00000000"));
                        colorset2 = false;
                        prev2=i;
                        prevView2=view;
                    }
                    else{
                        prevView2.setBackgroundColor(Color.parseColor("#00000000"));
                        view.setBackgroundColor(Color.parseColor("#6650BCDF"));
                        colorset2 = true;
                        prev2=i;
                        prevView2=view;
                    }
                }
                else {
                    prev2=i;
                    view.setBackgroundColor(Color.parseColor("#6650BCDF"));
//                    adapterView.setBackgroundColor(Color.parseColor("#F12312"));
                    colorset2=true;
                    prevView2=view;
                }
                selectInfo.put(1, i);
            }
        });
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(colorset3) {
                    if(prev3==i) {
                        view.setBackgroundColor(Color.parseColor("#00000000"));
                        colorset3 = false;
                        prev3=i;
                        prevView3=view;
                    }
                    else{
                        prevView3.setBackgroundColor(Color.parseColor("#00000000"));
                        view.setBackgroundColor(Color.parseColor("#6650BCDF"));
                        colorset3 = true;
                        prev3=i;
                        prevView3=view;
                    }
                }
                else {
                    prev3=i;
                    view.setBackgroundColor(Color.parseColor("#6650BCDF"));
//                    adapterView.setBackgroundColor(Color.parseColor("#F12312"));
                    colorset3=true;
                    prevView3=view;
                }
                selectInfo.put(2, i);
            }
        });
        gridView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(colorset4) {
                    if(prev4==i) {
                        view.setBackgroundColor(Color.parseColor("#00000000"));
                        colorset4 = false;
                        prev4=i;
                        prevView4=view;
                    }
                    else{
                        prevView4.setBackgroundColor(Color.parseColor("#00000000"));
                        view.setBackgroundColor(Color.parseColor("#6650BCDF"));
                        colorset4 = true;
                        prev4=i;
                        prevView4=view;
                    }
                }
                else {
                    prev4=i;
                    view.setBackgroundColor(Color.parseColor("#6650BCDF"));
//                    adapterView.setBackgroundColor(Color.parseColor("#F12312"));
                    colorset4=true;
                    prevView4=view;
                }
                selectInfo.put(3, i);
            }
        });

        nextButton.setOnClickListener(view -> {
            Iterator<Integer> iterator = selectInfo.keySet().iterator();
            int currIndex = iterator.hasNext() ? iterator.next() : -1;

            Intent intent = new Intent(PlayingRegister.this, FirstCateogryResult.class);
            intent.putExtra("currIndex", currIndex);
            intent.putExtra("selectInfo", selectInfo);
            startActivity(intent);
        });


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //Toast.makeText(PlayingRegister.this, "You Clicked on " + categoryNameId[i], Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(PlayingRegister.this, Result.class);
//
//                intent.putExtra("category", categoryNameId[i].toString());
//                startActivity(intent);
//            }
//        });
    }
}