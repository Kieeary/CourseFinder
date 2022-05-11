package com.example.coursefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.coursefinder.searchVo.ImageList;
import com.example.coursefinder.searchVo.ImageSearchResult;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.PlaceSearchResult;
import com.example.coursefinder.searchVo.ResultPath;
import com.example.coursefinder.searchapi.ApiClient;
import com.example.coursefinder.searchapi.ApiClient2;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final String TAG= "FC";

    // json 형태의 검색 및 이미지 기록을 임시로 담아줄 객체
    private PlaceSearchResult placeSearchResult;
    private ImageSearchResult imageSearchResult;
    private ArrayList<PlaceList> placeLists = new ArrayList<PlaceList>();
    private List<String> imgLinks = new ArrayList<String>();

    /**
     * 메인 스레드와 백드라운드 스레드를 따로 돌리는 형식으로 db, api 검색을 해줘야함
     * Apiclient는 retrofit객체를 생성하는 클래스며, apiinterface에 정의된 함수들을 통해서 작업을 함
     * baseurl이 다르기 때문에 apiclient 클래스가 여러개 존재하게 될 수 있음
     */
    String imgResults;

    // 장소 목록들을 받아옴 1번(한식) 2번(전시회) 3번(영화관) 이라면 , key 1번에는 values는 한식 검색 결과들을 담고있는 ArrayList<PlaceList>
    private Map<Integer, ArrayList<PlaceList>> orderschResults = new HashMap<Integer, ArrayList<PlaceList>>();
    // private Map<Integer, ArrayList<ImageList>> orderImageResults = new HashMap<Integer, ArrayList<ImageList>>();

    // 마커들을 담음
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    // json형태의 길찾기 경로들을 담음
    private ResultPath resultPath = new ResultPath();
    String [] schNames = {"한식", "전시회", "영화관"};
    String results;

    // 네이버맵 지도 객체
    private NaverMap navermap;

    // 현재위치 설정을 위한 변수 및 객체들
    private FusedLocationSource mLocationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    // 권환 확인
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 지도를 띄움
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // 현재 위치 추적을 하기 위해서 설정함
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);


        final int[] num = {1};
        try{
            Gson gson = new Gson();
            // 장소 검색, async를 통해서 받아올 때는 try catch문 안에서 사용해야 함
            for(int i=0; i<3; i++){
                results = new GetSchResult(schNames[i]).execute().get();
                placeSearchResult = gson.fromJson(results, PlaceSearchResult.class);
                orderschResults.put(i+1, placeSearchResult.getPlaceLists());
            }


            // 이미지 검색, 보안이 필요할 듯 하여 주석처리 초당 10건 제한이 있음
            for(int i=0; i<4; i++){
                imgResults = new GetImgResult(orderschResults.get(3).get(i).getTitle()).execute().get();
                imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                if(imageSearchResult.getImgResult().get(0).getLink() == null) imgLinks.add("empty");
                else    imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
            }

            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<4; i++){
                        try{
                            imgResults = new GetImgResult(orderschResults.get(2).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
                            imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                            imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
                        }catch(Exception e){
                            Log.d(TAG, "2nd sch failed" + e.getMessage());
                            imgLinks.add("emtpty");
                        }
                    }
                }
            },1500);



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<4; i++){
                        try{
                            imgResults = new GetImgResult(orderschResults.get(3).get(i).getTitle().replaceAll("<b>", "").replaceAll("</b>", "")).execute().get();
                            imageSearchResult = gson.fromJson(imgResults, ImageSearchResult.class);
                            imgLinks.add(imageSearchResult.getImgResult().get(0).getLink());
                        }catch(Exception e){
                            Log.d(TAG, "3rd sch failed" + e.getMessage());
                            imgLinks.add("empty");
                        }
                    }
                }
            },2500);
            */
        }catch(Exception e){
            Log.d(TAG, "가져오지못함" + e.getMessage());
        }


        /* 이미지들을 확인하는 과정인데, 5개만 받아올 거면 수정이 필요함
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<imgLinks.size(); i++){
                    Log.d(TAG, i +"번째 = " + imgLinks.get(i));
                }


            }
        }, 3500);
        */
    }


    // 지도를 띄워주는 과정
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        // 현재위치 추적
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        // 첫번째 장소에서 두번째 장소까지의 경로, 지도에 polyline으로 보여주고있음
        getRoute();
        // 장소 3개의 마커
        setMarkers();
        // 장소 3개를 이어주는 폴리라인
        setPolyLines();
        // 현재 위치에서 지도 띄우기
        navermap.setLocationSource(mLocationSource);
        // CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new Tm128(orderschResults.get(1).get(0).getMapx(), orderschResults.get(1).get(0).getMapy()).toLatLng());
        // naverMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navermap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

    // 코스 장소들의 위치를 찍는 마커
    public void setMarkers(){
        Marker marker;
        for(int i=0; i<3; i++){
            marker = new Marker();
            Tm128 tm = new Tm128(orderschResults.get(i+1).get(0).getMapx(), orderschResults.get(i+1).get(0).getMapy());
            marker.setPosition(tm.toLatLng());
            markers.add(marker);
            markers.get(i).setCaptionText(orderschResults.get(i+1).get(0).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));
            markers.get(i).setCaptionTextSize(20);
            markers.get(i).setMap(navermap);
        }
    }

    // 코스의 장소들을 이어주는 폴리라인
    public void setPolyLines(){
        PathOverlay path = new PathOverlay();
        List<LatLng> latLngList = new ArrayList<LatLng>();
        for(int i=0; i<markers.size(); i++){
            latLngList.add(markers.get(i).getPosition());
        }
        path.setCoords(latLngList);
        path.setMap(navermap);
    }

    // 길찾기 이용 시 폴리라인
    public void setPolyLines(List<LatLng> latLngList){
        PathOverlay path = new PathOverlay();
        path.setCoords(latLngList);
        path.setWidth(35);
        path.setColor(Color.YELLOW);
        path.setMap(navermap);
    }

    // 장소 검색을 위한 클래스
    class GetSchResult extends AsyncTask<Void, Void, String>{
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

    // 경로 찾기 함수
    public void getRoute(){
        String goal = toLatLng(orderschResults.get(1).get(0).getMapx(), orderschResults.get(1).get(0).getMapy());
        String start = toLatLng(orderschResults.get(2).get(0).getMapx(), orderschResults.get(2).get(0).getMapy());
        ApiInterface apiInterface = ApiClient2.getInstance().create(ApiInterface.class);
        Call<ResultPath> call = apiInterface.getRoute("53o7d43lub", "kFF1Zm70tVyOZl2o2hpP1yyol1rxp3Hk51xhbIbr",
                start, goal, "");
        call.enqueue(new Callback<ResultPath>() {
            @Override
            public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultPath = response.body();
                    PathOverlay path = new PathOverlay();
                    List<LatLng> latLngList = new ArrayList<LatLng>();
                    for(int i=0; i<resultPath.getTrackOption().getTraoptimal().get(0).getPath().size(); i++){
                        double a,b;
                        a = resultPath.getTrackOption().getTraoptimal().get(0).getPath().get(i).get(0);
                        b = resultPath.getTrackOption().getTraoptimal().get(0).getPath().get(i).get(1);
                        latLngList.add(new LatLng(b,a));
                    }
                    // 폴리라인 찍어줔
                    setPolyLines(latLngList);
                }
                else {
                    Log.d(TAG, "실패 : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResultPath> call, Throwable t) {
                Log.d(TAG, "에러 : " + t.getMessage());
            }
        });
    }

    // 검색에서 나온 위도 경도 값의 형식을 다시 바꿔줘야함
    public String toLatLng(int x, int y){
        Tm128 tm = new Tm128(x, y);
        LatLng latLng = tm.toLatLng();
        return latLng.longitude + "," + latLng.latitude;
    }
}