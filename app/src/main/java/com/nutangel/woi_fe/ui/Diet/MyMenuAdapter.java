package com.nutangel.woi_fe.ui.Diet;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.nutangel.woi_fe.databinding.ItemMenuBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuViewHolder>{
    private Context context;
    private List<MenuDTO> menuList, itemList;
    private List<MenuDTO> selectedList = new ArrayList<>();
    private Map<MenuDTO, Boolean> selectionMap = new HashMap<>();

    public MyMenuAdapter(Context context, List<MenuDTO> itemList, List<MenuDTO> menuList){
        this.context = context;
        this.itemList = itemList; // 검색해서 나오는 메뉴 리스트
        this.menuList = menuList; // 업데이트 할 떄 원래 가지고있던 메뉴 리스트
        // 초기화 시 선택 상태 맵과 선택 리스트를 설정
        for (MenuDTO item : menuList) {
            selectionMap.put(item, true);
            selectedList.add(item);
        }
    }

    @NonNull
    @Override
    public MyMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyMenuAdapter", "MyMenuAdapter 어댑터연결 성공");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMenuBinding binding = ItemMenuBinding.inflate(layoutInflater, parent, false);
        return new MyMenuViewHolder(binding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyMenuViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MenuDTO data = itemList.get(position);

        holder.binding.itemFoodNameView.setText(data.getFoodName());
        holder.binding.itemCalView.setText(String.format("%.2f", data.getCalories()));
        holder.binding.itemCarView.setText(String.format("%.2f", data.getFat()));
        holder.binding.itemFatView.setText(String.format("%.2f", data.getCarbohydrate()));
        holder.binding.itemProView.setText(String.format("%.2f", data.getProtein()));

        // 선택된 상태인지 확인
        boolean isSelected = selectionMap.getOrDefault(data, false);
        holder.binding.menuCardView.setBackgroundColor(isSelected ? Color.LTGRAY : Color.WHITE);

        holder.binding.itemFoodNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    selectedList.remove(data);
                    selectionMap.put(data, false);
                } else {
                    selectedList.add(data);
                    selectionMap.put(data, true);
                }
                notifyItemChanged(position); // 아이템 갱신
                Log.d("SelectedMenus", selectedList.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // 원래 메뉴 리스트 반환 메서드
    public List<MenuDTO> getOriginalItems() {
        return menuList;
    }

    // 선택된 객체 리스트 반환 메서드
    public List<MenuDTO> getSelectedItems() {
        return selectedList;
    }
}
