package com.example.woi_fe.ui.Diet;

import android.app.AlertDialog;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.woi_fe.Dialog.CustomDialog;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.ui.Diet.ItemMove.ItemMoveCallback;
import com.example.woi_fe.R;
import com.example.woi_fe.databinding.ActivityDietUpdateBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DietItemAdapter.AdapterCallback, CustomDialog.DialogCallbackListener {

    private Calendar calendar;

    private ActivityDietUpdateBinding binding;
    private MyUDietAdapter adapter;
    private List<MenuDTO> selectedItems = new ArrayList<>();
    private DietRetrofitAPI retrofitAPI;

    private DietItemAdapter diet_list_adapter;
    private ItemTouchHelper diet_list_helper;

    private boolean isSaved = false;
    private boolean isEdited = false;
    private boolean isChangedCategory = false;
    private int lastPosition = 0;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MyUDietAdapter(this, selectedItems);
        binding.dietRecyclerView.setAdapter(adapter);
        binding.dietRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 전달된 데이터를 가져옴
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String foodName = extras.getString("foodName");
//            String carbohydrate = extras.getString("carbohydrate");
//            String protein = extras.getString("protein");
//            String fat = extras.getString("fat");
//            String calories = extras.getString("calories");
//
//            // 전달받은 데이터를 MenuDTO 객체로 만듦
//            MenuDTO dietItem = new MenuDTO();
//            dietItem.setFoodName(foodName);
//            dietItem.setCalories(calories);
//            dietItem.setCarbohydrate(carbohydrate);
//            dietItem.setProtein(protein);
//            dietItem.setFat(fat);
//
//            Log.d("DietUpdateActivity", dietItem.getFoodName());
//        }

        // dietresponsedto를 activity에 선언해놓고 menulist를 menuresponsedto로 바꾼 후 붙여주기 -> 후에 adapter에 넘기기
        // list 미리 선언해놓고 마지막에 adapter에 붙이기?

        // 초기화
        isSaved = false;
        isEdited = false;

        Log.d("MainActivity", "[UpdateActivity] save: " + isSaved + " edit: " + isEdited);

        binding.btnMenuSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // menu 검색 페이지로 넘어가기
                Intent intent = new Intent(DietUpdateActivity.this, MenuSearchActivity.class);
                startActivity(intent);
            }
        });

        //날짜 초기화
        setInitDate();
        //날짜 변경
        onChangedDate();

        //스피너 설정
        setSpinner();
        //diet_item 설정
        setDietList();

        //저장 버튼
        binding.dietUpdateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrofit 연결
//                createDiet();

                isSaved = true;
                isChangedCategory = false;
                lastPosition = position; //현재 눌렸던 식단 타입을 이전 position으로 전달

                Log.d("MainActivity", "[UpdateActivity] save 클릭");
                Log.d("MainActivity", "save: " + isSaved + "changed: " + isChangedCategory);
                Log.d("MainActivity", "position: " + position + " lastPosition: " + lastPosition);
            }
        });
        //닫기 버튼
        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "[UpdateActivity] 닫기 버튼 클릭");

                //커스텀 다이얼로그 설정 (팝업창)
                setCustomDialog();
                isSaved = false;
                isEdited = false;
            }
        });
    }

    private void createDiet(DietDTO dietDTO){
        Call<ResponseBody> call = retrofitAPI.createDiet(dietDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    // 식단 일정 페이지로 넘어가기
                } else {
                    Toast.makeText(DietUpdateActivity.this, "식단 작성 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DietUpdateActivity",t.getMessage());
            }
        });
    }

    private void setCustomDialog() {
        Log.d("MainActivity", "**[setCustomDialog]");
        Log.d("MainActivity", "**[setCustomDialog] save: " + isSaved + " edit: " + isEdited);
        if(!isSaved){
            //완료 버튼을 누르지 않은 경우
            if(isEdited){
                //텍스트의 변경이 있는 경우
                //팝업 창
                showCustomDialog();
            }else{
                //텍스트의 변경이 없는 경우
                //창 닫기
            }
        }else{
            //완료 버튼을 누른 경우
            //초기화해야할 boolean 값들 ?
            isSaved = false;
            isEdited = false;
            //창 닫기
        }
        Log.d("MainActivity", "**[setCustomDialog]");
    }

    private void showCustomDialog() {
        Log.d("MainActivity", "** **[showCustomDialog]");

        CustomDialog dialog = new CustomDialog(this, this);

        dialog.show();
        Log.d("MainActivity", "** **[showCustomDialog]");
    }

    private void setInitDate() {
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;
        int date = calendar.get(Calendar.DATE);

        binding.dietDate.setText(year + "년 " + month + "월 " + date + "일");
    }

    private void setCompleteButtonClickListener() {
        binding.dietUpdateCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int setYear = binding.dietUpdateSetDatepicker.getYear();
                int setMonth = binding.dietUpdateSetDatepicker.getMonth() + 1;
                int setDate = binding.dietUpdateSetDatepicker.getDayOfMonth();
                binding.dietUpdateOpacityLayout.setVisibility(View.GONE);
                binding.dietUpdateDatepickerLayout.setVisibility(View.GONE);
                binding.dietDate.setText(setYear + "년 " + setMonth + "월 " + setDate + "일");
            }
        });
    }

    private void onChangedDate() {
        binding.dietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dietUpdateOpacityLayout.setVisibility(View.VISIBLE);
                binding.dietUpdateDatepickerLayout.setVisibility(View.VISIBLE);

                setCompleteButtonClickListener();
            }
        });
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.item_diet_update_diet_category);
        binding.dietSpinner.setAdapter(spinner_adapter);
        binding.dietSpinner.setOnItemSelectedListener(this);
        Log.d("MainActivity", "스피너 연결");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("MainActivity", "**[OnItemSelected]");
        Log.d("MainActivity", String.valueOf(adapterView.getSelectedItem()));

        isChangedCategory = true;

        lastPosition = i;

        isSaved = false;

        Log.d("MainActivity", "save: " + isSaved + "changed: " + isChangedCategory);
        Log.d("MainActivity", "position: " + position + " lastPosition: " + lastPosition);

        setCustomDialog();
        Log.d("MainActivity", "**[OnItemSelected]");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setDietList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietRecyclerView.setLayoutManager(manager);

        diet_list_adapter = new DietItemAdapter(this);
        binding.dietRecyclerView.setAdapter(diet_list_adapter);

        diet_list_helper = new ItemTouchHelper(new ItemMoveCallback(diet_list_adapter));
        diet_list_helper.attachToRecyclerView(binding.dietRecyclerView);

//        diet_list_adapter.addItem("흰쌀밥");
//        diet_list_adapter.addItem("된장찌개");
//        diet_list_adapter.addItem("계란말이");
//        diet_list_adapter.addItem("김치");
    }

    @Override
    public void onItemAdded(int position) {
        isEdited = true;
    }

    @Override
    public void onItemRemoved(int position) {
        isEdited = true;
    }

    @Override
    public void onItemChanged(int position) {
        isEdited = true;
    }

    @Override
    public void dialogCallbackListener(boolean isDialogResult) {
        Log.d("MainActivity", "** ** **[dialogCallbackListener]");
        if(isDialogResult){

            // true -> yes를 누른 경우
            // 수정 내용 반영 x
            // 기존의 내용 그대로 취소
            // lastPosition = 0
        }else{
            // false -> no를 누른 경우
            // 수정 내용 유지
            // customdialog만 취소
            // lastPosition = 0
            if(isChangedCategory){
                Log.d("MainActivity", "position: " + position);

            }
        }
        Log.d("MainActivity", "** ** **[dialogCallbackListener]");
    }
}
