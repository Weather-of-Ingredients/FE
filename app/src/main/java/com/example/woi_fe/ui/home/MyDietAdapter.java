package com.example.woi_fe.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.databinding.ItemDietBinding;

import java.util.List;

public class MyDietAdapter extends RecyclerView.Adapter<MyDietViewHolder>{
    private Context context;
    private List<DietDTO> itemList;

    public MyDietAdapter(Context context, List<DietDTO> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyDietViewHolder(ItemDietBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull MyDietViewHolder holder, int position) {
        DietDTO data = itemList.get(position);

        holder.binding.itemTypeView.setText(data.getType());
        holder.binding.itemDateView.setText(data.getDate());
        holder.binding.itemWeekView.setText(data.getWeek());
        holder.binding.itemMenusView.setText(data.getMenus());

        holder.binding.itemMenusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", data.getType());
                bundle.putString("date", data.getDate());
                bundle.putString("week", data.getWeek());
                bundle.putString("menus", data.getMenus());

//                Intent intent = new Intent(context, DietDetailFragment.class);
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}