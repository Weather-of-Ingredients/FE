package com.nutangel.woi_fe.ui.dietcal;

import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.databinding.ItemDietcalBinding;

public class MyTDietViewHolder extends RecyclerView.ViewHolder{
    ItemDietcalBinding binding;

    public MyTDietViewHolder(ItemDietcalBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
