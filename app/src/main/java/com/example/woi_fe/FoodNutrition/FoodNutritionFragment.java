package com.example.woi_fe.FoodNutrition;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.Dialog.CalendarDialog;
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
        // 이전 데이터 설정
        binding.fnCarbohydratePrevData.setText(String.format("%.2f", getPrevCarbohydrate));
        binding.fnFatPrevData.setText(String.format("%.2f", getPrevFat));
        binding.fnProteinPrevData.setText(String.format("%.2f", getPrevProtein));

        // 다음 데이터 설정
        binding.fnCarbohydrateNextData.setText(String.format("%.2f", getCarbohydrate));
        binding.fnFatNextData.setText(String.format("%.2f", getFat));
        binding.fnProteinNextData.setText(String.format("%.2f", getProtein));
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
                CalendarDialog calendarDialog = new CalendarDialog(requireContext());
                calendarDialog.show();
                calendarDialog.setCalendarDialogCallbackListener(new CalendarDialog.CalendarDialogCallbackListener() {
                    @Override
                    public void dialogCallbackListener(int Year, int Month) {
                        callRetrofit(Year, Month);
                        binding.foodNutritionSetYear.setText(Year + "년");
                        binding.foodNutritionSetMonth.setText(Month + "월");
                    }
                });
            }
        });
    }
}