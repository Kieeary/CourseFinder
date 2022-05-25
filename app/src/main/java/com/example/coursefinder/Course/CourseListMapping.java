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
import com.example.coursefinder.smallcategory.SmallCategory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    String title[] = new String[]{"","","",""};
    int currIndex;
    HashMap<Integer, Integer> selectInfo;

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

        Intent intent = getIntent();
        currIndex = intent.getIntExtra("currIndex", -1);
        selectInfo = (HashMap<Integer, Integer>) intent.getSerializableExtra("selectInfo");

        int index = 0;

        switch(currIndex) {
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

        for(int i = 1; i < 4; i++) //1->2->3
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
            //                if(index == 1)
//                {
//                    title[index] = SmallCategory.cafeCategoryNameId[selectInfo.get(currIndex)];
//                    index++; //index = 2 -> 3 저장
//                }
//
//                if(index == 2)
//                {
//                    title[index] = SmallCategory.gameCategoryNameId[selectInfo.get(currIndex)];
//                    index++; //index = 2 -> 3 저장
//                }
//
//                if(index == 3)
//                {
//                    title[index] = SmallCategory.cultureCategoryNameId[selectInfo.get(currIndex)];
//                    index++; //index = 2 -> 3 저장
//                }

            if (iterator.hasNext() == false && i == 3) {
                last = temp; //temp에는 3 이 있음 -> last 도 3 이 저장.
            }
        }
        int k = 0;
        int num = 0;

        while(title[k] != "")
        {
            num++;
            k++;
        }

        k = 0;

        String[] categoryTitle = new String[num];
        int j = 0;

        Log.i("확인121", title[0]);
        Log.i("확인123", title[1]);
        Log.i("확인123", title[2]);

        while(title[k] != "")
        {
            categoryTitle[j] = title[k];
            j++;
            k++;
        }

        Log.i("확인", categoryTitle[0]);
        Log.i("확인", categoryTitle[1]);
        Log.i("확인", categoryTitle[2]);


        Gson gson = new Gson();
        int size = categoryTitle.length * categoryTitle.length;
        resultsArr=new String[size];
        try{

            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new CourseListMapping.GetSchResult(categoryTitle[i]).execute().get();
                resultsArr[i] = results;
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());

            }

        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }
        courseList=makeCourseList(orderschResults);


//        CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, buttonStr, orderschResults.get(1));
        CourseListMappingGrid adapter = new CourseListMappingGrid(CourseListMapping.this, courseList, courseOrder, categoryTitle, resultsArr);
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
