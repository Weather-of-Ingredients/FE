package com.example.woi_fe.Retrofit.dto.diet;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuParDTO implements Parcelable {
    private Integer menuId;
    private double carbohydrate;
    private double protein;
    private double fat;
    private String foodName;
    private double calories;
    private DietResponseDTO diet;

    public MenuParDTO() {
    }

    protected MenuParDTO(Parcel in) {
        if (in.readByte() == 0) {
            menuId = null;
        } else {
            menuId = in.readInt();
        }
        carbohydrate = in.readDouble();
        protein = in.readDouble();
        fat = in.readDouble();
        foodName = in.readString();
        calories = in.readDouble();
        diet = in.readParcelable(DietResponseDTO.class.getClassLoader());
    }

    public static final Creator<MenuParDTO> CREATOR = new Creator<MenuParDTO>() {
        @Override
        public MenuParDTO createFromParcel(Parcel in) {
            return new MenuParDTO(in);
        }

        @Override
        public MenuParDTO[] newArray(int size) {
            return new MenuParDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (menuId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(menuId);
        }
        dest.writeDouble(carbohydrate);
        dest.writeDouble(protein);
        dest.writeDouble(fat);
        dest.writeString(foodName);
        dest.writeDouble(calories);
        dest.writeParcelable((Parcelable) diet, flags);
    }

    // Getters and setters...

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
