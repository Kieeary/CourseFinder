package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursefinder.Course.CourseDetail;
import com.example.coursefinder.Course.CourseRegitDetail;
import com.example.coursefinder.MemberVo.MemberLogInResults;
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

// 받아온 입력값들을 바탕으로 검색을 돌려서 , ListView형태로 보여주는 클래스. 
// 검색 결과들을 클릭해서 객체에 담아 준 후에 CourseRegitDetail로 이동해야 함
// 미완성임,
public class Result extends AppCompatActivity {

    String TAG = "TAG";
    private static int cnt = 0;
    private static boolean isupdated = false;
    ListView listView;
//    ImageButton imageButton;

    ListView listView2;

    ListView listView3;

    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private ArrayList<String> imgLinks = new ArrayList<String>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    String results;
    String imgResults;
    String [] schNames = {"한식", "카페", "영화관"};

    int[] image = {R.drawable.map};
    String[] placeName = {"가게이름"};

    int[] image2 = {R.drawable.map};
    String[] placeName2 = {"가게이름"};

    int[] image3 = {R.drawable.map};
    String[] placeName3 = {"가게이름"};

    private SharedPreferences sharedPreferences;
    private MemberLogInResults loginMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (ListView)findViewById(R.id.listView);
//        imageButton = (ImageButton)findViewById(R.id.detailBtn);

        listView2 = (ListView)findViewById(R.id.listView2);

        listView3 = (ListView)findViewById(R.id.listView3);

        // onClicklistner -> onitemClickListner 변경
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Result.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Result.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Result.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
            }
        });

        Gson gson = new Gson();

        try{

            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new Result.GetSchResult(schNames[i]).execute().get();
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());
            }


            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
            for(int j=1; j<=3; j++) {
                for (int i = 0; i < 4; i++) {
                    imgResults = new Result.GetImgResult(orderschResults.get(j).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
                    imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                    if (imageSearchResult.getImgResult() == null)
                       // imgLinks.add("empty");
                        orderschResults.get(j).get(i).setImgLink("empty");
                    else orderschResults.get(j).get(i).setImgLink(imageSearchResult.getImgResult().get(0).getLink());
                        //imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
                }
            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }

        // courseinfo 와 courseplace에 저장할 장소들(사용자가 클릭한 장소들을 담아서 전달함)
        placeLists.add(orderschResults.get(1).get(0));
        placeLists.add(orderschResults.get(2).get(0));
        placeLists.add(orderschResults.get(3).get(0));


        ResultAdapter resultAdapter = new ResultAdapter(Result.this, image, orderschResults.get(1));
        listView.setAdapter(resultAdapter);

        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, orderschResults.get(2));
        listView2.setAdapter(resultAdapter2);

        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, orderschResults.get(3));
        listView3.setAdapter(resultAdapter3);

        Intent intent = getIntent();

        TextView textView = findViewById(R.id.textView1);
        textView.setText(intent.getStringExtra("category"));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, CourseRegitDetail.class);
                Log.d("TAG", placeLists.size()+" ");
                intent.putExtra("placeList", placeLists);
                startActivity(intent);
            }
        });
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