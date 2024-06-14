package com.example.woi_fe.Retrofit.controller;

import com.example.woi_fe.Retrofit.dto.user.LoginRequestDTO;
import com.example.woi_fe.Retrofit.dto.user.UserRegisterDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationRetrofitAPI {
    @POST("/api/user/register")
    Call<ResponseBody> register(@Body UserRegisterDTO userRegisterDTO);

    @POST("/api/user/login")
    Call<ResponseBody> login(@Body LoginRequestDTO loginRequestDTO);
}