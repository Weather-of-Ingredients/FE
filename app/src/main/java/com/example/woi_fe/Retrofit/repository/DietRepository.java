package com.example.woi_fe.Retrofit.repository;

import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

import retrofit2.Call;

public class DietRepository {
    private DietRetrofitAPI dietRetrofitAPI;

    public DietRepository() {
        dietRetrofitAPI = RetrofitClient.getInstance().create(DietRetrofitAPI.class);
    }

    public Call<List<DietDTO>> getAllDiets() {
        return dietRetrofitAPI.getAllDiets();
    }

//    public Call<List<DietDTO>> getUserDiets() {
//        return dietRetrofitAPI.getUserDiets();
//    }
}
