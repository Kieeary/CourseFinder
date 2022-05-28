package com.example.coursefinder.Course;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.Review.Review;
import com.example.coursefinder.Review.ReviewDetail;
import com.example.coursefinder.courseVo.CourseInfo;
import com.example.coursefinder.courseVo.SelectFromView;
import com.example.coursefinder.mycourse.MyCourse;
import com.example.coursefinder.searchVo.PlaceList;
import com.example.coursefinder.searchVo.ResultPath;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        TextView user = null;
        TextView review = null;

        cname = (TextView) findViewById(R.id.course_name);
        cprice = (TextView) findViewById(R.id.textView8);
        cinfo = (TextView) findViewById(R.id.textView7);

        Intent intent = getIntent();
        String ciid = intent.getIntExtra("courseId", 0)+"";



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
                sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
                String member = sharedPreferences.getString("MemberInfo", "null");
                Gson gson = new Gson();
                MemberLogInResults loginMember = gson.fromJson(member, MemberLogInResults.class);
                member = loginMember.getMemberInfo().get(0).getId();

                if(member != null){
                    saveCourse(ciid, member);
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

        cname.setText(coursePlaces.get(0).getCi_name());
        //cprice.setText(coursePlaces.get(0).getCi_price()+"");
        //cinfo.setText(coursePlaces.get(0).getCi_info());
        // gridview리스트로 보여준다
        CourseDetailGrid adapter = new CourseDetailGrid(CourseDetail.this, web, imageId, coursePlaces);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);

        //작성자 이름 클릭시 작성자가 지금까지 작성한 리뷰로 넘어감
        /*
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewDetail.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });
        //리뷰 제목 클릭시 해당 코스 리뷰로 넘어감
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Review.class); //현재 액티비티, 이동하고 싶은 액티비티
                startActivity(intent); //액티비티 이동

            }
        });

         */
    }


    // 지도를 띄워주는 과정
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.navermap = naverMap;
        // 현재위치 추적
        // ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        // 첫번째 장소에서 두번째 장소까지의 경로, 지도에 polyline으로 보여주고있음
     //    getRoute();
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
}
