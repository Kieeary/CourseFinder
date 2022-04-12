package com.example.coursefinder.searchapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    // 장소 검색 retrofit 객체 생성 클래스 및 메소드
    // BASE_URL은 고정되야함, 변경 하지 말 것
    // .addConverterFactory(GsonConverterFactory.create(gson)) 는 ApiInterface에서 Call<Object> (Object >지정한 타입(객체도 가능)으로 리턴을 해줌
    // json형식의 문자열을 자동으로 반환을 시켜주기 위해서 retrofit 생성시 build를 했으나, 작동이 안되는 경우가 있음.
    // 작동이 안될 경우에는 Call<String> 으로 반환 후에 gson 객체를 생성하고 gson.from(result, 변환할 클래스)로 변환시킬것
    private static final String BASE_URL = "https://openapi.naver.com/v1/";
    private static Retrofit retrofit;

    public static Retrofit getInstance(){


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
