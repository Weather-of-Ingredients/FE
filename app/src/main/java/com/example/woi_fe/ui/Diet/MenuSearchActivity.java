package com.example.woi_fe.ui.Diet;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.Retrofit.repository.DietRepository;
import com.example.woi_fe.databinding.ActivitySearchBinding;
import com.example.woi_fe.ui.home.MyDietAdapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dietRepository = new DietRepository(this);

        Retrofit retrofit = RetrofitClient.getInstance(this);
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);

        List<MenuDTO> itemList = new ArrayList<>();
        MyMenuAdapter adapter = new MyMenuAdapter(this, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.feedRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyMenuAdapter(this, new ArrayList<>());
        binding.feedRecyclerView.setAdapter(adapter);

        binding.bSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadMenuList();
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
}
