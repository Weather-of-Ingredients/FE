package com.example.woi_fe.Retrofit.repository;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;

import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;

public class DietRepository {
    private DietRetrofitAPI dietRetrofitAPI;

    public DietRepository(Context context) {
        dietRetrofitAPI = RetrofitClient.getInstance(context).create(DietRetrofitAPI.class);
    }

    public Call<DietResponseDTO> getDietByDietId(Integer dietId){
        return dietRetrofitAPI.getDietByDietId(dietId);
    }

    public Call<ResponseBody> updateDiet(Integer dietId, DietDTO dietDTO){
        return dietRetrofitAPI.updateDiet(dietId, dietDTO);
    }

    public Call<List<DietResponseDTO>> getAllDiets() {
        return dietRetrofitAPI.getAllDiets();
    }

    public Call<List<DietResponseDTO>> getDietByUserAndDate(String date) {
        return dietRetrofitAPI.getDietByUserAndDate(date);
    }

    public Call<List<DietResponseDTO>> getDietByUserAndToday(){ return dietRetrofitAPI.getDietByUserAndToday(); }

    public Call<List<MenuDTO>> getMenuList(String foodName) { return dietRetrofitAPI.getMenuList(foodName); }
}
