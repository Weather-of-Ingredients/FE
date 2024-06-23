package com.example.woi_fe.ui.Diet;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ItemDietUpdateMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MyUDietAdapter extends RecyclerView.Adapter<MyUDietViewHolder> {
    private Context context;
    private List<MenuDTO> menuList;

    public MyUDietAdapter(Context context, List<MenuDTO> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MyUDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietUpdateMenuBinding binding = ItemDietUpdateMenuBinding.inflate(layoutInflater, parent, false);
        return new MyUDietViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUDietViewHolder holder, int position) {
        MenuDTO menu = menuList.get(position);

        holder.binding.dietItemName.setText(menu.getFoodName());
        holder.binding.caloriesTextView.setText(menu.getCalories());
        holder.binding.fatTextView.setText(menu.getFat());
        holder.binding.carbohydrateTextView.setText(menu.getCarbohydrate());
        holder.binding.proteinTextView.setText(menu.getProtein());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }
}
