package com.example.woi_fe.Retrofit.controller;

import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DietRetrofitAPI {

    @POST("api/diet") // 식단 작성
    Call<ResponseBody> createDiet(@Body DietDTO dietDTO);

    @PUT("api/diet/update/{dietId}") // 식단 수정
    Call<ResponseBody> updateDiet(@Body int dietId, DietDTO dietDTO);

    @DELETE("api/diet/delete/{dietId}") // 식단 삭제
    Call<ResponseBody> deleteDiet(@Path("dietId") int dietId);

    @GET("api/allDiet/") // 식단 목록 가져오기
    Call<List<DietDTO>> getAllDiets();
}