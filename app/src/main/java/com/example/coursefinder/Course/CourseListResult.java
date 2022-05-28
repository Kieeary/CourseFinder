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
import java.util.Iterator;
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

    String title[] = new String[]{"","","",""};
    int currIndex;
    HashMap<Integer, Integer> selectInfo;


    String results;
    String imgResults;
    String [] schNames = {"한식", "카페", "영화관"};
    private SelectCourseList schCourseResults = new SelectCourseList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_mapping);

        Intent intent = getIntent();
        currIndex = intent.getIntExtra("currIndex", -1);
        selectInfo = (HashMap<Integer, Integer>) intent.getSerializableExtra("selectInfo");

        int index = 0;

        switch (currIndex) {
            case 0:
                title[index] = SmallCategory.categoryNameId[selectInfo.get(currIndex)];
                index++;  //index값은 1 로 변경
                break;
            case 1:
                title[index] = SmallCategory.cafeCategoryNameId[selectInfo.get(currIndex)];
                index++;
                break;
            case 2:
                title[index] = SmallCategory.gameCategoryNameId[selectInfo.get(currIndex)];
                index++;
                break;
            case 3:
                title[index] = SmallCategory.cultureCategoryNameId[selectInfo.get(currIndex)];
                index++;
                break;
        }

        Iterator<Integer> iterator = selectInfo.keySet().iterator();
        int last = -1;
        int temp = iterator.next(); // 맨처음엔 0이 저장.

        for (int i = 1; i < 4; i++) //1->2->3
        {
            if (currIndex == temp && iterator.hasNext()) {
                currIndex = iterator.next(); // 0 -> 1 -> 3로 저장 (0,1,3 이 저장이라고 가정)
                temp = currIndex;

                switch (currIndex) {
                    case 1:
                        title[index] = SmallCategory.cafeCategoryNameId[selectInfo.get(currIndex)];
                        break;
                    case 2:
                        title[index] = SmallCategory.gameCategoryNameId[selectInfo.get(currIndex)];
                        break;
                    case 3:
                        title[index] = SmallCategory.cultureCategoryNameId[selectInfo.get(currIndex)];
                        break;
                }
                index++;
            }
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

     /*   try{

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

      */

        }

        Log.d("TAG", title[0]); Log.d("TAG", title[1]); Log.d("TAG", title[2]);
        Log.d("TAG", title[3] + "is null???");

        // 검색어를 받아온 후에 함수 매개변수로 이용
        searchCourse(title[0], title[1], title[2], title[3]);

        Button nextButton = (Button) findViewById(R.id.next_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

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
    public void searchCourse(String sch1, String sch2, String sch3, String sch4){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.searchCourse(sch1, sch2, sch3, sch4);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && !(response.body().equals("failed"))) {
                    String result = response.body();
                    Gson gson = new Gson();
                    schCourseResults = gson.fromJson(result, SelectCourseList.class);

                    // 어텝터 호출
                    Log.d("TAG", "ADAPTER");
                    CourseListResultGrid2 adapter = new CourseListResultGrid2(CourseListResult.this, buttonStr, schCourseResults.getCourseLists());
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

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
