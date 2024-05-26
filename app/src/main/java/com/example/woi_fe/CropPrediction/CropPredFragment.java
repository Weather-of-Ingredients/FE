package com.example.woi_fe.CropPrediction;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.R;
import com.example.woi_fe.databinding.CropPredBottomSheetBinding;
import com.example.woi_fe.databinding.FragmentCropPredBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CropPredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CropPredFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentCropPredBinding binding;

    private Calendar calendar;

    public CropPredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CropPredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CropPredFragment newInstance(String param1, String param2) {
        CropPredFragment fragment = new CropPredFragment();
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
        binding = FragmentCropPredBinding.inflate(inflater, container, false);

        calendar = Calendar.getInstance();
        binding.cropPredSetYear.setText(calendar.get(Calendar.YEAR) + "년");
        binding.cropPredSetMonth.setText(calendar.get(Calendar.MONTH) +1 + "월");

        binding.monthText.setText(calendar.get(Calendar.MONTH) +1 + "월");

        binding.originalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomSheet();
            }
        });
        //달력 버튼 선택
        setButtonClickListener();

        return binding.getRoot();
    }

    private void ShowBottomSheet() {
        CropPredBottomSheetBinding bottomSheetBinding = CropPredBottomSheetBinding.inflate(getLayoutInflater());
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(bottomSheetBinding.getRoot());

        dialog.setCanceledOnTouchOutside(true);
        dialog.create();
        dialog.show();
    }

    private void setButtonClickListener() {
        binding.cropPredSetMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭 시 datapickter 등장
                binding.cropPredOpacityLayout.setVisibility(View.VISIBLE);
                binding.cropPredDatepickerLayout.setVisibility(View.VISIBLE);
                //날짜 범위 제한
                setDateInit();
                //완료 버튼 선택 시
                setCompleteButtonClickListener();
            }
        });
    }

    private void setCompleteButtonClickListener() {
        binding.cropPredCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //날짜 얻어오기
                int setYear = binding.cropPredSetDatepicker.getYear();
                int setMonth = binding.cropPredSetDatepicker.getMonth() + 1;
                //뷰를 다시 닫기 visible.GONE
                binding.cropPredOpacityLayout.setVisibility(View.GONE);
                binding.cropPredDatepickerLayout.setVisibility(View.GONE);
                //날짜 수정하기
                binding.cropPredSetYear.setText(setYear + "년");
                binding.cropPredSetMonth.setText(setMonth + "월");
                binding.monthText.setText(setMonth + "월");
            }
        });
    }

    private void setDateInit() {
        //현재 날짜
        Calendar maxMonth = Calendar.getInstance();

        //이번 달에 3을 더하여 설정.
        maxMonth.add(Calendar.MONTH, 3);

        // DatePicker의 최대 날짜를 다음 달의 마지막 날로 설정
        binding.cropPredSetDatepicker.setMaxDate(maxMonth.getTimeInMillis());

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}