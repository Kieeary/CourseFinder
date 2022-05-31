package com.example.coursefinder.smallcategory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.coursefinder.Course.CourseListMapping;
import com.example.coursefinder.Course.CourseListResult;
import com.example.coursefinder.MainCategory;
import com.example.coursefinder.R;
import com.example.coursefinder.PlayingRegister.GridAdapter;

import java.util.HashMap;
import java.util.Iterator;

public class SmallCategory extends Activity {

    GridView gridView;
    GridView gridView2;
    GridView gridView3;
    GridView gridView4;

    View prevView;
    View prevView2;
    View prevView3;
    View prevView4;

    Button nextButton;
    Button prevButton;
    HashMap<Integer, Integer> selectInfo; // 선택된 카테고리 정보를 담기 위한 hashmap

    int[] categoryImgId = {R.drawable.hansik, R.drawable.ilsik, R.drawable.yangsik, R.drawable.jungsik,
            R.drawable.meat, R.drawable.bunsik, R.drawable.yasik, R.drawable.seafood, R.drawable.salad};
    public static String[] categoryNameId = {"한식", "일식", "양식", "중식", "고기", "분식", "야식", "해산물", "샐러드"};

    int[] cafeCategoryImgId = {R.drawable.cafe, R.drawable.bakery, R.drawable.dessert, R.drawable.beer, R.drawable.wine, R.drawable.cocktail};
    public static String[] cafeCategoryNameId = {"카페", "베이커리", "디저트", "맥주", "와인", "칵테일"};

    int[] gameCategoryImgId = {R.drawable.pcroom, R.drawable.singingroom, R.drawable.escaperoom,
            R.drawable.dangguroom, R.drawable.bowlingroom, R.drawable.boardgameroom};
    public static String[] gameCategoryNameId = {"PC방", "노래방", "방탈출", "당구장", "볼링장", "보드게임방"};

    int[] cultureCategoryImgId = {R.drawable.movie, R.drawable.exhibition, R.drawable.reading};
    public static String[] cultureCategoryNameId = {"영화", "전시회", "독서", "공연"};

    Boolean colorset = false;
    Boolean colorset2 = false;
    Boolean colorset3 = false;
    Boolean colorset4 = false;
    int prev=-1;
    int prev2=-1;
    int prev3=-1;
    int prev4=-1;
    private int isDB = -1;  // 사용자 코스로 이동할지, 리스트 맵핑으로 이동할지 결정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tem_small_category);

        // CourseSchSelect에서 받아온 값을 통해서, 사용자 코스 리스트를 보여줄지, 리스트 맵핑 코스리스트를 보여줄지 결정
        // 1이면 사용자, -1이면 리스트 맵핑
        Intent getSort = getIntent();
        isDB = getSort.getIntExtra("isDb", -1);
        Log.d("TAG", "ISDB = " + isDB+"");



        selectInfo = new HashMap<Integer, Integer>(); // 선택된 정보를 담을 맵 객체

        gridView = findViewById(R.id.gridViewId);
        gridView2 = findViewById(R.id.gridViewId2);
        gridView3 = findViewById(R.id.gridViewId3);
        gridView4 = findViewById(R.id.gridViewId4);

        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        GridAdapter gridAdapter = new GridAdapter(SmallCategory.this, categoryNameId, categoryImgId);
        gridView.setAdapter(gridAdapter);

        GridAdapter cafeGridAdapter = new GridAdapter(SmallCategory.this, cafeCategoryNameId, cafeCategoryImgId);
        gridView2.setAdapter(cafeGridAdapter);

        GridAdapter gameGridAdapter = new GridAdapter(SmallCategory.this, gameCategoryNameId, gameCategoryImgId);
        gridView3.setAdapter(gameGridAdapter);

        GridAdapter cultureGridAdapter = new GridAdapter(SmallCategory.this, cultureCategoryNameId, cultureCategoryImgId);
        gridView4.setAdapter(cultureGridAdapter);

        // 각 그리드 뷰에 클릭 리스너 설정
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                adapterView.setBackgroundColor(Color.YELLOW);
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
                //Log.i("확인", "첫번째 그리드 뷰");
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
                //Log.i("확인", "두번째 그리드 뷰");
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
                //Log.i("확인", "세번째 그리드 뷰");
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
                //Log.i("확인", "네번째 그리드 뷰");
            }
        });

        nextButton.setOnClickListener(view -> {
            Iterator<Integer> iterator = selectInfo.keySet().iterator();
            int currIndex = iterator.hasNext() ? iterator.next() : -1; //ex 0,1,3 이 저장이라고 가정.

            if(isDB == 1){
                Intent intent = new Intent(SmallCategory.this, CourseListResult.class);
                intent.putExtra("currIndex", currIndex);
                intent.putExtra("selectInfo", selectInfo);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SmallCategory.this, CourseListMapping.class);
                intent.putExtra("currIndex", currIndex);
                intent.putExtra("selectInfo", selectInfo);
                startActivity(intent);
            }
/*
            Intent intent = new Intent(SmallCategory.this, CourseListMapping.class);
            intent.putExtra("currIndex", currIndex);
            intent.putExtra("selectInfo", selectInfo);
            startActivity(intent);
        });
*/

//            GridView grid;
//    String[] buttonStr={
//            "일식",
//            "한식",
//            "한식",
//            "일식",
//            "한식",
//            "일식",
//            "한식",
//            "일식",
//            "한식",
//            "일식",
//            "한식",
//            "일식"
//    };
//    int[] count = {
//            2,
//            3,
//            4,
//            1,
//            2,
//            3,
//            2,
//            3,
//            1,
//            2,
//            4,
//            2
//    }; // db에서 갖고와야할 정보
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.small_category);
//
//        SmallCategoryGrid adapter = new SmallCategoryGrid(SmallCategory.this, buttonStr, count);
//        grid=(GridView)findViewById(R.id.grid);
//        grid.setAdapter(adapter);
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(SmallCategory.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        Button nextButton = (Button) findViewById(R.id.next_button);
//        Button cancelButton = (Button) findViewById(R.id.cancel_button);
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CourseListMapping.class);
//                startActivity(intent);
//            }
//
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainCategory.class);
//                startActivity(intent);
//            }
//
//        });
//    }
//
    });
        prevButton.setOnClickListener(view -> {
            Intent intent = new Intent(SmallCategory.this, MainCategory.class);
            startActivity(intent);
            finish();
        });
}}


