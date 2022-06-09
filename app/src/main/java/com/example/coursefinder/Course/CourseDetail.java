package com.example.coursefinder.Course;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.example.coursefinder.MainCategory;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.Review.Review;
import com.example.coursefinder.Review.ReviewDetail;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.courseVo.SelectFromCourseReview;
import com.example.coursefinder.courseVo.SelectFromView;
import com.example.coursefinder.mycourse.MyCourse;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.ResultPath;
import com.example.coursefinder.searchapi.ApiClient2;
import com.example.coursefinder.searchapi.ApiClient3;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetail extends AppCompatActivity implements OnMapReadyCallback {
    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    } ;
    int[] imageId = {
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,
            R.drawable.course_image,

    };



    private ArrayList<CourseInfo> coursePlaces = new ArrayList<CourseInfo>();
    // 네이버맵 지도 객체
    private NaverMap navermap;
    // 마커들을 담음
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    // json형태의 길찾기 경로들을 담음
    private ResultPath resultPath = new ResultPath();
    private Gson gson;
    private SelectFromView selectFromView;

    private TextView cname;
    private TextView cprice;
    private TextView cinfo;

    private ImageButton fav;
    private SharedPreferences sharedPreferences;
    
    private Button places;  // 장소 목록 버튼
    private Button reviews; // 리뷰 목록 버튼
    private Button regit_btn;
    private String miid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        TextView user = null;
        TextView review = null;

        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        MemberLogInResults loginMember = gson.fromJson(member, MemberLogInResults.class);
        miid = loginMember.getMemberInfo().get(0).getId();


        cname = (TextView) findViewById(R.id.course_name);
        cprice = (TextView) findViewById(R.id.textView8);
        cinfo = (TextView) findViewById(R.id.textView7);
        places = (Button) findViewById(R.id.places);
        reviews = (Button) findViewById(R.id.reviews);
        regit_btn = (Button) findViewById(R.id.regit_btn);

        Intent intent = getIntent();
        String ciid = intent.getIntExtra("courseId", 0)+"";
        CoFavChk(miid, ciid);
        try{
            String results = new GetDetail(ciid).execute().get();
            gson = new Gson();
            selectFromView = gson.fromJson(results, SelectFromView.class);
        }catch(Exception e){
            Log.d("TAG", e.getMessage()+" ");
        }
        for(int i=0; i<selectFromView.getCourseLists().size(); i++){
            coursePlaces.add(selectFromView.getCourseLists().get(i));
        }


        // 즐겨찾기 버튼 (코스 저장)
        fav = (ImageButton)findViewById(R.id.add_btn);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원 정보(아이디)를 받아온다.

                if(miid != null){
                    saveCourse(ciid, miid);
                }
            }
        });


        // 지도를 띄움

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        cname.setText(coursePlaces.get(0).getCi_name()); // 코스 이름
        grid=(GridView)findViewById(R.id.grid);
        //  장소 버튼 클릭시 장소 리스트 불러옴
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseDetailGrid adapter = new CourseDetailGrid(CourseDetail.this, web, imageId, coursePlaces);
                grid.setAdapter(adapter);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCourseReview(coursePlaces.get(0).getCi_idx()+"");
            }
        });

        regit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CourseDetail.this, MainCategory.class);
                startActivity(intent2);
            }
        });

    }


    // 지도를 띄워주는 과정
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        // 현재위치 추적
        // ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        // 장소 3개의 마커
        setMarkers();
        // 장소 3개를 이어주는 폴리라인
        setPolyLines();
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(
                new LatLng(coursePlaces.get(0).getCp_lt(), coursePlaces.get(0).getCp_la()));

        naverMap.moveCamera(cameraUpdate);
    }


    // 코스 장소들의 위치를 찍는 마커
    public void setMarkers(){
        Marker marker;
        for(int i=0; i<coursePlaces.size(); i++){
            marker = new Marker();
            marker.setPosition(new LatLng(coursePlaces.get(i).getCp_lt() , coursePlaces.get(i).getCp_la()));
            markers.add(marker);
            markers.get(i).setCaptionText(coursePlaces.get(i).getCp_name().replaceAll("<b>", " ").replaceAll("</b>", " "));
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

    public void saveCourse(String ciid, String miid){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.saveCourse(ciid, miid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body().equals("1")){
                    Log.d("TAG", "코스 저장 성공!");
                    Intent intent = new Intent(CourseDetail.this, MyCourse.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    // 코스의 상세정보들을 받아오는 클래스. 해당 코스의 번호를 매개변수로 받아온다.
    class GetDetail extends AsyncTask<Void, Void, String> {
        String ciid;

        public GetDetail(String ciid) {
            this.ciid = ciid;
        }

        @Override
        protected void onPostExecute(String s) { super.onPostExecute(s); }

        @Override
        protected String doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.getcoursedetail(ciid);
            try{
                Response<String> response = call.execute();
                return response.body().toString();
            }catch(Exception e){
                Log.d("TAG", "error occured");
            }return null;
        }
    }

    public void getCourseReview(String ciid){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.getCourseReview(ciid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && !(response.body().equals("failed"))){
                    String result = response.body();
                    gson = new Gson();
                    SelectFromCourseReview selectFromCourseReview = gson.fromJson(result, SelectFromCourseReview.class);
                    CourseDetailGridReview adapter = new CourseDetailGridReview(CourseDetail.this, web, imageId, selectFromCourseReview.getCourseReview());
                    grid=(GridView)findViewById(R.id.grid);
                    grid.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public void getRoute(){
        // 시작 ,중간지점, 목적지 설정
        String goal = coursePlaces.get(0).getCp_la() +"," + coursePlaces.get(0).getCp_lt();
        String waypoint ="";
        String start = coursePlaces.get(coursePlaces.size()-1).getCp_la() +"," + coursePlaces.get(coursePlaces.size()-1).getCp_lt();

        for(int i=1; i<coursePlaces.size()-1; i++){
            waypoint += "|"+coursePlaces.get(i).getCp_la() +"," + coursePlaces.get(i).getCp_lt();
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

    public void setPolyLines(List<LatLng> latLngList){
        PathOverlay path = new PathOverlay();
        path.setCoords(latLngList);
        path.setWidth(35);
        path.setColor(Color.YELLOW);
        path.setMap(navermap);
    }

    // 즐겨찾기 여부 확인
    public void CoFavChk(String miid, String ciid ) {
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.isCoFav(ciid, miid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body().equals("1")) {
                    fav.setImageResource(R.drawable.ic_heart_pressed);
                    fav.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
