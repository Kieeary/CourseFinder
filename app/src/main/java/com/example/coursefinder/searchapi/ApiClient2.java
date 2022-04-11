package com.example.coursefinder.searchapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient2 {
    private static final String BASE_URL = "https://naveropenapi.apigw.ntruss.com/map-direction/";
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
