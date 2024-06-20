package com.example.woi_fe.ui.dietcal;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ItemDietBinding;
import com.example.woi_fe.databinding.ItemDietcalBinding;
import com.example.woi_fe.ui.Diet.DietUpdateFragment;
import com.example.woi_fe.ui.home.MyDietViewHolder;

import java.util.List;

public class MyTDietAdapter extends RecyclerView.Adapter<MyTDietViewHolder>{
    private Context context;
    private List<DietResponseDTO> itemList;

    public MyTDietAdapter(Context context, List<DietResponseDTO> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyTDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietcalBinding binding = ItemDietcalBinding.inflate(layoutInflater, parent, false);
        return new MyTDietViewHolder(ItemDietcalBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTDietViewHolder holder, int position) {
        DietResponseDTO data = itemList.get(position);

        holder.binding.itemTypeView.setText(data.getType());
        holder.binding.itemDateView.setText(data.getDate());
        holder.binding.itemWeekView.setText(data.getWeek());
        
        // menus 데이터를 적절한 문자열 형식으로 변환하여 표시
        StringBuilder menusText = new StringBuilder();
        for (MenuResponseDTO menu : data.getMenus()) {
            menusText.append(menu.getFoodName()).append("\n");
            // 필요한 경우 다른 필드도 추가
        }
        holder.binding.itemMenusView.setText(menusText.toString());

        holder.binding.itemMenusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", data.getType());
                bundle.putString("date", data.getDate());
                bundle.putString("week", data.getWeek());
                // menus 데이터를 적절한 문자열 형식으로 변환하여 Bundle에 추가
                StringBuilder menusText = new StringBuilder();
                for (MenuResponseDTO menu : data.getMenus()) {
                    menusText.append(menu.getFoodName()).append("\n");
                    // 필요한 경우 다른 필드도 추가
                }
                bundle.putString("menus", menusText.toString());

                Intent intent = new Intent(context, DietCalFragment.class);
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

    public void updateItems(List<DietResponseDTO> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}
