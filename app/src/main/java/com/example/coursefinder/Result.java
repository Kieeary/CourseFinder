package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.exercise.ListViewAdapter;
import com.example.coursefinder.searchVo.ImageSearchResult;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class Result extends AppCompatActivity {

    String TAG = "TAG";

    ListView listView;
    ImageButton imageButton;

    ListView listView2;

    ListView listView3;

    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private List<String> imgLinks = new ArrayList<String>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    String results;
    String imgResults;
    String [] schNames = {"한식", "전시회", "영화관"};

    int[] image = {R.drawable.map};
    String[] placeName = {"가게이름"};

    int[] image2 = {R.drawable.map};
    String[] placeName2 = {"가게이름"};

    int[] image3 = {R.drawable.map};
    String[] placeName3 = {"가게이름"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (ListView)findViewById(R.id.listView);
        imageButton = (ImageButton)findViewById(R.id.detailBtn);

        listView2 = (ListView)findViewById(R.id.listView2);

        listView3 = (ListView)findViewById(R.id.listView3);


        try{
            Gson gson = new Gson();
            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new Result.GetSchResult(schNames[i]).execute().get();
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());
            }


            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
            for(int i=0; i<4; i++){
                imgResults = new Result.GetImgResult(orderschResults.get(3).get(i).getTitle()).execute().get();
                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                if(imageSearchResult.getImgResult().get(0).getLink() == null) imgLinks.add("empty");
                else    imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }

        ResultAdapter resultAdapter = new ResultAdapter(Result.this, image, orderschResults.get(1));
        listView.setAdapter(resultAdapter);

        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, orderschResults.get(2));
        listView2.setAdapter(resultAdapter2);

        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, orderschResults.get(3));
        listView3.setAdapter(resultAdapter3);

        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView1);
        textView.setText(intent.getStringExtra("category"));

    }

    // 장소 검색을 위한 클래스
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



    // 이미지 검색을 위한 클래스
    class GetImgResult extends AsyncTask<Void, Void, String>{
        String keyword;

        public GetImgResult(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.getImageResult("l0EZZiIpcB0ffOfRU99J", "hu5ESzjw59", "image", keyword,1, "small");
            try{
                Response<String> response = call.execute();
                if(response.isSuccessful()){
                    return response.body().toString();
                }else{
                    Log.d(TAG, "FAIL TO SEARCH IMG" + response.code());
                    return null;
                }
            }catch(Exception e){
                Log.d(TAG, "error occured");
            }
            return null;
        }
    }
}