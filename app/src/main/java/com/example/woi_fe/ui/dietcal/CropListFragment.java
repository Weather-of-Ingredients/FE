package com.example.woi_fe.ui.dietcal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.recommendation.RecommendationDTO;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.example.woi_fe.Retrofit.repository.RecommendationRepository;
import com.example.woi_fe.databinding.FragmentCropListBinding;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CropListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CropListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentCropListBinding binding;

    private CropListAdapter cropListAdapter1, cropListAdapter2, cropListAdapter3;
    private Calendar calendar;
    private RecommendationRepository recommendationRepository;

    public CropListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CropListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CropListFragment newInstance(String param1, String param2) {
        CropListFragment fragment = new CropListFragment();
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
        binding = FragmentCropListBinding.inflate(inflater, container, false);
        recommendationRepository = new RecommendationRepository(requireContext());

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        callRetrofit(year, month);
        return binding.getRoot();
    }

    private void callRetrofit(int year, int month) {
        recommendationRepository.getRecommendationDTO(year, month).enqueue(new Callback<CropResponseDTO<RecommendationDTO>>() {
            @Override
            public void onResponse(Call<CropResponseDTO<RecommendationDTO>> call, Response<CropResponseDTO<RecommendationDTO>> response) {
                if(response.isSuccessful() && response.body() != null){
                    binding.cropListText.setVisibility(View.GONE);
                    binding.cropListFragment.setVisibility(View.VISIBLE);
                    CropResponseDTO<RecommendationDTO> responseDTO = response.body();
                    RecommendationDTO recommendationDTO = responseDTO.getData();

                    if(recommendationDTO != null){
                        List<CropItem> good_cropList = recommendationDTO.getGood_crop();
                        List<CropItem> bad_cropList = recommendationDTO.getBad_crop();
                        List<CropItem> alt_cropList = recommendationDTO.getAlt_crop();
                        setCropList(good_cropList, bad_cropList, alt_cropList);
                    }else{
                        binding.cropListFragment.setVisibility(View.GONE);
                        binding.cropListText.setVisibility(View.VISIBLE);
                    }
                }else{
                    binding.cropListFragment.setVisibility(View.GONE);
                    binding.cropListText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CropResponseDTO<RecommendationDTO>> call, Throwable t) {

            }
        });
    }


    private void setCropList(List<CropItem> good_cropList, List<CropItem> bad_cropList, List<CropItem> alt_cropList) {
        int spanCount = 3;

        GridLayoutManager manager = new GridLayoutManager(requireContext(), spanCount);
        binding.goodCropRecyclerview.setLayoutManager(manager);
        cropListAdapter1 = new CropListAdapter(good_cropList, requireContext());
        binding.goodCropRecyclerview.setAdapter(cropListAdapter1);

        GridLayoutManager manager2 = new GridLayoutManager(requireContext(), spanCount);
        binding.badCropRecyclerview.setLayoutManager(manager2);
        cropListAdapter2 = new CropListAdapter(bad_cropList, requireContext());
        binding.badCropRecyclerview.setAdapter(cropListAdapter2);


        GridLayoutManager manager3 = new GridLayoutManager(requireContext(), spanCount);
        binding.altCropRecyclerview.setLayoutManager(manager3);
        cropListAdapter3 = new CropListAdapter(alt_cropList, requireContext());
        binding.altCropRecyclerview.setAdapter(cropListAdapter3);



    }
}