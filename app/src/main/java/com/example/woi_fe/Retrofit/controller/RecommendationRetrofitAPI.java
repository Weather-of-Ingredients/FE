package com.example.woi_fe.Retrofit.controller;

import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RecommendationRetrofitAPI {

    @GET("/api/crops")
    Call<List<CropItem>> getAllCropItems();


    /*@GET("/api/crops/{year}/{month}/{bad_crops}")
    Call<CropResponseDTO<List<CropItem>>> getCropItems(@Header("Authorization") String token, @Path("year") int year, @Path("month") int month, @Path("bad_crops") String bad_crops);
*/

    @GET("/api/crops/{year}/{month}/{bad_crops}")
    Call<CropResponseDTO<List<CropItem>>> getCropItems(@Path("year") int year, @Path("month") int month, @Path("bad_crops") String bad_crops);

}
