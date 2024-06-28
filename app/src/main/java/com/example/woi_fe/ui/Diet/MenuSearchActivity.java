package com.example.woi_fe.ui.Diet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuParDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.Retrofit.repository.DietRepository;
import com.example.woi_fe.databinding.ActivitySearchBinding;

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
    String type, date, week;
    Integer dietId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dietRepository = new DietRepository(this);

        Retrofit retrofit = RetrofitClient.getInstance(this);
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);

        // From DietCreateActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            date = bundle.getString("date");
            type = bundle.getString("type");
            week = bundle.getString("week");
            dietId = bundle.getInt("dietId");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MenuSearchActivity.this);
        binding.feedRecyclerView.setLayoutManager(layoutManager);

//        loadDietByID(dietId);

        binding.bSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadMenuList();
            }
        });

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<MenuDTO> newItems = adapter.getSelectedItems();
                List<MenuParDTO> menuParDTOList = new ArrayList<>();
                for (MenuDTO menuDTO : newItems) {
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

                Intent intent2 = new Intent(MenuSearchActivity.this, DietCreateActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);

                finish();
            }
        });

    }

    private void loadDietByID(Integer dietId){
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

                    adapter = new MyMenuAdapter(MenuSearchActivity.this, menuDTOList);
                    binding.feedRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DietResponseDTO> call, Throwable t) {
                Log.e("MenuSearchActivity", "Failed to load diet by ID", t);
            }
        });
    }

    private void loadMenuList() {
        Call<List<MenuDTO>> call = dietRetrofitAPI.getMenuList(binding.eSearchWord.getText().toString());
        call.enqueue(new Callback<List<MenuDTO>>() {
            @Override
            public void onResponse(Call<List<MenuDTO>> call, Response<List<MenuDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuDTO> menus = response.body();
                    adapter = new MyMenuAdapter(MenuSearchActivity.this, menus);
                    binding.feedRecyclerView.setAdapter(adapter);
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
