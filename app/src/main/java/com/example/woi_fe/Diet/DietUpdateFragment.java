package com.example.woi_fe.Diet;

import android.content.DialogInterface;
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

import com.example.woi_fe.Dialog.CustomDialog;
import com.example.woi_fe.Diet.ItemMove.ItemMoveCallback;
import com.example.woi_fe.R;
import com.example.woi_fe.databinding.FragmentDietUpdateBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietUpdateFragment extends Fragment implements AdapterView.OnItemSelectedListener, DietItemAdapter.AdapterCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar calendar;

    private FragmentDietUpdateBinding binding;
    private DietItemAdapter diet_list_adapter;
    private ItemTouchHelper diet_list_helper;

    private boolean isSaved = false;
    private boolean isEdited = false;
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
        //save 버튼 초기화
        isSaved = false;
        isEdited = false;

        Log.d("MainActivity", String.valueOf(isSaved));


        //날짜 초기화
        setInitDate();
        //날짜 변경
        onChangedDate();

        //스피너 설정
        setSpinner();
        //diet_item 설정
        setDietList();

        binding.dietUpdateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrofit 연결

                isSaved = true;
                Log.d("MainActivity", String.valueOf(isSaved));
            }
        });
        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "버튼 선택");
                //retrofit 연결
                /*
                 * case 1
                 * 텍스트의 변경이 있었지만
                 * 완료버튼을 누르지 않고
                 *
                 * 닫기 버튼을 누른 경우
                 *
                 * case 2
                 * 텍스트의 변경이 있었지만
                 * 완료버튼을 누르지 않고
                 *
                 * 화면을 내린 경우
                 *
                 *
                 * 추후 추가해야할 case
                 * 텍스트의 변경이 있었지만
                 * 완료버튼을 누르지 않고
                 *
                 * 카테고리 변경을 누른 경우

                 * */

                //커스텀 다이얼로그 설정 (팝업창)
                setCustomDialog();
                isSaved = false;
                isEdited = false;
            }
        });
        return binding.getRoot();
    }

    private void setCustomDialog() {
        Log.d("MainActivity", "setCustomDialog");
        Log.d("MainActivity", String.valueOf(isSaved));

        if(!isSaved){
            //완료 버튼을 누르지 않은 경우
            if(isEdited){
                //텍스트의 변경이 있는 경우
                //팝업 창
                Log.d("MainActivity", String.valueOf(isSaved));
                showCustomDialog();
            }else{
                //텍스트의 변경이 없는 경우
                //창 닫기
            }
        }else{
            //완료 버튼을 누른 경우
            //초기화해야할 boolean 값들 ?
            Log.d("MainActivity", String.valueOf(isSaved));
            isSaved = false;
            isEdited = false;
            //창 닫기
        }
    }

    private void showCustomDialog() {
        //dialogCustomBinding = DialogCustomBinding.inflate(getLayoutInflater());
        CustomDialog dialog = new CustomDialog(requireContext());

        //dialog.setContentView(dialogCustomBinding.getRoot());
        dialog.show();
    }

    private void setInitDate() {
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;
        int date = calendar.get(Calendar.DATE);

        binding.dietDate.setText(year + "년 " + month + "월 " + date + "일");
    }

    /*달력*/
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

    private void onChangedDate() {
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
    }

    /*spinner*/
    private void setSpinner() {
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(requireContext(), R.array.diet_array, R.layout.item_diet_update_diet_category);
        binding.dietSpinner.setAdapter(spinner_adapter);
        binding.dietSpinner.setOnItemSelectedListener(this);
        Log.d("MainActivity", "스피너 연결");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("MainActivity", String.valueOf(adapterView.getSelectedItem()));
        //카테고리가 변경될 때마다 완료 버튼의 boolean 값 false 로 변경
        //아니오를 누른 경우
        //변경되면 안됨
        isSaved = false;
        Log.d("MainActivity", String.valueOf(isSaved));
        setCustomDialog();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*item list*/
    private void setDietList() {
        /*레이아웃 설정*/
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietRecyclerView.setLayoutManager(manager);

        /*어댑터 설정*/
        diet_list_adapter = new DietItemAdapter(this);
        binding.dietRecyclerView.setAdapter(diet_list_adapter);

        /*콜백 설정*/
        diet_list_helper = new ItemTouchHelper(new ItemMoveCallback(diet_list_adapter));
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

    /*adapter callback method*/
    @Override
    public void onItemAdded(int position) {
        /*plus btn*/
        isEdited = true;
    }

    @Override
    public void onItemRemoved(int position) {
        /*delete btn*/
        isEdited = true;
    }

    @Override
    public void onItemChanged(int position) {
        /*edit text*/
        isEdited = true;
    }
}