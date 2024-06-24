package com.example.woi_fe.Retrofit.dto.diet;

public class MenuDTO {
    private Integer menuId;
    private String carbohydrate;
    private String protein;
    private String fat;
    private String foodName;
    private String calories;
    private DietResponseDTO diet;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public DietResponseDTO getDiet() {
        return diet;
    }

    public void setDiet(DietResponseDTO diet) {
        this.diet = diet;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
