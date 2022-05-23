package com.example.coursefinder.searchapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient3 {
    // DB 연결 retrofit 클래스, 객체 생성 메소드
    // 안드로이드는 localhost 접속을 위해서 10.0.2.2로만 접속이 가능
    // 자신의 핸드폰으로 연결한 경우 cmd에서 -ipconfig 입력 후 ipv4를 BASE_URL에 입력 EX) http:172.0.1.14/
    private static final String BASE_URL = "http:10.0.2.2/";
    //"http:10.0.2.2/";
    // http:172.30.1.19/
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        // 네이버 검색 api에 비해서 알 수 없는 오류들이 많이 나기 때문에 interceptor를 통해서 어떻게 값이 반환되는지 log창에서 확인이 가능함.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
