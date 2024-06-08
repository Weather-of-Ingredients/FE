package com.example.woi_fe.Diet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.woi_fe.Diet.ItemMove.ItemMoveAdapter;
import com.example.woi_fe.Diet.ItemMove.ItemMoveCallback;
import com.example.woi_fe.R;
import com.example.woi_fe.databinding.FragmentDietUpdateBinding;
import com.example.woi_fe.databinding.ItemDietUpdateMenuBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietUpdateFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar calendar;

    private FragmentDietUpdateBinding binding;
    private ItemDietUpdateMenuBinding menuBinding;

    public DietUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietUpdateFragment newInstance(String param1, String param2) {
        DietUpdateFragment fragment = new DietUpdateFragment();
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
        binding = FragmentDietUpdateBinding.inflate(inflater, container, false);

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;
        int date = calendar.get(Calendar.DATE);

        binding.dietDate.setText(year + "년 " + month + "월 " + date + "일");

        //날짜 변경
        binding.dietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //버튼 클릭 시 datapickter 등장
                binding.dietUpdateOpacityLayout.setVisibility(View.VISIBLE);
                binding.dietUpdateDatepickerLayout.setVisibility(View.VISIBLE);

                //완료 버튼 선택 시
                setCompleteButtonClickListener();
            }
        });

        //메뉴 수정


        //스피너 설정
        setSpinner();

        //diet_item 설정
        setDietList();

        return binding.getRoot();
    }

    private void setCompleteButtonClickListener() {
        binding.dietUpdateCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //날짜 얻어오기
                int setYear = binding.dietUpdateSetDatepicker.getYear();
                int setMonth = binding.dietUpdateSetDatepicker.getMonth() + 1;
                int setDate = binding.dietUpdateSetDatepicker.getDayOfMonth();
                //뷰를 다시 닫기 visible.GONE
                binding.dietUpdateOpacityLayout.setVisibility(View.GONE);
                binding.dietUpdateDatepickerLayout.setVisibility(View.GONE);
                //날짜 수정하기
                binding.dietDate.setText(setYear + "년 " + setMonth + "월 " + setDate + "일");

            }
        });
    }

    private void setDietList() {
        /*레이아웃 설정*/
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietRecyclerView.setLayoutManager(manager);

        /*어댑터 설정*/
        ItemMoveAdapter diet_list_adapter = new ItemMoveAdapter();
        binding.dietRecyclerView.setAdapter(diet_list_adapter);

        /*콜백 설정*/
        ItemTouchHelper diet_list_helper = new ItemTouchHelper(new ItemMoveCallback(diet_list_adapter));
        diet_list_helper.attachToRecyclerView(binding.dietRecyclerView);

        /*item 설정
        * 추후 변경
        * Retrofit 연결 필요
        * */
        diet_list_adapter.addItem("흰쌀밥");
        diet_list_adapter.addItem("된장찌개");
        diet_list_adapter.addItem("계란말이");
        diet_list_adapter.addItem("김치");
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(requireContext(), R.array.diet_array, R.layout.item_diet_update_diet_category);
        binding.dietSpinner.setAdapter(spinner_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("MainActivity", adapterView.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }}