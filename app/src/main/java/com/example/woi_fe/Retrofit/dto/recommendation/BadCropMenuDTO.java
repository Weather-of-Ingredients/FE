package com.example.woi_fe.Retrofit.dto.recommendation;

public class BadCropMenuDTO {
    private int year;
    private int month;
    private int day;

    private String food_name;
    private String bad_crop_name;
    private String alt_crop_name;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getBad_crop_name() {
        return bad_crop_name;
    }

    public String getAlt_crop_name() {
        return alt_crop_name;
    }
}
