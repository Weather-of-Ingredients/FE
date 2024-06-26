package com.nutangel.woi_fe.Retrofit.controller;

import com.nutangel.woi_fe.Retrofit.dto.user.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserAPI {

    @GET("/api/user/info")
    Call<UserInfoResponse> getUserInfo();
}
