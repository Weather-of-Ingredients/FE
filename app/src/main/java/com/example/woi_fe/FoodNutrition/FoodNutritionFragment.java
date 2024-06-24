package com.example.woi_fe.FoodNutrition;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.dto.foodNutrition.NutritionDTO;
import com.example.woi_fe.Retrofit.repository.FoodNutritionRepository;
import com.example.woi_fe.databinding.FragmentFoodNutritionBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.collections.UCollectionsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodNutritionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodNutritionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Calendar calendar;

    private FragmentFoodNutritionBinding binding;

    private FoodNutritionRepository foodNutritionRepository;
    private double getCarbohydrate, getProtein, getFat = 0;
    public FoodNutritionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodNutritionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodNutritionFragment newInstance(String param1, String param2) {
        FoodNutritionFragment fragment = new FoodNutritionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFoodNutritionBinding.inflate(inflater, container, false);
        foodNutritionRepository = new FoodNutritionRepository(requireContext());

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        binding.foodNutritionSetYear.setText(year + "년");
        binding.foodNutritionSetMonth.setText(month + "월");

        setButtonClickListener();

        callRetrofit(year, month);
        setPieChart();
        setBinding();

        return binding.getRoot();
    }

    private void setBinding() {
        binding.fnCarbohydrateNextData.setText(String.valueOf(getCarbohydrate));
        binding.fnFatNextData.setText(String.valueOf(getFat));
        binding.fnProteinNextData.setText(String.valueOf(getProtein));
    }

    private void callRetrofit(int year, int month) {
        Log.e("MainActivity", "영양성분 레트로핏 연결");
        foodNutritionRepository.getCarbohydrate(year, month).enqueue(new Callback<NutritionDTO>() {
            @Override
            public void onResponse(Call<NutritionDTO> call, Response<NutritionDTO> response) {
                Log.e("MainActivity", "탄수화물 레트로핏 연결");
                if(response.isSuccessful()){
                    Log.e("MainActivity", "탄수화물 isSuccessful");
                    if(response.body() != null){
                        Log.e("MainActivity", "탄수화물 body not null");
                        NutritionDTO nutritionDTO = response.body();
                        getCarbohydrate = nutritionDTO.getNutrition();
                    }else{

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<NutritionDTO> call, Throwable t) {
                Log.e("MainActivity", "탄수화물 GET request failed", t);
            }
        });

        foodNutritionRepository.getProtein(year, month).enqueue(new Callback<NutritionDTO>() {
            @Override
            public void onResponse(Call<NutritionDTO> call, Response<NutritionDTO> response) {
                Log.e("MainActivity", "단백질 레트로핏 연결");
                if(response.isSuccessful()){
                    Log.e("MainActivity", "단백질 isSuccessful");
                    if(response.body() != null){
                        Log.e("MainActivity", "단백질 body not null");
                        NutritionDTO nutritionDTO = response.body();
                        getProtein = nutritionDTO.getNutrition();
                    }else{

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<NutritionDTO> call, Throwable t) {
                Log.e("MainActivity", "단백질 GET request failed", t);
            }
        });

        foodNutritionRepository.getFat(year, month).enqueue(new Callback<NutritionDTO>() {
            @Override
            public void onResponse(Call<NutritionDTO> call, Response<NutritionDTO> response) {
                Log.e("MainActivity", "지방 레트로핏 연결");
                if(response.isSuccessful()){
                    Log.e("MainActivity", "지방 isSuccessful");
                    if(response.body() != null){
                        Log.e("MainActivity", "지방 body not null");
                        NutritionDTO nutritionDTO = response.body();
                        getFat = nutritionDTO.getNutrition();
                    }else{

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<NutritionDTO> call, Throwable t) {
                Log.e("MainActivity", "지방 GET request failed", t);
            }
        });
    }

    private void setPieChart(){
        binding.graph.setUsePercentValues(true);

        List<PieEntry> dataList = new ArrayList<>();
        /*추후 데베 연결*/
        dataList.add(new PieEntry((float)getCarbohydrate, "탄수화물"));
        dataList.add(new PieEntry((float)getFat, "지방"));
        dataList.add(new PieEntry((float) getProtein, "단백질"));

        /*색깔 지정*/
        PieDataSet dataSet = new PieDataSet(dataList, "");
        List<Integer> colors = new ArrayList<>();

        colors.add(ContextCompat.getColor(requireContext(), R.color.pink));
        colors.add(ContextCompat.getColor(requireContext(), R.color.green));
        colors.add(ContextCompat.getColor(requireContext(), R.color.pale_brown));

        dataSet.setColors(colors);
        /*텍스트 지우기*/
        dataSet.setDrawValues(false);


        PieData pieData = new PieData(dataSet);
        binding.graph.setData(pieData);
        binding.graph.getDescription().setEnabled(false);
        binding.graph.getLegend().setEnabled(false);
        binding.graph.setEntryLabelColor(R.color.black);



    }
    private void setButtonClickListener() {
        binding.foodNutritionSetMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭 시 datapickter 등장
                binding.foodNutritionOpacityLayout.setVisibility(View.VISIBLE);
                binding.foodNutritionDatepickerLayout.setVisibility(View.VISIBLE);
                //날짜 범위 제한
                setDateInit();
                //완료 버튼 선택 시
                setCompleteButtonClickListener();
            }
        });
    }
    private void setCompleteButtonClickListener() {
        binding.foodNutritionCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //날짜 얻어오기
                int setYear = binding.foodNutritionSetDatepicker.getYear();
                int setMonth = binding.foodNutritionSetDatepicker.getMonth() + 1;
                //뷰를 다시 닫기 visible.GONE
                binding.foodNutritionOpacityLayout.setVisibility(View.GONE);
                binding.foodNutritionDatepickerLayout.setVisibility(View.GONE);
                //날짜 수정하기
                binding.foodNutritionSetYear.setText(setYear + "년");
                binding.foodNutritionSetMonth.setText(setMonth + "월");
                callRetrofit(setYear, setMonth);
            }
        });


    }

    private void setDateInit() {
        //현재 날짜
        Calendar maxMonth = Calendar.getInstance();

        //이번 달에 3을 더하여 설정.
        maxMonth.add(Calendar.MONTH, 3);

        // DatePicker의 최대 날짜를 다음 달의 마지막 날로 설정
        binding.foodNutritionSetDatepicker.setMaxDate(maxMonth.getTimeInMillis());

    }

}