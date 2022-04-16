package com.example.coursefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.coursefinder.searchapi.ApiClient3;
import com.example.coursefinder.searchapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static boolean ischked = false;
    // 중복 확인 버튼
    private Button dupChk;
    private Button register;
    private EditText inputId;
    private EditText inputPwd;
    private EditText pwdChk;
    private EditText inputName;
    private EditText inputPhone;
    private EditText inputBirth;
    private RadioButton man;
    private RadioButton women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dupChk = (Button)findViewById(R.id.duplicate);
        register = (Button)findViewById(R.id.registerFinish);
        inputId = (EditText) findViewById(R.id.inputId);
        inputPwd = (EditText) findViewById(R.id.pwsd);
        inputName = (EditText)findViewById(R.id.name);
        inputBirth = (EditText)findViewById(R.id.birth);
        pwdChk = (EditText) findViewById(R.id.pwsd2);
        inputPhone  = findViewById(R.id.phoneNum);
        man = findViewById(R.id.man);
        women = findViewById(R.id.woman);

        // 아이디 중복 체크
        dupChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dupChk(inputId.getText().toString());
            }
        });

        // 회원 가입
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = inputPwd.getText().toString();
                if(pwd.equals(pwdChk.getText().toString()) && ischked){
                    Log.d("TAG", ischked+"");
                    // 회원 가입 함수 호출
                    String id = inputId.getText().toString();
                    String name = inputName.getText().toString();
                    String phone = inputPhone.getText().toString();
                    String birth = inputBirth.getText().toString();
                    String gender = man.isChecked()? "m" : "w";

                    doRegister(id, pwd, name, phone, birth, gender);
                }else{
                    Log.d("TAG", ischked+"");
                    Log.d("TAG", pwd +" , " + pwdChk.getText().toString());
                    Log.d("TAG", "비밀번호가 다릅니다!");
                }
            }
        });

    }

    public void dupChk(String id){
        Log.d("TAG", "INPUT ID : " + id);
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.dupChk(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && (response.body().equals("1")) ){
                    // 아이디 이용 가능
                    ischked = true;
                    Log.d("TAG", "SUCESS");
                    Toast.makeText(getApplicationContext(),"사용가능한 아이디입니다", Toast.LENGTH_SHORT);
                }else{
                    // 중복 아이디 존재
                    ischked = false;
                    Log.d("TAG", "DUP");
                    Toast.makeText(getApplicationContext(),"중복된 아이디 입니다", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "연결 오류");
            }
        });
    }


    // 회원 등록 함수 필요
    public void doRegister(String id, String pwd, String name, String phone, String birth, String gender){
        ApiInterface apiInterface = ApiClient3.getInstance().create(ApiInterface.class);
        Call<String> call = apiInterface.insertMember(id, pwd);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && (response.body().equals("1")) ){
                    // 가입 성공
                 }else{
                 }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", "연결 오류");
            }
        });
    }
}