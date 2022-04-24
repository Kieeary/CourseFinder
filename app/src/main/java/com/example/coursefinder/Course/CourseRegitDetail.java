package com.example.coursefinder.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.Result;
import com.example.coursefinder.mycourse.MyCourse;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.ResultPath;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseRegitDetail extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<PlaceList> placeLists = new ArrayList<>();
    private static int cnt=0;
    // 네이버맵 지도 객체
    private NaverMap navermap;

    // 마커들을 담음
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    // json형태의 길찾기 경로들을 담음
    private ResultPath resultPath = new ResultPath();
    private Gson gson;

    private Button nextBtn;
    private Button cancelBtn;
    private SharedPreferences sharedPreferences;
    private MemberLogInResults loginMember;


    private String cname;
    private String cinfo;
    private int price;

    EditText inputCname;
    EditText inputCinfo;
    EditText inputPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_regit_detail);
        // 검색 후 선택된 장소들을 받아온다
        Intent intent = getIntent();
        placeLists = (ArrayList<PlaceList>) intent.getSerializableExtra("placeList");


        nextBtn = (Button)findViewById(R.id.next);
        cancelBtn = (Button)findViewById(R.id.cancel);
        inputCname = (EditText)findViewById(R.id.course_name);
        inputCinfo = (EditText)findViewById(R.id.textView7);
        inputPrice = (EditText)findViewById(R.id.textView8);

        // 로그인 정보를 받아온다.
        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        loginMember = gson.fromJson(member, MemberLogInResults.class);
        member = loginMember.getMemberInfo().get(0).getId();


        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        String finalMember = member;

        /**
         * 사용자가 지정한 정보들이 담겨져 있는 ArrayList placeLists에 있는 장소들을 리스트로 보여줘야 합니다
         * */





        // 코스 이름이나 설명 edittext에서 받아오는 형식
        // 확인 버튼 클릭 << db에 저장
        // 확인 버튼 클릭시 저장한 코스로 이동합니다.
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cname = inputCname.getText().toString();
                cinfo = inputCinfo.getText().toString();
                price = Integer.parseInt(inputPrice.getText().toString());
                String ci_cata ="";
                // 지정한 3개의 장소들의 cata들 모두 더하지만 구분문자 '@'를 통해서 구분지을 수 있도록 함
                for(int i=0; i<3; i++){
                    ci_cata += placeLists.get(i).getCategory()+"@";
                }

                Log.d("TAG", ci_cata +"임");

                makeCourseInfo(cname, cinfo, price, placeLists.get(0).getImgLink(), finalMember, ci_cata);
                for(int i=0; i<3; i++){
                    makeCourse(cname , placeLists.get(i), price, i+1, placeLists.get(i).getImgLink());
                }
            }
        });
    }

    // 지도를 띄워주는 과정
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        // 장소 3개의 마커
        setMarkers();
        // 장소 3개를 이어주는 폴리라인
        setPolyLines();
        Tm128 tm = new Tm128(placeLists.get(0).getMapx(), placeLists.get(0).getMapy());
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(
                new LatLng(tm.toLatLng().latitude, tm.toLatLng().longitude));
        naverMap.moveCamera(cameraUpdate);
    }


    // 코스 장소들의 위치를 찍는 마커
    public void setMarkers(){
        Marker marker;
        for(int i=0; i<placeLists.size(); i++){
            marker = new Marker();
            Tm128 tm = new Tm128(placeLists.get(i).getMapx(), placeLists.get(i).getMapy());
            marker.setPosition(tm.toLatLng());
            markers.add(marker);
            markers.get(i).setCaptionText(placeLists.get(i).getTitle().replaceAll("<b>", " ").replaceAll("</b>", " "));
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


    public void makeCourseInfo(String cname, String cinfo, int cprice, String cimg, String miid, String ci_cata){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.insertCourseInfo(cname, cinfo, cprice, cimg, miid,
                ci_cata.substring(0, ci_cata.trim().lastIndexOf("@")));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && (response.body().equals("1")) ){
                    Log.d("TAG", "info테이블 등록 성공");
                }else {
                    Log.d("TAG", "INFO테이블 등록 실패" + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "ERROR" + t.getMessage());
            }
        });
    }

    // CourseInfo table에 저장
    class MakeCourseInfo extends AsyncTask<Void, Void, String> {
        String cname;
        String cinfo;
        int cprice;
        String cimg;
        String miid;
        String ci_cata;

        public MakeCourseInfo(String cname, String cinfo, int cprice, String cimg, String miid, String ci_cata) {
            this.cname = cname;
            this.cinfo = cinfo;
            this.cprice = cprice;
            this.cimg = cimg;
            this.miid = miid;
            this.ci_cata = ci_cata;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.insertCourseInfo(cname, cinfo, cprice, cimg, miid,
                    ci_cata.substring(0, ci_cata.lastIndexOf("@")-1));
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
    public void makeCourse(String courseName, PlaceList places, int price, int order ,String imgLink){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Tm128 tm = new Tm128(places.getMapx(), places.getMapy());
        LatLng latLng = tm.toLatLng();
        Call<String> call = apiInterface.insertCourse(
                order, places.getTitle().replaceAll("<b>", "").replaceAll("</b>", ""), latLng.longitude, latLng.latitude, places.getAddress(), imgLink, places.getCategory()
        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && (response.body().equals("1")) ){

                    Log.d("TAG", "SUCESS");
                    cnt++;
                    Log.d("TAG", cnt +"in retrofit");
                    if(cnt==3){
                        Log.d("TAG", "전부 다 들어간 상태");
                        Intent intent = new Intent(CourseRegitDetail.this, MyCourse.class);
                        startActivity(intent);
                    }
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