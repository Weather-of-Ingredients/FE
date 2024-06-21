package com.example.woi_fe.ui.Diet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ItemDietUpdateMenuBinding;
import com.example.woi_fe.databinding.ItemDietcalBinding;
import com.example.woi_fe.ui.dietcal.DietCalFragment;
import com.example.woi_fe.ui.dietcal.MyTDietViewHolder;

import java.util.List;

public class MyUDietAdapter extends RecyclerView.Adapter<MyUDietViewHolder>{
    private Context context;
    private List<DietResponseDTO> itemList;

    public MyUDietAdapter(Context context, List<DietResponseDTO> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    public void setDietItems(List<DietResponseDTO> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyUDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietUpdateMenuBinding binding = ItemDietUpdateMenuBinding.inflate(layoutInflater, parent, false);
        return new MyUDietViewHolder(ItemDietUpdateMenuBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull MyUDietViewHolder holder, int position) {
        DietResponseDTO data = itemList.get(position);

        holder.bind((MenuDTO) itemList);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItems(List<DietResponseDTO> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}
