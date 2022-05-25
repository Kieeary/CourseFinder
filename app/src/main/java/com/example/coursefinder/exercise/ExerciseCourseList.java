package com.example.coursefinder.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.ExerciseInfo;
import com.example.coursefinder.courseVo.SelectExerciseFromView;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExerciseCourseList extends AppCompatActivity implements OnMapReadyCallback {


    ListView listView;
    ImageButton imageButton;
    Button btn;

    // 나중에 db 내용을 받아오는 부분
    int[] courseImgId = {R.drawable.map, R.drawable.map};
    String[] courseNameId = {"김철수", "박철수"};
    String[] coursePlaceId = {"A B C", "C B A"};
    String[] courseScore = {"5점", "5점"};

    // 현재위치 설정을 위한 변수 및 객체들
    private FusedLocationSource mLocationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    // 권환 확인
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private NaverMap navermap;
    private double lat, lon;

    private boolean isTracked = false;

    private ArrayList<Marker> markers = new ArrayList<Marker>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_course_list);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        /*
        // 현재 위치 추적을 하기 위해서 설정함
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        lat = location.getLatitude();
        lon = location.getLongitude();


         */

        listView = (ListView) findViewById(R.id.listView);
        imageButton = (ImageButton) findViewById(R.id.detailBtn);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        navermap.setLocationSource(mLocationSource);
        navermap.setLocationTrackingMode(LocationTrackingMode.Follow);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        lat = location.getLatitude();
//        lon = location.getLongitude();
        lat = 37.298512;
        lon = 127.105930;
        getExList(lat, lon);
    }




    public void getExList(double lat, double lon){
        Log.d("TAG", "LAT = " + lat +" LON = " + lon);
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);

        Call<String> call = apiInterface.getExList(lat, lon);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && !(response.body().equals("failed"))){
                    String result = response.body();
                    Gson gson = new Gson();
                    SelectExerciseFromView selectExerciseFromView = gson.fromJson(result, SelectExerciseFromView.class);
                    ArrayList<ExerciseInfo> exerciseInfos = selectExerciseFromView.getExerciseInfos();

                    setMarkers(exerciseInfos);

                    ExListViewAdapter listViewAdapter = new ExListViewAdapter(ExerciseCourseList.this, exerciseInfos);
                    listView.setAdapter(listViewAdapter);

                }else{
                    Log.d("TAG", "DB연결은 성공했으나" + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Log.d("TAG", "조회 실패");
            }
        });
    }

    public void setMarkers(ArrayList<ExerciseInfo> exerciseInfos){
        Marker marker;
        for(int i=0; i<exerciseInfos.size(); i++){
            marker = new Marker();
            marker.setPosition(new LatLng(exerciseInfos.get(i).getWcp_at() , exerciseInfos.get(i).getWcp_lt()));
            markers.add(marker);
            markers.get(i).setCaptionText(exerciseInfos.get(i).getWi_name());
            markers.get(i).setMap(navermap);
        }
    }

}