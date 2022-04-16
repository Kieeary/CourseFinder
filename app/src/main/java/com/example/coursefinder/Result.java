package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private final String TAG= "FC";
    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();
    private List<String> imgLinks = new ArrayList<String>();
    String results;
    String imgResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        TextView textView = findViewById(R.id.textView6);
        TextView textView2 = findViewById(R.id.textView7);
        ImageView imageView = findViewById(R.id.img1);
        // 입력값 받아온 , 아마 배열
        String schKeyword = intent.getStringExtra("category");


        try {
            Gson gson = new Gson();
            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            results = new GetSchResult(schKeyword).execute().get();
            placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
            orderschResults.put(1, placeSearchResult.getPlaceLists());


            for(int i=0; i<4; i++){
                imgResults = new GetImgResult(orderschResults.get(1).get(i).getTitle()).execute().get();
                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                if(imageSearchResult.getImgResult().get(0).getLink() == null) imgLinks.add("empty");
                else    imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
            }
        }catch(Exception e){
            Log.d("ERROR!" ,"failed " + e.getMessage());
        }

        textView.setText(orderschResults.get(1).get(0).getTitle() +", " +
                orderschResults.get(1).get(0).getMapx() + " , " +
                orderschResults.get(1).get(0).getMapy()
        );



        String imgUrl = imgLinks.get(2);
        if(imgUrl.equals("empty"))  imgUrl = "기본 이미지 url";
        Glide.with(this).load(imgUrl).into(imageView);

        // textView2.setText(imgLinks.get(0) +", " + imgLinks.get(1) +", " + imgLinks.get(2));


        Log.d(TAG, "ordershcReulst의 크기는 : " + orderschResults.size()+"입니다");

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
            // 리스트로 보여주기
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