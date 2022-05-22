package com.example.coursefinder.ranking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseReviewDetail;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.SelectFromExReview;
import com.example.coursefinder.exercise.ExerciseDetailCourse;
import com.example.coursefinder.exercise.ListViewAdapter;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Ranking extends Activity {
    String TAG = "TAG";
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

    private SelectCourseList selectCourseList;
    String results;
    String resultsArr[];
    String [] schNames = {"중식", "카페", "영화관"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Intent intent = getIntent();
/*        Gson gson = new Gson();
        int size = 10;      // 10개 불러오기
        resultsArr = new String[size];
        try{
            for (int i =0; i<3; i++){
                //results = new Ranking.GetSchResult(schNames[i]).execute().get();
                resultsArr = new String[size];
                selectCourseList = gson.fromJson(results, SelectCourseList.class);
            }
        } catch(Exception e){
            Log.d(TAG, "가져오지 못함" + e.getMessage());
        }*/
       // RankingGrid adapter = new RankingGrid(Ranking.this);
        grid = (GridView) findViewById(R.id.grid);
      //  grid.setAdapter(adapter);
        getCourse("1");
/*

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
*/
    }

    public void getCourse(String wiid){      // wiid

        Log.d("TAG", "WIID = " + wiid);
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);

        Call<String> call = apiInterface.getbestcourse(wiid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && !(response.body().equals("failed"))){
                    String result = response.body();
                    Gson gson = new Gson();
                    SelectCourseList selectCourseList = gson.fromJson(result, SelectCourseList.class);
                    ArrayList<CourseListVo> courseListVos = selectCourseList.getCourseLists();

                    RankingGrid rankingGrid = new RankingGrid(Ranking.this, courseListVos);
                    grid.setAdapter(rankingGrid);

                }else{
                    Log.d("TAG", "DB연결은 성공했으나" + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Log.d("TAG", "조회 실패");
            }
        });
    }

}