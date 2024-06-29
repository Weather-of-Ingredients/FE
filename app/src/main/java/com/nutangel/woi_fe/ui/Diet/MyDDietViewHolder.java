package com.nutangel.woi_fe.ui.Diet;

import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.databinding.ItemDietMenuBinding;

public class MyDDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietMenuBinding binding;

    public MyDDietViewHolder(ItemDietMenuBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
