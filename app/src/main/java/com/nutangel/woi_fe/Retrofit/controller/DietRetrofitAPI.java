package com.nutangel.woi_fe.Retrofit.controller;

import com.nutangel.woi_fe.Retrofit.dto.diet.DietDTO;
import com.nutangel.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.nutangel.woi_fe.Retrofit.dto.diet.MenuDTO;

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
    @POST("/api/diet") // 식단 작성
    Call<ResponseBody> createDiet(@Body DietDTO dietDTO);

    @PUT("/api/diet/update/{dietId}") // 식단 수정
    Call<ResponseBody> updateDiet(@Path("dietId") int dietId, @Body DietDTO dietDTO);

    @DELETE("/api/diet/delete/{dietId}") // 식단 삭제
    Call<Void> deleteDiet(@Path("dietId") int dietId);

    @GET("/api/diet/get/{dietId}")
    Call<DietResponseDTO> getDietByDietId(@Path("dietId") Integer dietId);

    @GET("/api/diet/all") // 식단 목록 가져오기
    Call<List<DietResponseDTO>> getAllDiets();

    @GET("/api/user/diet/{date}") // 사용자별 날짜별로 식단 가져오기
    Call<List<DietResponseDTO>> getDietByUserAndDate(@Path("date") String date);

    @GET("/api/user/diet/today") // 사용자별 오늘자 식단 가져오기
    Call<List<DietResponseDTO>> getDietByUserAndToday();

    @GET("/api/menus/{food_Name}") // 메뉴 검색
    Call<List<MenuDTO>> getMenuList(@Path("food_Name") String foodName);
}
