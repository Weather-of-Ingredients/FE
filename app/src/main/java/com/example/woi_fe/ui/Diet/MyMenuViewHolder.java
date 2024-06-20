package com.example.woi_fe.ui.Diet;

import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.databinding.ItemDietBinding;
import com.example.woi_fe.databinding.ItemMenuBinding;

public class MyMenuViewHolder extends RecyclerView.ViewHolder{
    ItemMenuBinding binding;

    public MyMenuViewHolder(ItemMenuBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
