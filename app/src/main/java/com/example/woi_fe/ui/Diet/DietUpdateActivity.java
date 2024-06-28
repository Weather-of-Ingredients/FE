package com.example.woi_fe.ui.Diet;


import static android.text.format.DateUtils.getDayOfWeekString;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.woi_fe.Dialog.CustomDialog;
import com.example.woi_fe.RegisterActivity;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.ui.Diet.ItemMove.ItemMoveCallback;
import com.example.woi_fe.R;
import com.example.woi_fe.databinding.ActivityDietUpdateBinding;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DietUpdateActivity extends AppCompatActivity {

    private Calendar calendar;
    private ActivityDietUpdateBinding binding;
    private MyUDietAdapter adapter2;
    private DietRetrofitAPI retrofitAPI;
    private DietDTO dietDTO;
    Integer dietId;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = RetrofitClient.getInstance(this);
        retrofitAPI = retrofit.create(DietRetrofitAPI.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietUpdateRecyclerView.setLayoutManager(layoutManager);

        // Intent로부터 Bundle을 가져옴(DietDetailActivity 가져온 것)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dietId = bundle.getInt("dietId");
        }
        loadDietByID(dietId);

        // 날짜 연결 및 변경 가능하게
        binding.addDdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedDate();
            }
        });

        // type 선택
        binding.addDtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });

        // 메뉴 추가 로직
        binding.btnMenuSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // menu 검색 페이지로 넘어가기
                Bundle bundle = new Bundle();
                bundle.putInt("dietId", dietId);

                Intent intent = new Intent(DietUpdateActivity.this, MenuSearchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        binding.dietUpdateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dietDTO != null) {
                    updateDiet(dietId, dietDTO);
                } else {
                    Toast.makeText(DietUpdateActivity.this, "식단 정보를 입력하세요", Toast.LENGTH_SHORT).show();

                //retrofit 연결
//                createDiet();

//                 isSaved = true;
//                 isChangedCategory = false;
//                 lastPosition = position; //현재 눌렸던 식단 타입을 이전 position으로 전달

//                 Log.d("MainActivity", "[UpdateActivity] save 클릭");
//                 Log.d("MainActivity", "save: " + isSaved + "changed: " + isChangedCategory);
//                 Log.d("MainActivity", "position: " + position + " lastPosition: " + lastPosition);

            }
        });

        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            DialogInterface.OnClickListener alertHandler = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DietUpdateActivity.this);
                builder.setTitle("정말 나가시겠습니까?");
                builder.setMessage("변경된 사항은 저장되지 않습니다.");
                builder.setNegativeButton("Cancel", alertHandler);
                builder.setPositiveButton("Yes", alertHandler);
                builder.show();

            }
        });

    }
    private void loadDietByID(Integer dietId){
        Call<DietResponseDTO> call = retrofitAPI.getDietByDietId(dietId);
        call.enqueue(new Callback<DietResponseDTO>() {
            @Override
            public void onResponse(Call<DietResponseDTO> call, Response<DietResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null){
                    binding.dietDate.setText(response.body().getDate());
                    binding.dietType.setText(response.body().getType());
                    binding.dietWeek.setText(response.body().getWeek());
                    // adapter설정 -> menu list 불러오기
                    adapter2 = new MyUDietAdapter(DietUpdateActivity.this, response.body().getMenus());
                    binding.dietUpdateRecyclerView.setAdapter(adapter2);
                }
            }

            @Override
            public void onFailure(Call<DietResponseDTO> call, Throwable t) {
                Log.e("DietUpdateActivity", "Failed to load diet by ID", t);
            }
        });
    }

    private void updateDiet(Integer dietId, DietDTO dietDTO){
        Call<ResponseBody> call = retrofitAPI.updateDiet(dietId, dietDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    // 식단 일정 페이지로 넘어가기(수정중...)
                    Intent intent = new Intent(DietUpdateActivity.this, DietDetailActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DietUpdateActivity.this, "식단 수정 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DietUpdateActivity",t.getMessage());
            }
        });
    }


    private void showTypeDialog() {
        final String[] options = {"조식", "중식", "석식"};

//     private void setCustomDialog() {
//         Log.d("MainActivity", "**[setCustomDialog]");
//         Log.d("MainActivity", "**[setCustomDialog] save: " + isSaved + " edit: " + isEdited);
//         if(!isSaved){
//             //완료 버튼을 누르지 않은 경우
//             if(isEdited){
//                 //텍스트의 변경이 있는 경우
//                 //팝업 창
//                 showCustomDialog();
//             }else{
//                 //텍스트의 변경이 없는 경우
//                 //창 닫기
//             }
//         }else{
//             //완료 버튼을 누른 경우
//             //초기화해야할 boolean 값들 ?
//             isSaved = false;
//             isEdited = false;
//             //창 닫기
//         }
//         Log.d("MainActivity", "**[setCustomDialog]");
//     }

//     private void showCustomDialog() {
//         Log.d("MainActivity", "** **[showCustomDialog]");

//         CustomDialog dialog = new CustomDialog(this, this);

//         dialog.show();
//         Log.d("MainActivity", "** **[showCustomDialog]");
//     }

//     private void setInitDate() {
//         calendar = Calendar.getInstance();

//         int year = calendar.get(Calendar.YEAR);
//         int month = calendar.get(Calendar.MONTH) +1;
//         int date = calendar.get(Calendar.DATE);

//         binding.dietDate.setText(year + "년 " + month + "월 " + date + "일");
//     }

//     private void setCompleteButtonClickListener() {
//         binding.dietUpdateCompleteButton.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 int setYear = binding.dietUpdateSetDatepicker.getYear();
//                 int setMonth = binding.dietUpdateSetDatepicker.getMonth() + 1;
//                 int setDate = binding.dietUpdateSetDatepicker.getDayOfMonth();
//                 binding.dietUpdateOpacityLayout.setVisibility(View.GONE);
//                 binding.dietUpdateDatepickerLayout.setVisibility(View.GONE);
//                 binding.dietDate.setText(setYear + "년 " + setMonth + "월 " + setDate + "일");
//             }
//         });
//     }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("타입 선택");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedType = options[which];
                binding.dietType.setText(selectedType);
            }
        });
        builder.create().show();
    }

    public void getSelectedDate(){
        calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                DietUpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedMonth = String.format("%02d", month + 1);
                        String formattedDay = String.format("%02d", dayOfMonth);
                        selectedDate = year + "-" + formattedMonth + "-" + formattedDay;

                        // 선택된 날짜의 요일을 계산
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);
                        int dayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);
                        String dayOfWeekString = getDayOfWeekString(dayOfWeek);

                        // 결과 표시
                        binding.dietDate.setText(selectedDate);
                        binding.dietWeek.setText(dayOfWeekString);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // 요일 분류
    public String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "일";
            case Calendar.MONDAY:
                return "월";
            case Calendar.TUESDAY:
                return "화";
            case Calendar.WEDNESDAY:
                return "수";
            case Calendar.THURSDAY:
                return "목";
            case Calendar.FRIDAY:
                return "금";
            case Calendar.SATURDAY:
                return "토";
            default:
                return "";
//     private void setSpinner() {
//         ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.item_diet_update_diet_category);
//         binding.dietSpinner.setAdapter(spinner_adapter);
//         binding.dietSpinner.setOnItemSelectedListener(this);
//         Log.d("MainActivity", "스피너 연결");
//     }

//     @Override
//     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//         Log.d("MainActivity", "**[OnItemSelected]");
//         Log.d("MainActivity", String.valueOf(adapterView.getSelectedItem()));

//         isChangedCategory = true;

//         lastPosition = i;

//         isSaved = false;

//         Log.d("MainActivity", "save: " + isSaved + "changed: " + isChangedCategory);
//         Log.d("MainActivity", "position: " + position + " lastPosition: " + lastPosition);

//         setCustomDialog();
//         Log.d("MainActivity", "**[OnItemSelected]");
//     }

//     @Override
//     public void onNothingSelected(AdapterView<?> adapterView) {

//     }

//     private void setDietList() {
//         LinearLayoutManager manager = new LinearLayoutManager(this);
//         manager.setOrientation(LinearLayoutManager.VERTICAL);
//         binding.dietRecyclerView.setLayoutManager(manager);

//         diet_list_adapter = new DietItemAdapter(this);
//         binding.dietRecyclerView.setAdapter(diet_list_adapter);

//         diet_list_helper = new ItemTouchHelper(new ItemMoveCallback(diet_list_adapter));
//         diet_list_helper.attachToRecyclerView(binding.dietRecyclerView);

// //        diet_list_adapter.addItem("흰쌀밥");
// //        diet_list_adapter.addItem("된장찌개");
// //        diet_list_adapter.addItem("계란말이");
// //        diet_list_adapter.addItem("김치");
//     }

//     @Override
//     public void onItemAdded(int position) {
//         isEdited = true;
//     }

//     @Override
//     public void onItemRemoved(int position) {
//         isEdited = true;
//     }

//     @Override
//     public void onItemChanged(int position) {
//         isEdited = true;
//     }

//     @Override
//     public void dialogCallbackListener(boolean isDialogResult) {
//         Log.d("MainActivity", "** ** **[dialogCallbackListener]");
//         if(isDialogResult){

//             // true -> yes를 누른 경우
//             // 수정 내용 반영 x
//             // 기존의 내용 그대로 취소
//             // lastPosition = 0
//         }else{
//             // false -> no를 누른 경우
//             // 수정 내용 유지
//             // customdialog만 취소
//             // lastPosition = 0
//             if(isChangedCategory){
//                 Log.d("MainActivity", "position: " + position);

//             }

        }
    }
}
