package com.example.woi_fe.ui.dietcal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.Retrofit.repository.DietRepository;
import com.example.woi_fe.databinding.FragmentDietcalBinding;
import com.example.woi_fe.ui.home.MyDietAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DietCalFragment extends Fragment {

    private FragmentDietcalBinding binding;
    private DietRepository dietRepository;
    private MyTDietAdapter adapter;
    private DietRetrofitAPI dietRetrofitAPI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDietcalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dietRepository = new DietRepository(requireContext());

        Retrofit retrofit = RetrofitClient.getInstance(requireContext());
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);

        Context context = requireContext();
        List<DietResponseDTO> itemList = new ArrayList<>();
        MyTDietAdapter adapter = new MyTDietAdapter(context, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.feedRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyTDietAdapter(requireContext(), new ArrayList<>());
        binding.feedRecyclerView.setAdapter(adapter);

        loadTDietList();

        return root;
    }

    private void loadTDietList() {
        Call<List<DietResponseDTO>> call = dietRetrofitAPI.getDietByUserAndToday();
        call.enqueue(new Callback<List<DietResponseDTO>>() {
            @Override
            public void onResponse(Call<List<DietResponseDTO>> call, Response<List<DietResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DietResponseDTO> diets = response.body();
                    adapter = new MyTDietAdapter(getContext(), diets);
                    binding.feedRecyclerView.setAdapter(adapter);
                    binding.yetNoDiet.setVisibility(View.GONE);
                } else {
                    binding.yetNoDiet.setVisibility(View.VISIBLE);
                    Log.e("DietCalFragment", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<DietResponseDTO>> call, Throwable t) {

            }
        });
    }
}
