package com.example.coursefinder.mycourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.UploadImg;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseInfo;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExCourseReview extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private MemberLogInResults loginMember;
    public static final int PICK_IMAGE = 100;
    private static String filePath = null;
    private static String miid="";
    String uuid;
    private RatingBar ratingBar;
    private ExerciseInfo exerciseInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_review);

        Intent intent = getIntent();
        exerciseInfo = (ExerciseInfo) (intent.getSerializableExtra("Exinfo"));

        Log.d("TAG", exerciseInfo.getWi_name()+"");


        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        loginMember = gson.fromJson(member, MemberLogInResults.class);
        miid = loginMember.getMemberInfo().get(0).getId();

        ratingBar = findViewById(R.id.ratingBar);

        Button cancelButton = (Button) findViewById(R.id.button3);
        Button regitButton = (Button) findViewById(R.id.button1);
        Button imgupload = (Button) findViewById(R.id.img_upload);


        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        regitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath != null){
                    getParts(exerciseInfo, miid, filePath, "12", "sadfsadf", ratingBar.getRating());
                }else{
                    saveReview(exerciseInfo, miid, "12", "sadfsadf", ratingBar.getRating(), null, null);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView img = (ImageView) findViewById(R.id.img);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            filePath = UploadImg.getRealPathFromURI_API19(this, data.getData());
            Glide.with(this).load(filePath).into(img);
        }
    }


    public void getParts(ExerciseInfo exerciseInfo, String miid, String filePath, String wcontent, String wcrtitle, double grade){
        uuid = UUID.randomUUID().toString();
        File file = new File(filePath);
        String filename = "test5" + uuid + filePath.substring(filePath.lastIndexOf("."));

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), reqFile);
        RequestBody idx = RequestBody.create(MediaType.parse("text/plain"), filename);
        saveReview(exerciseInfo, miid, wcontent, wcrtitle, grade ,body,idx);
    }

    public void saveReview(ExerciseInfo exerciseInfo, String miid, String content, String title, double grade, @Nullable MultipartBody.Part body, @Nullable RequestBody idx){
        RequestBody wiidx = RequestBody.create(MediaType.parse("text/plain"), exerciseInfo.getWi_idx()+"");
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"),miid);
        RequestBody wcontent = RequestBody.create(MediaType.parse("text/plain"),"이것은내요입니다");
        RequestBody wcrtitle = RequestBody.create(MediaType.parse("text/plain"),"제목을입력하십시오?");
        RequestBody wgrade = RequestBody.create(MediaType.parse("text/plain"),grade+"");


        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.saveExReview(wiidx, id,wcrtitle, wcontent, wgrade, body, idx);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body().equals("1")){
                    Log.d("TAG", "리뷰 등록 성공!");
                }else{
                    Log.d("TAG", "리뷰 등록에 실패");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

}