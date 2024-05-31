package com.example.woi_fe.Retrofit.dto.response;

public class CropResponseDTO<T> {
    private Boolean success;
    private T data;


    public T getData() {
        return data;
    }
}
