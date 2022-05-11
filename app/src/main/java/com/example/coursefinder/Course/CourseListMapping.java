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
import com.example.coursefinder.playingRegister.Result1;
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
    String[] courseOrder={
            "A", "B", "C",
            "D", "E", "F",
            "G", "H", "I"
    };

    private SelectCourseList schCourseResults = new SelectCourseList();

    private PlaceSearchResult placeSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

//    String first[] = new String[3];
//    String second[] = new String[3];
//    String third[] = new String[3];
//    String fourth[] = new String[3];
    String courseList[];

    String results;
    String resultsArr[];
    String imgResults;
    String [] schNames = {"중식", "카페", "영화관"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist_mapping);

        Gson gson = new Gson();
        int size = schNames.length * schNames.length;
        resultsArr=new String[size];
        try{

            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new CourseListMapping.GetSchResult(schNames[i]).execute().get();
                resultsArr[i] = results;
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());

            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }
        courseList=makeCourseList(orderschResults);


//        CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, buttonStr, orderschResults.get(1));
        CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, courseList, courseOrder, schNames, resultsArr);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(CourseListMapping.this, "You Clicked at " +courseOrder[+ position], Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CourseListMapping.this, CourseListSelect.class);
//                intent.putExtra("course_data", adapter.getItemId(position).getCi_idx());
            }

        });
    }
    String[] makeCourseList(Map<Integer, ArrayList<PlaceList>> orderschResults){
        String[] courseList={""};

        if(orderschResults.size()==3){
            int idx=0;
            courseList = new String[27];
            for(int i=0; i<3; i++){

                String first = orderschResults.get(1).get(i).getTitle();
                for(int j=0; j<3; j++){
                    String second = orderschResults.get(2).get(j).getTitle();
                    for(int z=0; z<3; z++){
                        String third = orderschResults.get(3).get(z).getTitle();
                        courseList[idx] = first + "->" + second + "->" + third;
                        Log.d(TAG, idx+": "+courseList[idx]);
                        idx++;
                    }
                }
            }
        }
        else if(orderschResults.size()==4){
            int idx=0;
            courseList = new String[81];
            for(int i=0; i<3; i++){
                String first = orderschResults.get(1).get(i).getTitle();
                for(int j=0; j<3; j++){
                    String second = orderschResults.get(2).get(j).getTitle();
                    for(int z=0; z<3; z++){
                        String third = orderschResults.get(3).get(z).getTitle();
                        for(int k=0; k<3; k++){
                            courseList[idx] = first + "->" + second + "->" + third;
                            Log.d(TAG, idx+": "+courseList[idx]);
                            idx++;
                        }
                    }
                }
            }
        }
        return courseList;
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
            Call<String> call = apiInterface.getSearchResult("l0EZZiIpcB0ffOfRU99J", "hu5ESzjw59", "local.json", keyword,3);
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
