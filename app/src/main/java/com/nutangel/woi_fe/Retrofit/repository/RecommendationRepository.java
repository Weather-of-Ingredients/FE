package com.nutangel.woi_fe.Retrofit.repository;

import android.content.Context;

import com.nutangel.woi_fe.Retrofit.controller.RecommendationRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.recommendation.BadCropMenuDTO;
import com.nutangel.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.nutangel.woi_fe.Retrofit.dto.recommendation.RecommendationDTO;
import com.nutangel.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;

public class RecommendationRepository {
    private RecommendationRetrofitAPI recommendationRetrofitAPI;

    public RecommendationRepository(Context context){
        recommendationRetrofitAPI = RetrofitClient.getInstance(context).create(RecommendationRetrofitAPI.class);
    }

    public Call<List<CropItem>> getAllCropItems(){
        return recommendationRetrofitAPI.getAllCropItems();
    }

    public Call<CropResponseDTO<List<CropItem>>> getCropItems(int year, int month, String bad_crops){
        return recommendationRetrofitAPI.getCropItems(year, month, bad_crops);
    }

    public Call<CropResponseDTO<RecommendationDTO>> getRecommendationDTO(int year, int month){
        return recommendationRetrofitAPI.getRecommendationDTO(year, month);
    }

    public Call<List<BadCropMenuDTO>> getBadCropsMenus(int year, int month){
        return recommendationRetrofitAPI.getBadCropsMenus(year, month);
    }
}
