package com.nutangel.woi_fe.Retrofit.repository;

import android.content.Context;

import com.nutangel.woi_fe.Retrofit.controller.FoodNutritionRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;

import retrofit2.Call;

public class FoodNutritionRepository {

    private FoodNutritionRetrofitAPI foodNutritionRetrofitAPI;

    public FoodNutritionRepository(Context context) {
        foodNutritionRetrofitAPI = RetrofitClient.getInstance(context).create(FoodNutritionRetrofitAPI.class);
    }

    public Call<Double> getCarbohydrate(int year, int month){
        return foodNutritionRetrofitAPI.getCarbohydrate(year, month);
    }

    public Call<Double> getProtein(int year, int month){
        return foodNutritionRetrofitAPI.getProtein(year, month);
    }

    public Call<Double> getFat(int year, int month){
        return foodNutritionRetrofitAPI.getFat(year, month);
    }
}
