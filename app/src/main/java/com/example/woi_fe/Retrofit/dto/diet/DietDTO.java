package com.example.woi_fe.Retrofit.dto.diet;

import com.example.woi_fe.Retrofit.dto.user.UserDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DietDTO {
    @SerializedName("dietId")
    private Integer dietId;
    @SerializedName("date")
    private String date;
    @SerializedName("type")
    private String type;
    @SerializedName("week")
    private String week;

    @SerializedName("menus")
    private List<MenuResponseDTO> menus;
    @SerializedName("userEntity")
    private UserDTO userEntity;

    public Integer getDietId() {
        return dietId;
    }

    public void setDietId(Integer dietId) {
        this.dietId = dietId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<MenuResponseDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuResponseDTO> menus) {
        this.menus = menus;
    }

    public UserDTO getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserDTO userEntity) {
        this.userEntity = userEntity;
    }
}
