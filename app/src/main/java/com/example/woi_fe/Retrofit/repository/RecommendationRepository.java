package com.example.woi_fe.Retrofit.repository;

import com.example.woi_fe.Retrofit.controller.RecommendationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;

public class RecommendationRepository {
    private RecommendationRetrofitAPI recommendationRetrofitAPI;

    public RecommendationRepository(){
        recommendationRetrofitAPI = RetrofitClient.getInstance().create(RecommendationRetrofitAPI.class);
    }

    public Call<List<CropItem>> getAllCropItems(){
        return recommendationRetrofitAPI.getAllCropItems();
    }

    public Call<CropResponseDTO<List<CropItem>>> getCropItems(int year, int month, String bad_crops){
        return recommendationRetrofitAPI.getCropItems(year, month, bad_crops);
    }
}