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

import retrofit2.Call;
import retrofit2.http.Path;

public class DietRepository {
    private DietRetrofitAPI dietRetrofitAPI;

    public DietRepository(Context context) {
        dietRetrofitAPI = RetrofitClient.getInstance(context).create(DietRetrofitAPI.class);
    }

    public Call<List<DietResponseDTO>> getAllDiets() {
        return dietRetrofitAPI.getAllDiets();
    }

    public Call<List<DietResponseDTO>> getUserDiets() {
        return dietRetrofitAPI.getUserDiets();
    }

    public Call<List<MenuDTO>> getMenuList(String foodName) { return dietRetrofitAPI.getMenuList(foodName); }
}
