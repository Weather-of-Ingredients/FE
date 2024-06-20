package com.example.woi_fe.ui.Diet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.databinding.ItemMenuBinding;

import java.util.List;

public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuViewHolder>{
    private Context context;
    private List<MenuDTO> itemList;

    public MyMenuAdapter(Context context, List<MenuDTO> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMenuBinding binding = ItemMenuBinding.inflate(layoutInflater, parent, false);
        return new MyMenuViewHolder(ItemMenuBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMenuViewHolder holder, int position) {
        MenuDTO data = itemList.get(position);

        holder.binding.itemFoodNameView.setText(data.getFoodName());
        holder.binding.itemCalView.setText(data.getCalories());
        holder.binding.itemCarView.setText(data.getCarbohydrate());
        holder.binding.itemFatView.setText(data.getFat());
        holder.binding.itemProView.setText(data.getProtein());

        holder.binding.itemFoodNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foodName", data.getFoodName());
                bundle.putString("carbohydrate", data.getCarbohydrate());
                bundle.putString("protein", data.getProtein());
                bundle.putString("fat", data.getFat());
                bundle.putString("calories", data.getCalories());

                Intent intent = new Intent(context, DietUpdateFragment.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItems(List<MenuDTO> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}
