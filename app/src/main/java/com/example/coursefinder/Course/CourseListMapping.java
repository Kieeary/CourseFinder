package com.example.coursefinder.Course;

import android.app.Activity;
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

    private PlaceSearchResult placeSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    String results;
    String imgResults;
    String [] schNames = {"한식", "카페", "영화관"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_mapping);


        Gson gson = new Gson();

        try{

            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new CourseListMapping.GetSchResult(schNames[i]).execute().get();
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());
            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }

        CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, buttonStr, orderschResults.get(1));
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CourseListMapping.this, "You Clicked at " +buttonStr[+ position], Toast.LENGTH_SHORT).show();

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


}
