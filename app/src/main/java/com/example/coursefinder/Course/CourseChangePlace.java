package com.example.coursefinder.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursefinder.Course.CourseDetail;
import com.example.coursefinder.Course.CourseRegitDetail;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.playingRegister.Result1;
import com.example.coursefinder.playingRegister.ResultAdapter;
import com.example.coursefinder.exercise.ExerciseCourseList;
import com.example.coursefinder.exercise.ListViewAdapter;
import com.example.coursefinder.searchVo.ImageSearchResult;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseChangePlace extends AppCompatActivity {

    String TAG = "TAG";
    private static int cnt = 0;
    private static boolean isupdated = false;
    ListView listView;

    Button next1;
//    ImageButton imageButton;

//    ListView listView2;
//
//    ListView listView3;

    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private ArrayList<String> imgLinks = new ArrayList<String>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();
    private PlaceList place;

    String results;
    String imgResults;
    private boolean isBack = false;
    //    String [] schNames = {"한식", "카페", "영화관"};
    String [] schNames = {""};

    int[] image = {R.drawable.map};
    String[] placeName = {"가게이름"};

//    int[] image2 = {R.drawable.map};
//    String[] placeName2 = {"가게이름"};
//
//    int[] image3 = {R.drawable.map};
//    String[] placeName3 = {"가게이름"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_change);

        listView = (ListView)findViewById(R.id.listView);
        next1 = (Button)findViewById(R.id.next1);

//        imageButton = (ImageButton)findViewById(R.id.detailBtn);

//        listView2 = (ListView)findViewById(R.id.listView2);
//
//        listView3 = (ListView)findViewById(R.id.listView3);


        Intent intent = getIntent();

        String category = intent.getStringExtra("category");
        schNames[0] = category;

        isBack = intent.getBooleanExtra("isBack", false);
        if(isBack)  placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("Selectedplace");

        place = intent.getParcelableExtra("place");

        Gson gson = new Gson();
        try {
            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            results = new GetSchResult(schNames[0]).execute().get();
            placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
            orderschResults.put(1, placeSearchResult.getPlaceLists());
        } catch (Exception e) {
            Log.d(TAG, "장소 검색 실패" + e.getMessage());
        }


        try {
            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
            for (int i = 0; i < 4; i++) {
                imgResults = new GetImgResult(orderschResults.get(1).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                if (imageSearchResult.getImgResult() == null)
                    orderschResults.get(1).get(i).setImgLink("empty");
                else
                    orderschResults.get(1).get(i).setImgLink(imageSearchResult.getImgResult().get(0).getLink());
            }
        }catch(Exception e){
            Log.d(TAG, "이미지 검색 실패" + e.getMessage());
        }

        CourseChangePlaceGrid changeAdapter = new CourseChangePlaceGrid(CourseChangePlace.this, image, orderschResults.get(1), orderschResults);
        listView.setAdapter(changeAdapter);

//        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, orderschResults.get(2));
//        listView2.setAdapter(resultAdapter2);
//
//        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, orderschResults.get(3));
//        listView3.setAdapter(resultAdapter3);


        // onClicklistner -> onitemClickListner 변경
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isBack){
                    if(placeLists.indexOf(changeAdapter.getItem(i)) == -1){
                        placeLists.add(changeAdapter.getItem(i));
                    }else {
                        placeLists.remove(changeAdapter.getItem(i));
                        Log.d("TAG", "제거됨");
                    }
                }else{
                    if(findDup(changeAdapter, i)){ // 중복된 장소가 잇는 경우 (장소제거)
                        Log.d("TAG", "중복된 장소가 존재합니다");
                        placeLists.remove(i);
                    }else{  // 장소 추가인 경우
                        Log.d("TAG", "추가 합니다");
                        placeLists.add(changeAdapter.getItem(i));
                    }
                }

                Toast.makeText(CourseChangePlace.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
            }
        });

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseChangePlace.this, Result1.class);
                if(placeLists.size() > 0){
                    intent.putExtra("Selectedplace", placeLists);
                    startActivity(intent);
                }else{
                    Log.d("TAG", "장소 등록하세요");
                }
                startActivity(intent);
            }
        });

//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(placeLists.indexOf(resultAdapter2.getItem(i)) == -1){
//                    placeLists.add(resultAdapter2.getItem(i));
//                }else {
//                    placeLists.remove(resultAdapter2.getItem(i));
//                    Log.d("TAG", "제거됨");
//                }
//                Toast.makeText(Result.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("TAG", placeLists.indexOf(resultAdapter3.getItem(i))+" ");
//                if(placeLists.indexOf(resultAdapter3.getItem(i)) == -1){
//                    placeLists.add(resultAdapter3.getItem(i));
//                }else {
//                    placeLists.remove(resultAdapter3.getItem(i));
//                    Log.d("TAG", "제거됨");
//                }
//
//            }
//        });

        TextView textView = findViewById(R.id.textView1);
        textView.setText(intent.getStringExtra("category"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseChangePlace.this, CourseRegitDetail.class);
                if(placeLists.size() > 0){
                    intent.putExtra("Selectedplace", placeLists);
                    startActivity(intent);
                }else{
                    Log.d("TAG", "장소 등록하세요");
                }
            }
        });
    }

    public Boolean findDup(CourseChangePlaceGrid changeAdapter, int idx){
        for(int i = placeLists.size()-1; i>=0; i--){
            if(placeLists.get(i).getTitle().equals(changeAdapter.getItem(idx).getTitle())){
                return true;
            }
        }
        return false;
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