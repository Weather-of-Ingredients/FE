package com.example.woi_fe.ui.dietcal;


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
import com.example.woi_fe.databinding.ItemDietcalBinding;
import com.example.woi_fe.ui.Diet.DietDetailActivity;
import com.example.woi_fe.ui.Diet.DietUpdateActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyTDietAdapter extends RecyclerView.Adapter<MyTDietViewHolder>{
    private Context context;
    private List<DietResponseDTO> itemList;

    public MyTDietAdapter(Context context, List<DietResponseDTO> itemList){
        this.context = context;
        this.itemList = itemList;
        sortItemList(); // 아이템 리스트를 생성자에서 정렬
    }

    @NonNull
    @Override
    public MyTDietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyTDietAdapter", "MyTDietAdapter 어댑터연결 성공");
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
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", data.getType());
                bundle.putString("date", data.getDate());
                bundle.putString("week", data.getWeek());
                bundle.putInt("dietId", data.getDietId());

                // 메뉴 목록을 MenuResponseDTO 변환
                ArrayList<String> menuList = new ArrayList<>();
                for(MenuResponseDTO menu : data.getMenus()){
                    menuList.add(menu.getFoodName());
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
        sortItemList(); // 아이템 리스트를 생성자에서 정렬
        notifyDataSetChanged();
    }

    private void sortItemList() {
        Collections.sort(itemList, new Comparator<DietResponseDTO>() {
            @Override
            public int compare(DietResponseDTO o1, DietResponseDTO o2) {
                // 조식-중식-석식 순으로 정렬
                return getOrder(o1.getType()) - getOrder(o2.getType());
            }

            private int getOrder(String type) {
                switch (type) {
                    case "조식": return 1;
                    case "중식": return 2;
                    case "석식": return 3;
                    default: return 4;
                }
            }
        });
    }
}
