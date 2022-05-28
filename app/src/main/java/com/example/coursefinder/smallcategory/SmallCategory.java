package com.example.coursefinder.smallcategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.coursefinder.Course.CourseListMapping;
import com.example.coursefinder.Course.CourseListResult;
import com.example.coursefinder.R;
import com.example.coursefinder.PlayingRegister.GridAdapter;

import java.util.HashMap;
import java.util.Iterator;

public class SmallCategory extends Activity {

    GridView gridView;
    GridView gridView2;
    GridView gridView3;
    GridView gridView4;

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

    int[] cultureCategoryImgId = {R.drawable.movie, R.drawable.exhibition, R.drawable.reading};
    public static String[] cultureCategoryNameId = {"영화", "전시회", "독서", "공연"};
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
                selectInfo.put(0, i);
                //Log.i("확인", "첫번째 그리드 뷰");
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectInfo.put(1, i);
                //Log.i("확인", "두번째 그리드 뷰");
            }
        });
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectInfo.put(2, i);
                //Log.i("확인", "세번째 그리드 뷰");
            }
        });
        gridView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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

 */
        });

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
    }
}



//package com.example.coursefinder.smallcategory;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.Toast;
//
//import com.example.coursefinder.Course.CourseListMapping;
//import com.example.coursefinder.MainCategory;
//import com.example.coursefinder.R;
//import com.example.coursefinder.mycourse.MyCourse;
//
//public class SmallCategory extends Activity {
//    GridView grid;
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
//    private int position;
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
//}