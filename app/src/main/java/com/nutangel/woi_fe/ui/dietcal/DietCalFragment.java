package com.nutangel.woi_fe.ui.dietcal;

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


import com.nutangel.woi_fe.MainActivity;

import com.nutangel.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;

import com.nutangel.woi_fe.Retrofit.repository.DietRepository;
import com.nutangel.woi_fe.databinding.FragmentDietcalBinding;
import com.nutangel.woi_fe.ui.home.MyDietAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private Calendar calendar;

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

        // 일단 현재 날짜를 가져옴
        Calendar calendar = Calendar.getInstance();

        // 날짜 형식 지정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // selectedDate 업데이트
        String selectedDate = dateFormat.format(calendar.getTime());

        loadTDietList(selectedDate);
        setDate();


        binding.dietCalGotoCropList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 프래그먼트 매니저 객체 가져오기
                CropListFragment fragment = new CropListFragment();
                ((MainActivity)getActivity()).loadFragment(fragment);

            }
        });
        return root;
    }

    private void loadTDietList(String date) {
        Call<List<DietResponseDTO>> call = dietRetrofitAPI.getDietByUserAndDate(date);

        call.enqueue(new Callback<List<DietResponseDTO>>() {
            @Override
            public void onResponse(Call<List<DietResponseDTO>> call, Response<List<DietResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DietResponseDTO> diets = response.body();
                    adapter = new MyTDietAdapter(getContext(), diets);
                    binding.feedRecyclerView.setAdapter(adapter);
                    binding.yetNoDiet.setVisibility(View.GONE);
                    if(response.body().size() == 0){
                        binding.yetNoDiet.setVisibility(View.VISIBLE);
                    }
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

//    private void loadTDietList() {
//        Call<List<DietResponseDTO>> call = dietRetrofitAPI.getDietByUserAndToday();
//        call.enqueue(new Callback<List<DietResponseDTO>>() {
//            @Override
//            public void onResponse(Call<List<DietResponseDTO>> call, Response<List<DietResponseDTO>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<DietResponseDTO> diets = response.body();
//                    adapter = new MyTDietAdapter(getContext(), diets);
//                    binding.feedRecyclerView.setAdapter(adapter);
//                    binding.yetNoDiet.setVisibility(View.GONE);
//                    if(response.body().size() == 0){
//                        binding.yetNoDiet.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    binding.yetNoDiet.setVisibility(View.VISIBLE);
//                    Log.e("DietCalFragment", "Response not successful or body is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DietResponseDTO>> call, Throwable t) {
//
//            }
//        });
//    }

    private void setDate(){
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;
        int date = calendar.get(Calendar.DATE);

        String monthString = (month < 10) ? "0" + month : String.valueOf(month);
        String dateString = (date < 10) ? "0" + date : String.valueOf(date);

        binding.date.setText(year + "-" + monthString + "-" + dateString);
    }
}
