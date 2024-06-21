package com.example.woi_fe.ui.dietcal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.MainActivity;
import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.repository.DietRepository;
import com.example.woi_fe.databinding.FragmentDietcalBinding;
import com.example.woi_fe.ui.home.MyDietAdapter;

import java.util.ArrayList;

public class DietCalFragment extends Fragment {

    private FragmentDietcalBinding binding;
    private DietRepository dietRepository;
    private MyDietAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDietcalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dietRepository = new DietRepository(requireContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.feedRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyDietAdapter(requireContext(), new ArrayList<>());
        binding.feedRecyclerView.setAdapter(adapter);

//        binding.yetNoDiet.setVisibility(View.GONE);


        binding.dietCalGotoCropList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 프래그먼트 매니저 객체 가져오기
                CropListFragment fragment = new CropListFragment();
                ((MainActivity)getActivity()).loadFragment(fragment);

            }
        });
        return root;
    }
}
