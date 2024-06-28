package com.example.woi_fe.ui.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ItemDietBinding;
import com.example.woi_fe.ui.Diet.DietDetailActivity;
import com.example.woi_fe.ui.Diet.DietUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class MyDietAdapter extends RecyclerView.Adapter<MyDietViewHolder>{
    private Context context;
    private List<DietResponseDTO> itemList;

    public MyDietAdapter(Context context, List<DietResponseDTO> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyDietAdapter", "MyDietAdapter 어댑터연결 성공");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDietBinding binding = ItemDietBinding.inflate(layoutInflater, parent, false);
        return new MyDietViewHolder(ItemDietBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull MyDietViewHolder holder, int position) {
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
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", data.getType());
                bundle.putString("date", data.getDate());
                bundle.putString("week", data.getWeek());
                bundle.putInt("dietId", data.getDietId());
                Log.d("MyDietAdapter", data.getDietId().toString());

                // 메뉴 목록을 MenuResponseDTO 변환
                ArrayList<String> menuList = new ArrayList<>();
                for(MenuResponseDTO menu : data.getMenus()){
                    menuList.add(menu.getFoodName());
//                    menuList.add(String.format("%.2f",menu.getCalories()));
//                    menuList.add(String.format("%.2f",menu.getCarbohydrate()));
//                    menuList.add(String.format("%.2f",menu.getProtein()));
//                    menuList.add(String.format("%.2f",menu.getFat()));
                }
                bundle.putStringArrayList("menus", menuList);

                Intent intent = new Intent(context, DietDetailActivity.class);
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
