package com.example.coursefinder.Course;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CourseListResult extends Activity {

    GridView grid;
    String[] buttonStr={
            "강남",
            "홍대",
            "건대"
    };

    private SelectCourseList schCourseResults = new SelectCourseList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_result);

        // 검색어를 받아온 후에 함수 매개변수로 이용
        searchCourse("한식", "카페", "영화관");

        /*
        CourseListResultGrid adapter = new CourseListResultGrid(CourseListResult.this, buttonStr);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CourseListResult.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

            }
        });

         */

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

                    // 어텝터 호출
                    Log.d("TAG", "ADAPTER");
                    CourseListResultGrid adapter = new CourseListResultGrid(CourseListResult.this, buttonStr);
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(CourseListResult.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();
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
