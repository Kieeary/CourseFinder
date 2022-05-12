package com.example.coursefinder.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.SelectCourseList;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.example.coursefinder.smallcategory.SmallCategory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CourseListResult extends Activity {
    String TAG = "TAG";
    GridView grid;
    String[] buttonStr={
            "강남",
            "홍대",
            "건대"
    };

    private PlaceSearchResult placeSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    String results;
    String imgResults;
    String [] schNames = {"한식", "카페", "영화관"};
    private SelectCourseList schCourseResults = new SelectCourseList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_result);

        // 검색어를 받아온 후에 함수 매개변수로 이용
//        searchCourse("한식", "카페", "영화관");

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
        Gson gson = new Gson();

        try{

            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new CourseListResult.GetSchResult(schNames[i]).execute().get();
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());
            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }


    CourseListResultGrid adapter = new CourseListResultGrid(CourseListResult.this, buttonStr, orderschResults);
    grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
            Toast.makeText(CourseListResult.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

        }
    });

    Button nextButton = (Button) findViewById(R.id.next_button);
    Button cancelButton = (Button) findViewById(R.id.cancel_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CourseListResult.class);
            startActivity(intent);
        }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SmallCategory.class);
            startActivity(intent);
        }

        });
    }
    class GetSchResult extends AsyncTask<Void, Void, String> {
        String keyword;
        // 키워드 값을 세팅
        public GetSchResult(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.getSearchResult("l0EZZiIpcB0ffOfRU99J", "hu5ESzjw59", "local.json", keyword,5);
            try{
                Response<String> response = call.execute();
                return response.body().toString();
            }catch(Exception e){
                Log.d(TAG, "error occured");
            }
            return null;
        }
    }

    // 매개변수로 검색 키워드를 받아온다
    public void searchCourse(String sch1, String sch2, String sch3){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.searchCourse(sch1, sch2, sch3, "");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && !(response.body().equals("failed"))) {
                    String result = response.body();
                    Gson gson = new Gson();
                    schCourseResults = gson.fromJson(result, SelectCourseList.class);

                    // 어텝터 호출
                    Log.d("TAG", "ADAPTER");
                    CourseListResultGrid adapter = new CourseListResultGrid(CourseListResult.this, buttonStr, orderschResults);
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
