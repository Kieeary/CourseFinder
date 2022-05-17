package com.example.coursefinder.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.coursefinder.R;
import com.example.coursefinder.courseVo.ExerciseInfo;
import com.example.coursefinder.courseVo.SelectExerciseFromView;
import com.example.coursefinder.searchVo.ResultPath;
import com.example.coursefinder.searchapi.ApiClient2;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;

import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseRegitDetail extends AppCompatActivity implements OnMapReadyCallback {

    private NaverMap navermap;
    // 마커들을 담음
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    // json형태의 길찾기 경로들을 담음
    private ResultPath resultPath = new ResultPath();
    private Gson gson;
    private SelectExerciseFromView selectExerciseFromView;

    private ArrayList<ExerciseInfo> exerciseInfos;

    private TextView ename, etime, einfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_regit_detail);

        Intent intent = getIntent();
        String miid = intent.getStringExtra("miid");


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        exerciseInfos = new ArrayList<ExerciseInfo>();
        selectExerciseFromView = new SelectExerciseFromView();
        gson = new Gson();

        try {
            String result = new GetExerciseDetail(miid).execute().get();
            selectExerciseFromView = gson.fromJson(result, SelectExerciseFromView.class);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
            Log.d("TAG", "ERROR WHILE GSON");
        }

        for (int i = 0; i < selectExerciseFromView.getExerciseInfos().size(); i++) {
            exerciseInfos.add(selectExerciseFromView.getExerciseInfos().get(i));
        }
        ename = (TextView) findViewById(R.id.course_name);
        etime = (TextView) findViewById(R.id.textView8);
        einfo = (TextView)  findViewById(R.id.textView9);

        ename.setText(exerciseInfos.get(0).getWi_name());
        etime.setText(exerciseInfos.get(0).getWi_time());
        einfo.setText(exerciseInfos.get(0).getWi_info());
    }

    // 지도를 띄워주는 과정
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        getRoute();

        setMarkers();

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(
                new LatLng(exerciseInfos.get(0).getWcp_at(), exerciseInfos.get(0).getWcp_lt()));
        naverMap.moveCamera(cameraUpdate);
    }


    class GetExerciseDetail extends AsyncTask<Void, Void, String> {

        String miid;

        public GetExerciseDetail(String miid) {
            this.miid = miid;
        }

        @Override
        protected void onPostExecute(String s) { super.onPostExecute(s); }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.getwalkcoursedetail(miid, "");
            try{
                Response<String> response = call.execute();
                return response.body().toString();
            }catch(Exception e){
                Log.d("TAG", "error occured");
            }return null;
        }
    }



    public void setMarkers(){
        Marker marker;
        for(int i=0; i<exerciseInfos.size(); i++){
            marker = new Marker();
            marker.setPosition(new LatLng(exerciseInfos.get(i).getWcp_at() , exerciseInfos.get(i).getWcp_lt()));
            markers.add(marker);
            markers.get(i).setMap(navermap);
        }
    }

    public void getRoute(){
        // 시작 ,중간지점, 목적지 설정
        String goal = exerciseInfos.get(0).getWcp_lt() +"," + exerciseInfos.get(0).getWcp_at();
        String waypoint ="";
        String start = exerciseInfos.get(exerciseInfos.size()-1).getWcp_lt() +"," + exerciseInfos.get(exerciseInfos.size()-1).getWcp_at();

        for(int i=1; i<exerciseInfos.size()-1; i++){
            waypoint += "|"+exerciseInfos.get(i).getWcp_lt() +"," + exerciseInfos.get(i).getWcp_at();
        }
        if(!(waypoint.equals("")))  waypoint = waypoint.substring(1);
        Log.d("TAG", waypoint +" = waypoint");

        ApiInterface apiInterface = ApiClient2.getInstance().create(ApiInterface.class);
        Call<ResultPath> call = apiInterface.getRoute("53o7d43lub", "kFF1Zm70tVyOZl2o2hpP1yyol1rxp3Hk51xhbIbr",
                start, goal, waypoint);
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
                    Log.d("TAG", "실패 : " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResultPath> call, Throwable t) {
                Log.d("TAG", "에러 : " + t.getMessage());
            }
        });
    }

    // 길찾기 이용 시 폴리라인
    public void setPolyLines(List<LatLng> latLngList){
        PathOverlay path = new PathOverlay();
        path.setCoords(latLngList);
        path.setWidth(35);
        path.setColor(Color.YELLOW);
        path.setMap(navermap);
    }

}






