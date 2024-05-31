package com.example.woi_fe.CropPrediction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.example.woi_fe.Retrofit.repository.RecommendationRepository;
import com.example.woi_fe.databinding.CropPredBottomSheetBinding;
import com.example.woi_fe.databinding.FragmentCropPredBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private int itemSize = 0;
    private List<CropItem> cropItems = null;

    private RecommendationRepository recommendationRepository;
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

        //retrofit 연결
        recommendationRepository = new RecommendationRepository();

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        binding.cropPredSetYear.setText(year + "년");
        binding.cropPredSetMonth.setText(month + "월");
        binding.monthText.setText(month + "월");

        //bad_crops list 보여주기
        callRetrofit(year, month);

        setItemClickListener();

        //상세 메뉴 창 연결
        setDetailClickListener();
        //달력 버튼 선택
        setButtonClickListener();

        return binding.getRoot();
    }

    private void callRetrofit(int year, int month) {
        recommendationRepository.getCropItems(year, month, "bad_crops").enqueue(new Callback<CropResponseDTO<List<CropItem>>>() {
            @Override
            public void onResponse(Call<CropResponseDTO<List<CropItem>>> call, Response<CropResponseDTO<List<CropItem>>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        itemSize = 0;
                        CropResponseDTO<List<CropItem>> ResponseCropItems = response.body();
                        cropItems = ResponseCropItems.getData();

                        if(!cropItems.isEmpty()){
                            binding.prevBtn.setVisibility(View.INVISIBLE);
                            binding.cropId.setVisibility(View.VISIBLE);
                            binding.cropId.setText(cropItems.get(0).getIngredient_name());
                            if(cropItems.size() > 1){
                                binding.nextBtn.setVisibility(View.VISIBLE);
                                binding.prevBtn.setVisibility(View.INVISIBLE);
                            }
                        }else{
                            binding.nextBtn.setVisibility(View.INVISIBLE);
                            binding.prevBtn.setVisibility(View.INVISIBLE);
                            binding.cropId.setVisibility(View.INVISIBLE);
                            binding.cropId.setText("");
                        }

                    }else{
                        Log.e("MainActivity", "response.body()==null");
                    }
                }else{
                    Log.e("MainActivity", "response.isSuccessful()에서 오류");
                }
            }

            @Override
            public void onFailure(Call<CropResponseDTO<List<CropItem>>> call, Throwable t) {
                Log.e("MainActivity", "GET request failed", t);
            }


        });
    }


    private void setCropListener(){
        if((!cropItems.isEmpty()) && cropItems.size() > 1){
            if(itemSize == 0){
                binding.nextBtn.setVisibility(View.VISIBLE);
                binding.prevBtn.setVisibility(View.INVISIBLE);
            }else if(1 <= itemSize && itemSize < cropItems.size() -1){
                binding.nextBtn.setVisibility(View.VISIBLE);
                binding.prevBtn.setVisibility(View.VISIBLE);
            }else if(itemSize == cropItems.size() -1){
                binding.nextBtn.setVisibility(View.INVISIBLE);
                binding.prevBtn.setVisibility(View.VISIBLE);

            }else{
                System.out.println("사이즈 설정 오류");
            }
        }else{

        }
    }
    private void setItemClickListener() {
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSize++;
                binding.cropId.setText(cropItems.get(itemSize).getIngredient_name());
                setCropListener();
            }
        });
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSize--;
                binding.cropId.setText(cropItems.get(itemSize).getIngredient_name());
                setCropListener();
            }
        });

    }

    private void setDetailClickListener() {
        binding.originalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomSheet();
            }
        });
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
        binding.cropPredSetDatepicker.setMaxDate(maxMonth.getTimeInMillis());

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}