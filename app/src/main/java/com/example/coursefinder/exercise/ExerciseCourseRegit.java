package com.example.coursefinder.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coursefinder.R;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseCourseRegit extends AppCompatActivity implements OnMapReadyCallback {
    // 네이버맵 지도 객체
    private NaverMap navermap;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    // 지울 마커의 위치를 구할 변수
    private static int position= -1;


    // 현재위치 설정을 위한 변수 및 객체들
    private FusedLocationSource mLocationSource;
    private static final int PERMISSION_REQUEST_CODE = 100;
    // 권환 확인
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private Button btn2;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_regit);

        Intent intent = getIntent();
        String miid = intent.getStringExtra("miid");
        Log.d("TAG", miid +" user id");


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // 현재 위치 추적을 하기 위해서 설정함
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        // 장소 모두 초기화
        btn1 = findViewById(R.id.mapbtn1);


        // db저장
        btn2 = findViewById(R.id.mapbtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String result = new MakeWalkCourseInfo("산책코스1", "가볍게 걷는 운동", "3", miid).execute().get();
                    if(result != null){
                        if(markers.indexOf(null) != -1){
                            markers.remove(markers.indexOf(null));
                        }
                        for(int i=0; i<markers.size(); i++){
                            Log.d("TAG", markers.get(i).getPosition()+"");
                            makeWalkCourse(i+1, markers.get(i), miid);
                        }
                    }
                }catch(Exception e){
                    Log.d("TAG", e.getMessage().toString());
                }
            }
        });
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(37.298512, 127.105930));
        naverMap.moveCamera(cameraUpdate);

        navermap.setOnMapDoubleTapListener(new NaverMap.OnMapDoubleTapListener() {
            @Override
            public boolean onMapDoubleTap(@NonNull PointF pointF, @NonNull LatLng latLng) {
                Marker marker = new Marker();
                marker.setPosition(latLng);
                if(position != -1){
                    Log.d("TAG", "중간에 삭제 한 경우");
                    markers.set(position, marker);
                    markers.get(position).setMap(navermap);
                }else {
                    // 중간에 삭제 안한경우
                    Log.d("TAG", "신규 등록인 경우");
                    markers.add(marker);
                    markers.get(markers.size()-1).setMap(navermap);
                }
                setMakerEvt(markers);
                return true;
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Marker mark : markers){
                    mark.setMap(null);
                }
                markers.clear();
                position = -1;
                Log.d("TAG", "SIZE OF MARKERS : " + markers.size());
            }
        });
    }

    public void setMakerEvt(ArrayList<Marker> markers){
        //  Log.d("TAG", "SIZE OF MARKERS :" + markers.size());
        for(Marker mark : markers){
            mark.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    position = markers.indexOf(mark);
                    // Log.d("TAG", "pos = " + position);
                    mark.setMap(null);
                    markers.set(position, null);
                    return false;
                }
            });
        }
    }

    // CourseInfo table에 저장
    class MakeWalkCourseInfo extends AsyncTask<Void, Void, String> {
        String wcname;
        String wcinfo;
        String wctime;
        String miid;

        public MakeWalkCourseInfo(String wcname, String wcinfo, String wctime, String miid) {
            this.wcname = wcname;
            this.wcinfo = wcinfo;
            this.wctime = wctime;
            this.miid = miid;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.insertWalkCourseInfo(wcname, wcinfo, wctime, miid);
            try{
                Response<String> response = call.execute();
                return response.body().toString();
            }catch(Exception e){
                Log.d("TAG", "error occured");
            }
            return null;
        }
    }

    // DB에 지정한 코스를 저장함 (COURSEPLACE TABLE)
    public void makeWalkCourse(int order, Marker marker, String miid ){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.insertWalkCourse(order, marker.getPosition().latitude, marker.getPosition().longitude);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && (response.body().equals("1")) ){
                    Log.d("TAG", "SUCESS");
                    Intent intent = new Intent(ExerciseCourseRegit.this, ExerciseDetailCourse.class);
                    intent.putExtra("miid", miid);
                    startActivity(intent);
                }else{
                    // 등록 실패
                    Log.d("TAG", response.body());
                    Toast.makeText(getApplicationContext(),"등록에 실패" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "에러발생!");

            }
        });
    }

}