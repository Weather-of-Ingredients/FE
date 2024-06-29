package com.nutangel.woi_fe.Retrofit.dto.recommendation;

import java.util.List;

public class RecommendationDTO {

    private int year;

    private int month;

    private List<CropItem> good_crop;
    private List<CropItem> bad_crop;
    private List<CropItem> alt_crop;

    public List<CropItem> getGood_crop() {
        return good_crop;
    }

    public List<CropItem> getBad_crop() {
        return bad_crop;
    }

    public List<CropItem> getAlt_crop() {
        return alt_crop;
    }
}
