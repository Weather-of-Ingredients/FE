package com.nutangel.woi_fe.ui.Diet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nutangel.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.nutangel.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.nutangel.woi_fe.Retrofit.dto.diet.MenuParDTO;
import com.nutangel.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;
import com.nutangel.woi_fe.Retrofit.repository.DietRepository;
import com.nutangel.woi_fe.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuSearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private DietRetrofitAPI dietRetrofitAPI;
    private DietRepository dietRepository;
    private MyMenuAdapter adapter;
    List<MenuResponseDTO> menuResponseDTOS;
    List<MenuDTO> menuDTOList;
    String type, date, week,cu;
    Integer dietId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dietRepository = new DietRepository(this);

        Retrofit retrofit = RetrofitClient.getInstance(this);
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);

        // From DietCreateActivity, DietUpdateActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            date = bundle.getString("date");
            type = bundle.getString("type");
            week = bundle.getString("week");
            dietId = bundle.getInt("dietId");
            cu = bundle.getString("cu");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MenuSearchActivity.this);
        binding.feedRecyclerView.setLayoutManager(layoutManager);

        binding.bSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadMenuList();
            }
        });

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MenuDTO> newSelectedItems = adapter.getSelectedItems(); // 새로 선택된 메뉴 리스트

                Bundle bundle = new Bundle();
                List<MenuParDTO> menuParDTOList = new ArrayList<>();
                for (MenuDTO menuDTO : newSelectedItems) {
                    MenuParDTO menuParDTO = new MenuParDTO();
                    menuParDTO.setMenuId(menuDTO.getMenuId());
                    menuParDTO.setCarbohydrate(menuDTO.getCarbohydrate());
                    menuParDTO.setProtein(menuDTO.getProtein());
                    menuParDTO.setFat(menuDTO.getFat());
                    menuParDTO.setFoodName(menuDTO.getFoodName());
                    menuParDTO.setCalories(menuDTO.getCalories());
                    menuParDTO.setDiet(menuDTO.getDiet());
                    menuParDTOList.add(menuParDTO);
                }
                ArrayList<MenuParDTO> parcelableMenuList = new ArrayList<>(menuParDTOList);

                bundle.putString("type", type);
                bundle.putString("date", date);
                bundle.putString("week", week);
                bundle.putParcelableArrayList("newMenus", parcelableMenuList);
                bundle.putInt("dietId", dietId);

                if(cu.equals("create")){
                    Intent intent2 = new Intent(MenuSearchActivity.this, DietCreateActivity.class);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                } else {
                    Intent intent = new Intent(MenuSearchActivity.this, DietUpdateActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                finish();
            }
        });

    }

    private void loadMenuList() {
        // 수정할 때 원래 식단의 메뉴를 가져오는 메소드
        Call<DietResponseDTO> call = dietRetrofitAPI.getDietByDietId(dietId);
        call.enqueue(new Callback<DietResponseDTO>() {
            @Override
            public void onResponse(Call<DietResponseDTO> call, Response<DietResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null){
//                    binding.dietDate.setText(response.body().getDate());
//                    binding.dietType.setText(response.body().getType());
//                    binding.dietWeek.setText(response.body().getWeek());
                    menuResponseDTOS = response.body().getMenus();
                    menuDTOList = convertToMenuDTOList(menuResponseDTOS);
                }
            }
            @Override
            public void onFailure(Call<DietResponseDTO> call, Throwable t) {
                Log.e("MenuSearchActivity", "Failed to load diet by ID", t);
            }
        });

        // 원래 식단 메뉴를 가져와 어댑터 실행시키는 메소드
        Call<List<MenuDTO>> call2 = dietRetrofitAPI.getMenuList(binding.eSearchWord.getText().toString());
        call2.enqueue(new Callback<List<MenuDTO>>() {
            @Override
            public void onResponse(Call<List<MenuDTO>> call, Response<List<MenuDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuDTO> menus = response.body();
                    if(menuDTOList == null){
                        adapter = new MyMenuAdapter(MenuSearchActivity.this, menus, new ArrayList<>());
                        binding.feedRecyclerView.setAdapter(adapter);
                    } else {
                        adapter = new MyMenuAdapter(MenuSearchActivity.this, menus, menuDTOList);
                        binding.feedRecyclerView.setAdapter(adapter);
                    }
//                    adapter = new MyMenuAdapter(MenuSearchActivity.this, menus, menuDTOList);
//                    binding.feedRecyclerView.setAdapter(adapter);
                } else {
                    Log.e("SearchActivity", "Response not successful or body is null");
                }
            }
            @Override
            public void onFailure(Call<List<MenuDTO>> call, Throwable t) {

            }
        });
    }

    public static MenuResponseDTO convertToMenuResponseDTO(MenuDTO menuDTO) {
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setFoodName(menuDTO.getFoodName());
         menuResponseDTO.setCalories(menuDTO.getCalories());
         menuResponseDTO.setFat(menuDTO.getFat());
         menuResponseDTO.setCarbohydrate(menuDTO.getCarbohydrate());
         menuResponseDTO.setProtein(menuDTO.getProtein());
        return menuResponseDTO;
    }

    public static MenuDTO convertToMenuDTO(MenuResponseDTO menuResponseDTO) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setFoodName(menuResponseDTO.getFoodName());
        menuDTO.setCalories(menuResponseDTO.getCalories());
        menuDTO.setFat(menuResponseDTO.getFat());
        menuDTO.setCarbohydrate(menuResponseDTO.getCarbohydrate());
        menuDTO.setProtein(menuResponseDTO.getProtein());
        return menuDTO;
    }

    public static List<MenuResponseDTO> convertToMenuResponseDTOList(List<MenuDTO> menuDTOList) {
        List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOList) {
            menuResponseDTOList.add(convertToMenuResponseDTO(menuDTO));
        }
        return menuResponseDTOList;
    }

    public static List<MenuDTO> convertToMenuDTOList(List<MenuResponseDTO> menuResponseDTOList) {
        List<MenuDTO> menuDTOList = new ArrayList<>();
        for (MenuResponseDTO menuResponseDTO : menuResponseDTOList) {
            menuDTOList.add(convertToMenuDTO(menuResponseDTO));
        }
        return menuDTOList;
    }
}
