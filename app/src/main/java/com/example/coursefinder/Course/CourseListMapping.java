package com.example.coursefinder.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.Result;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.SelectFromView;
import com.example.coursefinder.searchVo.ImageSearchResult;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CourseListMapping extends Activity {
    String TAG = "TAG";
    GridView grid;
    String[] buttonStr={
            "A->B->C",
            "D->E->F",
            "G->H->I"
    };

    private SelectCourseList schCourseResults = new SelectCourseList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_mapping);
        searchCourse("한식", "카페", "영화관");

    }

    // 매개변수로 검색 키워드를 받아온다
    public void searchCourse(String sch1, String sch2, String sch3){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.searchCourse(sch1, sch2, sch3);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && !(response.body().equals("failed"))) {
                    String result = response.body();
                    Gson gson = new Gson();
                    schCourseResults = gson.fromJson(result, SelectCourseList.class);


                    CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, buttonStr, schCourseResults.getCourseLists());
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CourseListMapping.this, CourseDetail.class);
                            intent.putExtra("courseId", adapter.getItem(position).getCi_idx());
                            startActivity(intent);
                        }
                    });
                }else{
                    Log.d("TAG","조회에 실패함" + response.body() );
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // 등록 실패
                Log.d("TAG", t.getMessage());
            }
        });
    }


}
