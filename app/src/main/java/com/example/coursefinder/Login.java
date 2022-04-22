package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursefinder.MemberVo.MemberInfo;
import com.example.coursefinder.MemberVo.MemberLogInResults;
import com.example.coursefinder.searchVo.ResultPath;
import com.example.coursefinder.searchapi.ApiClient2;
import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Button login;
    private Button register;
    private EditText inputId;
    private EditText inputPw;

    private String id, pwd;
    private String TAG = "LOGIN";
    private MemberLogInResults memberLogInResults;
    private List<MemberInfo> memberInfo = new ArrayList<MemberInfo>();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("Member", MODE_PRIVATE);
//        로그아웃 시 sharedpreference 전부 삭제
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();    editor.commit();

        String userId = sharedPreferences.getString("MemberInfo", null);
        if(userId != null){
            Intent intent = new Intent(Login.this, MainCategory.class); //현재 액티비티, 이동하고 싶은 액티비티
            startActivity(intent);
        }

        login = findViewById(R.id.bt_login);
        inputId = (EditText) findViewById(R.id.et_inputid);
        inputPw = (EditText) findViewById(R.id.et_inputPwsd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userId == null){
                    id = inputId.getText().toString();
                    pwd = inputPw.getText().toString();
                    doLogin(id,pwd);
                }
            }
        });

        register = findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }


    public void doLogin(String id, String password){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.doLogin(id, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && !(response.body().equals("failed"))) {
                    String result = response.body();
                    editor.putString("MemberInfo", result);
                    editor.commit();

                    Intent intent = new Intent(Login.this, MainCategory.class); //현재 액티비티, 이동하고 싶은 액티비티
                    startActivity(intent); //액티비티 이동
                    /*
                    SharedPreferences에는 문자열로 저장해야 한다. json형태의 문자열로 저장 후 나중에 꺼내 쓸 때, gson을 이용해서 다시 파싱해서 사용해야 한다
                    Gson gson = new Gson();
                     memberLogInResults = gson.fromJson(result, MemberLogInResults.class);
                     memberInfo = memberLogInResults.getMemberInfo();
                    Log.d("TAG", memberInfo.get(0).getId());
                     */

                }else{
                    Log.d("TAG", "로그인에 실패");
                    Log.d("TAG", "ID :" + id +", pwd :" + password);
                    Log.d("TAG", response.body()+"임");
                    Toast toast = Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "에러 : " + t.getMessage());
            }
        });
    }
}