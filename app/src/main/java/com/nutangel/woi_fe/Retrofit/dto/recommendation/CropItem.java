package com.nutangel.woi_fe.Retrofit.dto.recommendation;

import com.google.gson.annotations.SerializedName;

public class CropItem {
    @SerializedName("PRDLST_NM")
    private String ingredient_name;
    @SerializedName("IMG_URL")
    private String ingredient_image;

    public String getIngredient_name() {
        return ingredient_name;
    }

    public String getIngredient_image() {
        return ingredient_image;
    }
}
