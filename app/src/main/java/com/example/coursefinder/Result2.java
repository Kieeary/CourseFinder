//package com.example.coursefinder;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.example.coursefinder.searchVo.ImageSearchResult;
//import com.example.coursefinder.searchVo.PlaceList;
//import com.example.coursefinder.searchVo.PlaceSearchResult;
//import com.example.coursefinder.searchapi.ApiClient;
//import com.example.coursefinder.searchapi.ApiInterface;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Response;
//
//public class Result2 extends AppCompatActivity {
//    String results;
//    String imgResults;
//
//    String TAG = "TAG";
//    private static int cnt = 0;
//    private static boolean isupdated = false;
//    ListView listView;
//
//    private PlaceSearchResult placeSearchResult;
//    private ImageSearchResult imageSearchResult;
//    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
//    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();
//    private Boolean isBack = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result2);
//
//        ListView listView2;
//        Button next2;
//        Button back2;
//        String [] schNames = {"카페"};
//
//        int[] image2 = {R.drawable.map};
//        String[] placeName2 = {"가게이름"};
//
//
//        listView2 = (ListView)findViewById(R.id.listView2);
//        next2 = (Button)findViewById(R.id.next2);
//        back2 = (Button)findViewById(R.id.back2);
//        Intent intent = getIntent();
//        placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("Selectedplace");
//        isBack = intent.getBooleanExtra("isBack", false);
//
//
//        Gson gson = new Gson();
//
//        try{
//            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
//            results = new GetSchResult(schNames[0]).execute().get();
//            placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
//            orderschResults.put(2, placeSearchResult.getPlaceLists());
//
//        }catch(Exception e){
//            Log.d(TAG, "장소 검색 실패" + e.getMessage());
//        }
//
//
//        try {
//            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
//            for (int i = 0; i < 4; i++) {
//                imgResults = new GetImgResult(orderschResults.get(2).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
//                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
//                if (imageSearchResult.getImgResult() == null)
//                    orderschResults.get(2).get(i).setImgLink("empty");
//                else
//                    orderschResults.get(2).get(i).setImgLink(imageSearchResult.getImgResult().get(0).getLink());
//            }
//        }catch(Exception e){
//            Log.d(TAG, "이미지 검색 실패" + e.getMessage());
//        }
//
//
//        ResultAdapter resultAdapter2 = new ResultAdapter(Result2.this, image2, orderschResults.get(2));
//        listView2.setAdapter(resultAdapter2);
//
//          listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if(!isBack){
//                    if(placeLists.indexOf(resultAdapter2.getItem(i)) == -1){
//                        placeLists.add(resultAdapter2.getItem(i));
//                    }else {
//                        placeLists.remove(resultAdapter2.getItem(i));
//                        Log.d("TAG", "제거됨");
//                    }
//                }else{
//                    if(findDup(resultAdapter2, i)){ // 중복된 장소가 잇는 경우 (장소제거)
//                        Log.d("TAG", "중복된 장소가 존재합니다");
//                        placeLists.remove(i);
//                    }else{  // 장소 추가인 경우
//                        Log.d("TAG", "추가 합니다");
//                        placeLists.add(resultAdapter2.getItem(i));
//                    }
//                }
//
//            }
//        });
//
//        next2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Result2.this, Result3.class);
//                if(placeLists.size() > 0){
//                    intent.putExtra("Selectedplace", placeLists);
//                    startActivity(intent);
//                }else{
//                    Log.d("TAG", "장소 등록하세요");
//                }
//            }
//        });
//
//        back2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Result2.this, Result.class);
//                intent.putExtra("Selectedplace", placeLists);
//                intent.putExtra("isBack", true);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//    public Boolean findDup(ResultAdapter resultAdapter, int idx){
//        for(int i = placeLists.size()-1; i>=0; i--){
//            if(placeLists.get(i).getTitle().equals(resultAdapter.getItem(idx).getTitle())){
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//
//
//    class GetSchResult extends AsyncTask<Void, Void, String> {
//        String keyword;
//        // 키워드 값을 세팅
//        public GetSchResult(String keyword) {
//            this.keyword = keyword;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
//            Call<String> call = apiInterface.getSearchResult("l0EZZiIpcB0ffOfRU99J", "hu5ESzjw59", "local.json", keyword,5);
//            try{
//                Response<String> response = call.execute();
//                return response.body().toString();
//            }catch(Exception e){
//                Log.d(TAG, "error occured");
//            }
//            return null;
//        }
//    }
//
//
//
//    // 이미지 검색을 위한 클래스
//    class GetImgResult extends AsyncTask<Void, Void, String>{
//        String keyword;
//
//        public GetImgResult(String keyword) {
//            this.keyword = keyword;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            ApiInterface apiInterface = ApiClient.getInstance().create(ApiInterface.class);
//            Call<String> call = apiInterface.getImageResult("l0EZZiIpcB0ffOfRU99J", "hu5ESzjw59", "image", keyword,1, "small");
//            try{
//                Response<String> response = call.execute();
//                if(response.isSuccessful()){
//                    return response.body().toString();
//                }else{
//                    Log.d(TAG, "FAIL TO SEARCH IMG" + response.code());
//                    return null;
//                }
//            }catch(Exception e){
//                Log.d(TAG, "error occured");
//            }
//            return null;
//        }
//    }
//}