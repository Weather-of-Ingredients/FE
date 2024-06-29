package com.nutangel.woi_fe.ui.Diet;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.nutangel.woi_fe.databinding.ItemDietMenuBinding;

import java.util.List;

public class MyDDietAdapter extends RecyclerView.Adapter<MyDDietViewHolder> {
    private Context context;
    private List<MenuResponseDTO> menuList;

    public MyDDietAdapter(Context context, List<MenuResponseDTO> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MyDDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyDDietAdapter", "MyDDietAdapter 어댑터연결 성공");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietMenuBinding binding = ItemDietMenuBinding.inflate(layoutInflater, parent, false);
        return new MyDDietViewHolder(binding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyDDietViewHolder holder, int position) {
        MenuResponseDTO data = menuList.get(position);

        holder.binding.dietItemName.setText(data.getFoodName());
        holder.binding.caloriesTextView.setText(String.format("%.2f", data.getCalories()));
        holder.binding.fatTextView.setText(String.format("%.2f", data.getFat()));
        holder.binding.carbohydrateTextView.setText(String.format("%.2f", data.getCarbohydrate()));
        holder.binding.proteinTextView.setText(String.format("%.2f", data.getProtein()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

}
