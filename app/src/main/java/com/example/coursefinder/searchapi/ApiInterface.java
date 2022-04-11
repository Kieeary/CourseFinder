package com.example.coursefinder.searchapi;

import com.example.coursefinder.searchVo.ResultPath;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // 키워드 검색 함수
    @GET("search/{type}")
    Call<String> getSearchResult(
            @Header("X-Naver-Client-Id") String id,
            @Header("X-Naver-Client-Secret") String pw,
            @Path("type") String type,
            @Query("query") String query,
            @Query("display") int num
    );

    // 이미지 검색 함수
    @GET("search/{type}")
    Call<String> getImageResult(
            @Header("X-Naver-Client-Id") String id,
            @Header("X-Naver-Client-Secret") String pw,
            @Path("type") String type,
            @Query("query") String query,
            @Query("display") int num,
            @Query("small") String filter
    );

    // 경로찾기 검색 함수
    @GET("v1/driving")
    Call<ResultPath> getRoute(
            @Header("X-NCP-APIGW-API-KEY-ID") String id,
            @Header("X-NCP-APIGW-API-KEY") String pw,
            @Query("start") String start,
            @Query("goal") String goal
    );
}
