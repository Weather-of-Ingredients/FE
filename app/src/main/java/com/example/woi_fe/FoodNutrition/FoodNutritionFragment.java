package com.example.woi_fe.FoodNutrition;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.R;
import com.example.woi_fe.databinding.FragmentFoodNutritionBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.UCollectionsKt;

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

    private FragmentFoodNutritionBinding binding;

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
        setPieChart();

        return binding.getRoot();
    }

    private void setPieChart(){
        binding.graph.setUsePercentValues(true);

        List<PieEntry> dataList = new ArrayList<>();
        /*추후 데베 연결*/
        dataList.add(new PieEntry(50F, "탄수화물"));
        dataList.add(new PieEntry(30F, "지방"));
        dataList.add(new PieEntry(20F, "단백질"));

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

}