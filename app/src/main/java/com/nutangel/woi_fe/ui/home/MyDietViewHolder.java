package com.nutangel.woi_fe.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.databinding.ItemDietBinding;

public class MyDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietBinding binding;

    public MyDietViewHolder(ItemDietBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
