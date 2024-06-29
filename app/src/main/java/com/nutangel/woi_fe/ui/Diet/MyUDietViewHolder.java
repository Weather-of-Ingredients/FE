package com.nutangel.woi_fe.ui.Diet;

import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.databinding.ItemDietUpdateMenuBinding;

public class MyUDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietUpdateMenuBinding binding;

    public MyUDietViewHolder(ItemDietUpdateMenuBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
