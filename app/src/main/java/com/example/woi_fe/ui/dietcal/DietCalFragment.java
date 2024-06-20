package com.example.woi_fe.ui.dietcal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        dietRepository = new DietRepository();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.feedRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyDietAdapter(requireContext(), new ArrayList<>());
        binding.feedRecyclerView.setAdapter(adapter);

//        binding.yetNoDiet.setVisibility(View.GONE);

        return root;
    }
}
