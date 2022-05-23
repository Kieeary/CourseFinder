package com.example.coursefinder.mycourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coursefinder.MainCategory;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.R;
import com.example.coursefinder.UploadImg;
import com.example.coursefinder.courseVo.CourseListVo;
import com.example.coursefinder.courseVo.ExerciseInfo;
import com.example.coursefinder.courseVo.ExerciseListVo;
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
    private ExerciseListVo exerciseInfo = null;
    private EditText contents;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_review);

        Intent intent = getIntent();
        exerciseInfo = (ExerciseListVo) (intent.getSerializableExtra("Exinfo"));

        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
        String member = sharedPreferences.getString("MemberInfo", "null");
        Gson gson = new Gson();
        loginMember = gson.fromJson(member, MemberLogInResults.class);
        miid = loginMember.getMemberInfo().get(0).getId();

        contents = (EditText)findViewById(R.id.textview);
        ratingBar = findViewById(R.id.ratingBar);

        EditText textView = (EditText) findViewById(R.id.textView);


        Button cancelButton = (Button) findViewById(R.id.button3);
        Button regitButton = (Button) findViewById(R.id.button1);
        Button imgupload = (Button) findViewById(R.id.img_upload);

        verifyStoragePermissions(this);
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
                    String content = contents.getText().toString();
                    String title = textView.getText().toString();
                    getParts(exerciseInfo, miid, filePath, content, title, ratingBar.getRating());
                }else{
                    String content = contents.getText().toString();
                    String title = textView.getText().toString();
                    saveReview(exerciseInfo, miid, content, title, ratingBar.getRating(), null, null);
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


    public void getParts(ExerciseListVo exerciseInfo, String miid, String filePath, String wcontent, String wcrtitle, double grade){
        uuid = UUID.randomUUID().toString();
        File file = new File(filePath);
        String filename = "test5" + uuid + filePath.substring(filePath.lastIndexOf("."));
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), reqFile);
        RequestBody idx = RequestBody.create(MediaType.parse("text/plain"), filename);
        saveReview(exerciseInfo, miid, wcontent, wcrtitle, grade ,body,idx);
    }

    public void saveReview(ExerciseListVo exerciseInfo, String miid, String content, String title, double grade, @Nullable MultipartBody.Part body, @Nullable RequestBody idx){
        RequestBody wiidx = RequestBody.create(MediaType.parse("text/plain"), exerciseInfo.getWi_idx()+"");
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"),miid);
        RequestBody wcontent = RequestBody.create(MediaType.parse("text/plain"),content);
        RequestBody wcrtitle = RequestBody.create(MediaType.parse("text/plain"),title);
        RequestBody wgrade = RequestBody.create(MediaType.parse("text/plain"),grade+"");


        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.saveExReview(wiidx, id,wcrtitle, wcontent, wgrade, body, idx);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body().equals("1")){
                    Toast.makeText(getApplicationContext(), "리뷰가 등록되었습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ExCourseReview.this, MainCategory.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "리뷰등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "리뷰 등록에 실패");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}