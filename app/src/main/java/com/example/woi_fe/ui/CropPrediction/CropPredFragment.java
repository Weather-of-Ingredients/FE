package com.example.woi_fe.ui.CropPrediction;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woi_fe.Retrofit.dto.recommendation.BadCropMenuDTO;
import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.example.woi_fe.Retrofit.repository.RecommendationRepository;
import com.example.woi_fe.databinding.FragmentCropPredBinding;

import java.io.IOException;
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

    private BadCropMenuItemAdapter adapter;


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

        recommendationRepository = new RecommendationRepository(requireContext());

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        binding.cropPredSetYear.setText(year + "년");
        binding.cropPredSetMonth.setText(month + "월");
        binding.monthText.setText(month + "월");

        callRetrofit(year, month);

        setItemClickListener();

        //달력 버튼 선택
        setButtonClickListener();

        return binding.getRoot();
    }

    private void setBadCropMenu(List<BadCropMenuDTO> badCropMenuDTOList, Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.cropPredMenuList.setLayoutManager(layoutManager);

        adapter = new BadCropMenuItemAdapter(badCropMenuDTOList, context);
        binding.cropPredMenuList.setAdapter(adapter);
    }

    private void callRetrofit(int year, int month) {
        /*SharedPreferences sharedPreferences = requireContext().getSharedPreferences("WoI", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("jwtToken", null);*/
        /*if(!token.isEmpty()) {
        *
        * "Bearer " + token,
        * */
            recommendationRepository.getCropItems(year, month, "bad_crop").enqueue(new Callback<CropResponseDTO<List<CropItem>>>() {
                @Override
                public void onResponse(Call<CropResponseDTO<List<CropItem>>> call, Response<CropResponseDTO<List<CropItem>>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            itemSize = 0;
                            CropResponseDTO<List<CropItem>> ResponseCropItems = response.body();
                            cropItems = ResponseCropItems.getData();

                            if (cropItems != null && !cropItems.isEmpty()) {
                                binding.prevBtn.setVisibility(View.INVISIBLE);
                                binding.cropId.setVisibility(View.VISIBLE);

                                binding.cropId.setText(cropItems.get(0).getIngredient_name());
                                Glide.with(requireContext())
                                        .load(cropItems.get(0).getIngredient_image())
                                        .apply(RequestOptions.circleCropTransform())
                                        .override(134, 134)
                                        .into(binding.cropImage);
                                if (cropItems.size() > 1) {
                                    binding.nextBtn.setVisibility(View.VISIBLE);
                                    binding.prevBtn.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                handleEmptyCropItems();
                            }

                        } else {
                            handleEmptyCropItems();
                        }
                    } else {
                        try {
                            Log.e("MainActivity", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CropResponseDTO<List<CropItem>>> call, Throwable t) {
                }


            });

            recommendationRepository.getBadCropsMenus(year, month).enqueue(new Callback<List<BadCropMenuDTO>>() {
                @Override
                public void onResponse(Call<List<BadCropMenuDTO>> call, Response<List<BadCropMenuDTO>> response) {
                    if(response.isSuccessful() && response.body()!=null){

                        List<BadCropMenuDTO> badCropMenuDTOList = response.body();

                        if(badCropMenuDTOList != null && !badCropMenuDTOList.isEmpty()){

                            binding.cropPredMenuListText.setVisibility(View.GONE);
                            binding.cropPredMenuList.setVisibility(View.VISIBLE);
                            setBadCropMenu(badCropMenuDTOList, requireContext());
                        }else{
                            binding.cropPredMenuList.setVisibility(View.GONE);
                            binding.cropPredMenuListText.setVisibility(View.VISIBLE);

                        }
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<List<BadCropMenuDTO>> call, Throwable t) {
                }
            });
    }

    private void handleEmptyCropItems() {
        binding.nextBtn.setVisibility(View.INVISIBLE);
        binding.prevBtn.setVisibility(View.INVISIBLE);
        binding.cropId.setVisibility(View.INVISIBLE);
        binding.cropId.setText("");
        Glide.with(requireContext())
                .clear(binding.cropImage);
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
                Glide.with(requireContext())
                        .load(cropItems.get(itemSize).getIngredient_image())
                        .apply(RequestOptions.circleCropTransform())
                        .override(134, 134)
                        .into(binding.cropImage);
                setCropListener();
            }
        });
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSize--;
                binding.cropId.setText(cropItems.get(itemSize).getIngredient_name());
                Glide.with(requireContext())
                        .load(cropItems.get(itemSize).getIngredient_image())
                        .apply(RequestOptions.circleCropTransform())
                        .override(134, 134)
                        .into(binding.cropImage);
                setCropListener();
            }
        });

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