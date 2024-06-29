package com.nutangel.woi_fe.ui.Diet;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.nutangel.woi_fe.databinding.ItemDietUpdateMenuBinding;

import java.util.List;

public class MyUDietAdapter extends RecyclerView.Adapter<MyUDietViewHolder> {
    private Context context;
    private List<MenuResponseDTO> menuList;

    public MyUDietAdapter(Context context, List<MenuResponseDTO> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MyUDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyUDietAdapter", "MyUDietAdapter 어댑터연결 성공");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietUpdateMenuBinding binding = ItemDietUpdateMenuBinding.inflate(layoutInflater, parent, false);
        return new MyUDietViewHolder(binding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyUDietViewHolder holder, int position) {
        MenuResponseDTO menu = menuList.get(position);

        holder.binding.dietItemName.setText(menu.getFoodName());
        holder.binding.caloriesTextView.setText(String.format("%.2f", menu.getCalories()));
        holder.binding.fatTextView.setText(String.format("%.2f", menu.getFat()));
        holder.binding.carbohydrateTextView.setText(String.format("%.2f", menu.getCarbohydrate()));
        holder.binding.proteinTextView.setText(String.format("%.2f", menu.getProtein()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void setMenuList(List<MenuResponseDTO> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }
}
