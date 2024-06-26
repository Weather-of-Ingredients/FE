package com.nutangel.woi_fe.Retrofit.dto.diet;

public class MenuDTO {
    private Integer menuId;
    private double carbohydrate;
    private double protein;
    private double fat;
    private String foodName;
    private double calories;
    private DietResponseDTO diet;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public DietResponseDTO getDiet() {
        return diet;
    }

    public void setDiet(DietResponseDTO diet) {
        this.diet = diet;
    }

}
