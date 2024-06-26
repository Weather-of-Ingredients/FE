package com.nutangel.woi_fe.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nutangel.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;
import com.nutangel.woi_fe.Retrofit.repository.DietRepository;
import com.nutangel.woi_fe.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DietRepository dietRepository;
    private MyDietAdapter adapter;
    private DietRetrofitAPI dietRetrofitAPI;
    private String selectedDate = "0";
    private Calendar calendar;

    //
//    private final String TAG = getClass().getSimpleName();
//    private Context mContext;
//
//    private int pageIndex = 0;
//    private Date currentDate;
//
//    private TextView calendarYearMonthText;
//    private LinearLayout calendarLayout;
//    private RecyclerView calendarView;
//    private CalendarAdapter calendarAdapter;
//
//    private static HomeFragment instance;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof MainActivity) {
//            mContext = context;
//        }
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dietRepository = new DietRepository(requireContext());

        Retrofit retrofit = RetrofitClient.getInstance(requireContext());
        dietRetrofitAPI = retrofit.create(DietRetrofitAPI.class);

        // Context를 가져옴
        Context context = requireContext();

        // itemList 생성
        List<DietResponseDTO> itemList = new ArrayList<>();

        // 어댑터 생성
        MyDietAdapter adapter = new MyDietAdapter(context, itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.feedRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyDietAdapter(requireContext(), new ArrayList<>());
        binding.feedRecyclerView.setAdapter(adapter);

//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext());
//        binding.calendarView.setLayoutManager(layoutManager2);
//        calendarAdapter = new CalendarAdapter(requireContext(), binding.calendarLayout, currentDate);
//        binding.calendarView.setAdapter(calendarAdapter);
//        initView(calendarView);

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        binding.cropPredSetYear.setText(year + "년");
        binding.cropPredSetMonth.setText(month + "월");

        // 캘린더뷰
        binding.calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
                loadDietList(selectedDate);
            }
        });

        return root;
    }

    private void loadDietList(String date) {
        Call<List<DietResponseDTO>> call = dietRetrofitAPI.getDietByUserAndDate(date);

        call.enqueue(new Callback<List<DietResponseDTO>>() {
            @Override
            public void onResponse(Call<List<DietResponseDTO>> call, Response<List<DietResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DietResponseDTO> diets = response.body();
                    adapter = new MyDietAdapter(getContext(), diets);
                    binding.feedRecyclerView.setAdapter(adapter);
                } else {
                    Log.e("HomeFragment", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<DietResponseDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}