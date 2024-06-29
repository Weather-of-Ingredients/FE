package com.nutangel.woi_fe.Retrofit.controller;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodNutritionRetrofitAPI {

    @GET("/api/nutrition/carbohydrate/{year}/{month}")
    Call<Double> getCarbohydrate(@Path("year") int year, @Path("month") int month);
    @GET("/api/nutrition/protein/{year}/{month}")
    Call<Double> getProtein(@Path("year") int year, @Path("month") int month);
    @GET("/api/nutrition/fat/{year}/{month}")
    Call<Double> getFat(@Path("year") int year, @Path("month") int month);

}
