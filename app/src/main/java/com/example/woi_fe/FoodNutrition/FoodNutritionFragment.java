package com.example.woi_fe.FoodNutrition;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.repository.FoodNutritionRepository;
import com.example.woi_fe.databinding.FragmentFoodNutritionBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private double getPrevCarbohydrate, getPrevProtein, getPrevFat = 0;
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

        return binding.getRoot();
    }

    private void setBinding() {
        binding.fnCarbohydratePrevData.setText(String.valueOf(getPrevCarbohydrate));
        binding.fnFatPrevData.setText(String.valueOf(getPrevFat));
        binding.fnProteinPrevData.setText(String.valueOf(getPrevProtein));

        binding.fnCarbohydrateNextData.setText(String.valueOf(getCarbohydrate));
        binding.fnFatNextData.setText(String.valueOf(getFat));
        binding.fnProteinNextData.setText(String.valueOf(getProtein));
    }

    private void callRetrofit(int year, int month) {
        getCarbohydrate = 0;
        getProtein = 0;
        getFat = 0;

        getPrevCarbohydrate = 0;
        getPrevProtein = 0;
        getPrevFat = 0;

        callNutritionData(year, month, true);

        if(month == 1){
            callNutritionData(year - 1, 12, false);
        }else{
            callNutritionData(year, month -1, false);
        }

    }

    private void callNutritionData(int year, int month, boolean b) {
        foodNutritionRepository.getCarbohydrate(year, month).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful() && response.body() != null){
                    double nutritionDTO = response.body();
                    if(b){
                        getCarbohydrate = nutritionDTO;
                    }else{
                        getPrevCarbohydrate = nutritionDTO;
                    }
                    checkAndSetPieChart();
                } else{
                    checkAndSetPieChart();
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                checkAndSetPieChart();
            }
        });

        foodNutritionRepository.getProtein(year, month).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful() && response.body() != null){
                    double nutritionDTO = response.body();
                    if(b){
                        getProtein = nutritionDTO;
                    }else{
                        getPrevProtein = nutritionDTO;
                    }
                    checkAndSetPieChart();
                }else{
                    checkAndSetPieChart();
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                checkAndSetPieChart();
            }
        });

        foodNutritionRepository.getFat(year, month).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful() && response.body() != null){
                    double nutritionDTO = response.body();
                    if(b){
                        getFat = nutritionDTO;
                    }else{
                        getPrevFat = nutritionDTO;
                    }
                    checkAndSetPieChart();
                }else{
                    checkAndSetPieChart();
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                checkAndSetPieChart();
            }
        });
    }

    private void checkAndSetPieChart() {
        if (getCarbohydrate > 0 || getProtein > 0 || getFat > 0) {
            setPieChart();
        } else {
            clearPieChart();
        }

        setBinding();
    }

    private void setPieChart(){
        binding.graphText.setVisibility(View.GONE);
        binding.graph.setVisibility(View.VISIBLE);
        binding.graph.setUsePercentValues(true);
        binding.graph.setNoDataText("");

        List<PieEntry> dataList = new ArrayList<>();
        dataList.add(new PieEntry((float)getCarbohydrate, "탄수화물"));
        dataList.add(new PieEntry((float)getFat, "지방"));
        dataList.add(new PieEntry((float) getProtein, "단백질"));

        PieDataSet dataSet = new PieDataSet(dataList, "");
        List<Integer> colors = new ArrayList<>();

        colors.add(ContextCompat.getColor(requireContext(), R.color.pink));
        colors.add(ContextCompat.getColor(requireContext(), R.color.green));
        colors.add(ContextCompat.getColor(requireContext(), R.color.pale_brown));

        dataSet.setColors(colors);

        dataSet.setDrawValues(true);


        PieData pieData = new PieData(dataSet);
        binding.graph.setData(pieData);
        binding.graph.invalidate();
        binding.graph.getDescription().setEnabled(false);
        binding.graph.getLegend().setEnabled(false);
        binding.graph.setEntryLabelColor(R.color.black);




    }
    private void clearPieChart() {
        binding.graph.clear();
        binding.graph.setVisibility(View.GONE);
        binding.graphText.setVisibility(View.VISIBLE);

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