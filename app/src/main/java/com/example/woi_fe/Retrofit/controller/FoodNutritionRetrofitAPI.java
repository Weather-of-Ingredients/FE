package com.example.woi_fe.Retrofit.controller;

import com.example.woi_fe.Retrofit.dto.foodNutrition.NutritionDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodNutritionRetrofitAPI {

    @GET("/carbohydrate/{year}/{month}")
    Call<NutritionDTO> getCarbohydrate(@Path("year") int year, @Path("month") int month);
    @GET("/protein/{year}/{month}")
    Call<NutritionDTO> getProtein(@Path("year") int year, @Path("month") int month);
    @GET("/fat/{year}/{month}")
    Call<NutritionDTO> getFat(@Path("year") int year, @Path("month") int month);

}
