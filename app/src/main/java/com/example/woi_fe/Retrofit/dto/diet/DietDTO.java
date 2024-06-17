package com.example.woi_fe.Retrofit.dto.diet;

public class DietDTO {
    private Integer dietId;
    private String date;
    private String type;
    private String week;

    private String menus;

    public Integer getDietId() {
        return dietId;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getWeek() {
        return week;
    }

    public String getMenus() {
        return menus;
    }

    public void setDietId(Integer dietId) {
        this.dietId = dietId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }
}
