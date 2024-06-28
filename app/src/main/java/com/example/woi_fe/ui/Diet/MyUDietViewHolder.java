package com.example.woi_fe.ui.Diet;

import android.view.Menu;

import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ItemDietUpdateMenuBinding;
import com.example.woi_fe.databinding.ItemMenuBinding;

import java.util.List;

public class MyUDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietUpdateMenuBinding binding;

    public MyUDietViewHolder(ItemDietUpdateMenuBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
