package com.example.woi_fe.ui.dietcal;

import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.databinding.ItemDietcalBinding;
import com.example.woi_fe.databinding.ItemMenuBinding;

public class MyTDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietcalBinding binding;

    public MyTDietViewHolder(ItemDietcalBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
