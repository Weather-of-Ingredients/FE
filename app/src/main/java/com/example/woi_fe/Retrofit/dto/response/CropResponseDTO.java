package com.example.woi_fe.Retrofit.dto.response;

import com.google.gson.annotations.SerializedName;

public class CropResponseDTO<T> {

    private Boolean success;
    private T data;


    public T getData() {
        return data;
    }
}
