package com.example.coursefinder.PlayingRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursefinder.Course.CourseRegitDetail;
import com.example.coursefinder.R;
import com.example.coursefinder.searchVo.ImageSearchResult;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FirstCateogryResult extends AppCompatActivity {

    String TAG = "TAG";
    private static int cnt = 0;
    private static boolean isupdated = false;
    ListView listView;
//    int isFirst;

    Button next1;

    int currIndex; //PlayingRegister의 selectinfo에 들어있는 currIndex를 받기 위한 변수
    HashMap<Integer, Integer> selectInfo; //PlayingRegister에서 보낸 값을 받기 위한 변수
//    ImageButton imageButton;
//    ListView listView2;
//    ListView listView3;

    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private ArrayList<PlaceList> regPlaceLists = new ArrayList<PlaceList>();
    private ArrayList<String> imgLinks = new ArrayList<String>();
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();

    String results;
    String imgResults;
//    private boolean isBack = false;

    //    String [] schNames = {"한식", "카페", "영화관"};
    String [] schNames = {"한식"};

    int[] image = {R.drawable.map};
    String[] placeName = {"가게이름"};

//    boolean test = false;
//    int[] image2 = {R.drawable.map};
//    String[] placeName2 = {"가게이름"};
//
//    int[] image3 = {R.drawable.map};
//    String[] placeName3 = {"가게이름"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (ListView)findViewById(R.id.listView);
        next1 = (Button)findViewById(R.id.next1);

//        isFirst = 0;

//        imageButton = (ImageButton)findViewById(R.id.detailBtn);
//        listView2 = (ListView)findViewById(R.id.listView2);
//
//        listView3 = (ListView)findViewById(R.id.listView3);
        Intent intent = getIntent();

        Intent intent2 = getIntent();

        //PlayingRegister에서 클릭한 클릭한 아이템의 값을 가져오는 부분
        currIndex = intent.getIntExtra("currIndex", -1);
        selectInfo = (HashMap<Integer, Integer>) intent.getSerializableExtra("selectInfo");
        TextView textView2 = findViewById(R.id.textView1);

//        Bundle tempBundle = new Bundle();
//
//        if((test = tempBundle.getBoolean("Test")) == true) {
//            currIndex = tempBundle.getInt("currIndex");
//            placeLists = tempBundle.getParcelableArrayList("placeLists");
//            Log.d("placeLists", placeLists.size() + "");
//        }


//        if(tempBundle = getIntent().getExtras() != null) {
//            Log.d("확인", "isFirst 는 1");
//            placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("placeLists");
//        }
//        else{
//            Log.d("확인", "isFirst 는 1이 아님");
//        }

        String title = "";

        Log.d("currIndex", currIndex + "");

        switch(currIndex) {
            case 0:
                title = PlayingRegister.categoryNameId[selectInfo.get(currIndex)];
                break;
            case 1:
                title = PlayingRegister.cafeCategoryNameId[selectInfo.get(currIndex)];
                break;
            case 2:
                title = PlayingRegister.gameCategoryNameId[selectInfo.get(currIndex)];
                break;
            case 3:
                title = PlayingRegister.cultureCategoryNameId[selectInfo.get(currIndex)];
                break;
        }

        Log.d("tile", title);
        textView2.setText(title);

        //isBack = intent.getBooleanExtra("isBack", false);
        //if(isBack)
        //placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("Selectedplace");

        Gson gson = new Gson();

        try{
            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            results = new GetSchResult(title).execute().get();
            placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
            orderschResults.put(1, placeSearchResult.getPlaceLists());

        }catch(Exception e){
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

        ResultAdapter resultAdapter = new ResultAdapter(FirstCateogryResult.this, image, orderschResults.get(1));
        listView.setAdapter(resultAdapter);

        next1.setOnClickListener(view -> {

            Iterator<Integer> iterator = selectInfo.keySet().iterator();
            int last = -1;

            while(true) {
                int temp = iterator.next();
                if(currIndex == temp && iterator.hasNext()) {
                    currIndex = iterator.next();
                    break;
                }
                if(iterator.hasNext() == false) {
                    last = temp;
                    break;
                }
            }

//            if(currIndex == last) {
//                Intent intentTemp = new Intent(Result1.this, CourseRegitDetail.class);
//                intentTemp.putExtra("Selectedplace", placeLists);
//                //intentTemp.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intentTemp);
//            }
//
            if(currIndex == last) {
                Intent intentTemp = new Intent(FirstCateogryResult.this, CourseRegitDetail.class);
                intentTemp.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentTemp);
            }
            else {

                switch(currIndex) {
                    case 0:

                        break;
                    case 1:
                        Intent intentTemp = new Intent(FirstCateogryResult.this, SecondCateogryResult.class);
                        intentTemp.putExtra("currIndex", currIndex);
                        intentTemp.putExtra("selectInfo", selectInfo);
                        intentTemp.putExtra("Selectedplace", placeLists);
                        startActivity(intentTemp);
                        break;
                    case 2:
                        Intent intentTemp2 = new Intent(FirstCateogryResult.this, ThirdCategoryResult.class);
                        intentTemp2.putExtra("currIndex", currIndex);
                        intentTemp2.putExtra("selectInfo", selectInfo);
                        intentTemp2.putExtra("Selectedplace", placeLists);
                        startActivity(intentTemp2);
                        break;
                    case 3:
                        Intent intentTemp3 = new Intent(FirstCateogryResult.this, FourthCategoryResult.class);
                        intentTemp3.putExtra("currIndex", currIndex);
                        intentTemp3.putExtra("selectInfo", selectInfo);
                        intentTemp3.putExtra("Selectedplace", placeLists);
                        startActivity(intentTemp3);
                        break;
                }
//                Intent intentTemp = new Intent(Result1.this, Result2.class);
//                intentTemp.putExtra("currIndex", currIndex);
//                intentTemp.putExtra("selectInfo", selectInfo);
//                intentTemp.putExtra("Selectedplace", placeLists);
//                startActivity(intentTemp);
            }
        });

        Log.d("PlaceList", placeLists.size()+"");

//        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, orderschResults.get(2));
//        listView2.setAdapter(resultAdapter2);
//
//        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, orderschResults.get(3));
//        listView3.setAdapter(resultAdapter3);


        // onClicklistner -> onitemClickListner 변경
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                placeLists.add(resultAdapter.getItem(i));
                Log.d("TAG", "추가 합니다");
                Log.d("Info", placeLists.get(0).getTitle());
//                Intent intent2 =  new Intent(Result1.this, Result1.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("placeLists", placeLists);
//                bundle.putBoolean("Test", true);
//                bundle.putInt("currIndex", currIndex + 1);
//                intent2.putExtras(bundle);
//                startActivity(intent2);
//                Result1.this.finish();
//                intent2.putExtra("isFirst", 1);

//                intent2.putExtra("isFirst",1);

//                if(findDup(resultAdapter,i)){
//                    Log.d("TAG", "중복된 장소가 존재합니다");
//                        placeLists.remove(i);
//                }
//                else {  // 장소 추가인 경우
//                        Log.d("TAG", "추가 합니다");
//                        placeLists.add(resultAdapter.getItem(i));
//                }



//                if(!isBack){
//                    if(placeLists.indexOf(resultAdapter.getItem(i)) == -1){
//                        placeLists.add(resultAdapter.getItem(i));
//                    }else {
//                        placeLists.remove(resultAdapter.getItem(i));
//                        Log.d("TAG", "제거됨");
//                    }
//                }else{
//                    if(findDup(resultAdapter, i)){ // 중복된 장소가 잇는 경우 (장소제거)
//                        Log.d("TAG", "중복된 장소가 존재합니다");
//                        placeLists.remove(i);
//                    }else{  // 장소 추가인 경우
//                        Log.d("TAG", "추가 합니다");
//                        placeLists.add(resultAdapter.getItem(i));
//                    }
//                }

                Toast.makeText(FirstCateogryResult.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
            }
        });

//        next1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Result1.this, Result2.class);
//                if(placeLists.size() > 0){
//                    intent.putExtra("Selectedplace", placeLists);
//                    intent.putExtra("currIndex", currIndex);
//                    intent.putExtra("selectInfo", selectInfo);
//                    startActivity(intent);
//                }else{
//                    Log.d("TAG", "장소 등록하세요");
//                }
//                startActivity(intent);
//            }
//        });

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
                Intent intent = new Intent(FirstCateogryResult.this, CourseRegitDetail.class);
                if(placeLists.size() > 0){
                    intent.putExtra("Selectedplace", placeLists);
                    startActivity(intent);
                }else{
                    Log.d("TAG", "장소 등록하세요");
                }
            }
        });
    }


    public Boolean findDup(ResultAdapter resultAdapter, int idx){
        for(int i = placeLists.size()-1; i>=0; i--){
            if(placeLists.get(i).getTitle().equals(resultAdapter.getItem(idx).getTitle())){
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


//package com.example.coursefinder.playingRegister;
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
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.coursefinder.Course.CourseRegitDetail;
//import com.example.coursefinder.MainCategory;
//import com.example.coursefinder.R;
//import com.example.coursefinder.searchVo.ImageSearchResult;
//import com.example.coursefinder.searchVo.PlaceList;
//import com.example.coursefinder.searchVo.PlaceSearchResult;
//import com.example.coursefinder.searchapi.ApiClient;
//import com.example.coursefinder.searchapi.ApiInterface;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Response;
//
//public class Result1 extends AppCompatActivity {
//
//    String TAG = "TAG";
//    private static int cnt = 0;
//    private static boolean isupdated = false;
//    ListView listView;
//
//    Button next1;
//
//    int currIndex;
//    HashMap<Integer, Integer> selectInfo;
////    ImageButton imageButton;
////    ListView listView2;
////    ListView listView3;
//
//    private PlaceSearchResult placeSearchResult;
//    private ImageSearchResult imageSearchResult;
//    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
//    private ArrayList<String> imgLinks = new ArrayList<String>();
//    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();
//
//    String results;
//    String imgResults;
//    private boolean isBack = false;
//
////    String [] schNames = {"한식", "카페", "영화관"};
//    String [] schNames = {"한식"};
//
//    int[] image = {R.drawable.map};
//    String[] placeName = {"가게이름"};
//
////    int[] image2 = {R.drawable.map};
////    String[] placeName2 = {"가게이름"};
////
////    int[] image3 = {R.drawable.map};
////    String[] placeName3 = {"가게이름"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//
//        listView = (ListView)findViewById(R.id.listView);
//        next1 = (Button)findViewById(R.id.next1);
//
////        imageButton = (ImageButton)findViewById(R.id.detailBtn);
////        listView2 = (ListView)findViewById(R.id.listView2);
////
////        listView3 = (ListView)findViewById(R.id.listView3);
//        Intent intent = getIntent();
//
//        //PlayingRegister에서 클릭한 클릭한 아이템의 값을 가져오는 부분
//        currIndex = intent.getIntExtra("currIndex", -1);
//        selectInfo = (HashMap<Integer, Integer>) intent.getSerializableExtra("selectInfo");
//        TextView textView2 = findViewById(R.id.textView1);
//
//        String title = "";
//
//        switch(currIndex) {
//            case 0:
//                title = PlayingRegister.categoryNameId[selectInfo.get(currIndex)];
//                break;
//            case 1:
//                title = PlayingRegister.cafeCategoryNameId[selectInfo.get(currIndex)];
//                break;
//            case 2:
//                title = PlayingRegister.gameCategoryNameId[selectInfo.get(currIndex)];
//                break;
//            case 3:
//                title = PlayingRegister.cultureCategoryNameId[selectInfo.get(currIndex)];
//                break;
//        }
//        textView2.setText(title);
//
//        isBack = intent.getBooleanExtra("isBack", false);
//        if(isBack)  placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("Selectedplace");
//
//
//        Gson gson = new Gson();
//
//        try{
//            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
//            results = new GetSchResult(title).execute().get();
//            placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
//            orderschResults.put(1, placeSearchResult.getPlaceLists());
//
//        }catch(Exception e){
//            Log.d(TAG, "장소 검색 실패" + e.getMessage());
//        }
//
//
//        try {
//            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
//            for (int i = 0; i < 4; i++) {
//                imgResults = new GetImgResult(orderschResults.get(1).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
//                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
//                if (imageSearchResult.getImgResult() == null)
//                    orderschResults.get(1).get(i).setImgLink("empty");
//                else
//                    orderschResults.get(1).get(i).setImgLink(imageSearchResult.getImgResult().get(0).getLink());
//                }
//        }catch(Exception e){
//            Log.d(TAG, "이미지 검색 실패" + e.getMessage());
//        }
//
//        ResultAdapter resultAdapter = new ResultAdapter(Result1.this, image, orderschResults.get(1));
//        listView.setAdapter(resultAdapter);
//
//        next1.setOnClickListener(view -> {
//
//            Iterator<Integer> iterator = selectInfo.keySet().iterator();
//            int last = -1;
//            while(true) {
//                int temp = iterator.next();
//                if(currIndex == temp && iterator.hasNext()) {
//                    currIndex = iterator.next();
//                    break;
//                }
//                if(iterator.hasNext() == false) {
//                    last = temp;
//                    break;
//                }
//            }
//            if(currIndex == last) {
//                Intent intentTemp = new Intent(Result1.this, MainCategory.class);
//                intentTemp.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intentTemp);
//            }
//            else {
//                Intent intentTemp = new Intent(Result1.this, Result1.class);
//                intentTemp.putExtra("currIndex", currIndex);
//                intentTemp.putExtra("selectInfo", selectInfo);
//                startActivity(intentTemp);
//            }
//
//        });
//
//
////        ResultAdapter resultAdapter2 = new ResultAdapter(Result.this, image2, orderschResults.get(2));
////        listView2.setAdapter(resultAdapter2);
////
////        ResultAdapter resultAdapter3 = new ResultAdapter(Result.this, image3, orderschResults.get(3));
////        listView3.setAdapter(resultAdapter3);
//
//
//        // onClicklistner -> onitemClickListner 변경
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(!isBack){
//                    if(placeLists.indexOf(resultAdapter.getItem(i)) == -1){
//                        placeLists.add(resultAdapter.getItem(i));
//                    }else {
//                        placeLists.remove(resultAdapter.getItem(i));
//                        Log.d("TAG", "제거됨");
//                    }
//                }else{
//                    if(findDup(resultAdapter, i)){ // 중복된 장소가 잇는 경우 (장소제거)
//                        Log.d("TAG", "중복된 장소가 존재합니다");
//                        placeLists.remove(i);
//                    }else{  // 장소 추가인 경우
//                        Log.d("TAG", "추가 합니다");
//                        placeLists.add(resultAdapter.getItem(i));
//                    }
//                }
//
//                Toast.makeText(Result1.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
////        next1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(Result1.this, Result2.class);
////                if(placeLists.size() > 0){
////                    intent.putExtra("Selectedplace", placeLists);
////                    startActivity(intent);
////                }else{
////                    Log.d("TAG", "장소 등록하세요");
////                }
////                startActivity(intent);
////            }
////        });
//
////        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                if(placeLists.indexOf(resultAdapter2.getItem(i)) == -1){
////                    placeLists.add(resultAdapter2.getItem(i));
////                }else {
////                    placeLists.remove(resultAdapter2.getItem(i));
////                    Log.d("TAG", "제거됨");
////                }
////                Toast.makeText(Result.this, i+1 + "번째 코스 저장!", Toast.LENGTH_SHORT).show();
////            }
////        });
////
////        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Log.d("TAG", placeLists.indexOf(resultAdapter3.getItem(i))+" ");
////                if(placeLists.indexOf(resultAdapter3.getItem(i)) == -1){
////                    placeLists.add(resultAdapter3.getItem(i));
////                }else {
////                    placeLists.remove(resultAdapter3.getItem(i));
////                    Log.d("TAG", "제거됨");
////                }
////
////            }
////        });
//
//        TextView textView = findViewById(R.id.textView1);
//        textView.setText(intent.getStringExtra("category"));
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Result1.this, CourseRegitDetail.class);
//                if(placeLists.size() > 0){
//                    intent.putExtra("Selectedplace", placeLists);
//                    startActivity(intent);
//                }else{
//                    Log.d("TAG", "장소 등록하세요");
//                }
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
//    // 장소 검색을 위한 클래스
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