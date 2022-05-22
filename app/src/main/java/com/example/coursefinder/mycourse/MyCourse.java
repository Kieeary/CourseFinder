package com.example.coursefinder.mycourse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.MainCategory;
import com.example.coursefinder.MemberVo.MemberInfo;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.SelectExCourseList;
import com.example.coursefinder.courseVo.SelectFromView;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourse extends Activity {
    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
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

    private SharedPreferences sharedPreferences;
    private MemberLogInResults loginMember;
    private Button playfav;
    private Button exfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_course);

        // 로그인 정보를 받아온다.
        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        loginMember = gson.fromJson(member, MemberLogInResults.class);
        String miid = loginMember.getMemberInfo().get(0).getId();

        playfav = (Button)findViewById(R.id.playfav);
        exfav = (Button)findViewById(R.id.exfav);

        playfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyCourse(miid);
            }
        });

        exfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyExCourse(miid);
            }
        });



        
        Button nextButton = (Button)findViewById(R.id.next_btn);
        Button cancelButton = (Button)findViewById(R.id.cancel_btn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourseReview.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainCategory.class);
                startActivity(intent);
            }
        });

    }

    public void getMyCourse(String miid){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.getFavCourse(miid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && !(response.body().equals("failed"))){
                    String result = response.body();
                    Gson gson = new Gson();
                    SelectCourseList courseList = gson.fromJson(result, SelectCourseList.class);
                    MyCourseGrid adapter = new MyCourseGrid(MyCourse.this, web, imageId, courseList);
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                } else{
                    Log.d("TAG", "FAILED");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "ERROR" + t.getMessage());
            }
        });
    }

    // 저장한 산책 코스 가져오기
    public void getMyExCourse(String miid){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.getFavExourse(miid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && !(response.body().equals("failed"))){
                    String result = response.body();
                    Gson gson = new Gson();
                    SelectExCourseList exfavList = gson.fromJson(result, SelectExCourseList.class);
                    MyCourseGrid2 adapter = new MyCourseGrid2(MyCourse.this, web, imageId, exfavList);
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                } else{
                    Log.d("TAG", "FAILED");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "ERROR" + t.getMessage());
            }
        });
    }

}