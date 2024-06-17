package com.example.woi_fe.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.MainActivity;
import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String selectedDate = "0";

    private DietRetrofitAPI dietRetrofitAPI;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Retrofit retrofit = RetrofitClient.getInstance();
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);


        // 바텀 시트
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
//        binding.textHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetDialog.show();
//            }
//        });
//        setupBottomSheet();
//
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // 캘린더뷰
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 일단 현재 날짜를 가져옴
                Calendar calendar = Calendar.getInstance();
                // 사용자가 선택한 날짜로 Calendar 객체를 업데이트
                calendar.set(year, month, dayOfMonth);

                // 날짜 형식 지정
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                // selectedDate 업데이트
                selectedDate = dateFormat.format(calendar.getTime());

                // 날짜를 하루 더함
                calendar.add(Calendar.DAY_OF_MONTH, 1);

                // 선택된 날짜에 대한 식단 목록 업데이트
                updateDiet();
            }
        });

        // Context를 가져옴
        Context context = requireContext();

        // itemList 생성
        List<DietDTO> itemList = new ArrayList<>();

        // 어댑터 생성
        MyDietAdapter adapter = new MyDietAdapter(context, itemList);

        // RecyclerView 설정
//        RecyclerView feedRecyclerView = getView().findViewById(R.id.feedRecyclerView);
//        feedRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        feedRecyclerView.setAdapter(adapter);

        return root;
    }

    private void setupBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_crop_pred, null);

//        binding.textHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetDialog.show();
//            }
//        });

        bottomSheetDialog.setContentView(view);
    }

    private void updateDiet(){

    }

    @Override
    public void onStart() {
        super.onStart();
        getDiet();
    }

    private void getDiet(){
        DietDTO dietDTO = new DietDTO();
        Call<List<DietDTO>> call = dietRetrofitAPI.getAllDiets();

        call.enqueue(new Callback<List<DietDTO>>() {
            @Override
            public void onResponse(Call<List<DietDTO>> call, Response<List<DietDTO>> response) {
                if(response.isSuccessful()){
                    try{
                        String responseString = response.body().toString();
                        JSONObject jsonResponse = new JSONObject(responseString);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<DietDTO>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}