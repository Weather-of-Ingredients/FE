package com.example.woi_fe.ui.Diet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuParDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.databinding.ActivityDietCreateBinding;

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

public class DietCreateActivity extends AppCompatActivity {

    private Calendar calendar;
    private ActivityDietCreateBinding binding;
    private MyUDietAdapter adapter2;
    private DietRetrofitAPI retrofitAPI;
    private DietDTO dietDTO;
    String type, date, week, selectedDate;
    private List<MenuParDTO> newMenusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = RetrofitClient.getInstance(this);
        retrofitAPI = retrofit.create(DietRetrofitAPI.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietUpdateRecyclerView.setLayoutManager(layoutManager);
        adapter2 = new MyUDietAdapter(this, new ArrayList<>());
        binding.dietUpdateRecyclerView.setAdapter(adapter2);

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
                Bundle bundle2 = new Bundle();
                bundle2.putString("date", selectedDate);
                bundle2.putString("type", binding.dietType.getText().toString());
                bundle2.putString("week", binding.dietWeek.getText().toString());

                // menu 검색 페이지로 넘어가기
                Intent intent = new Intent(DietCreateActivity.this, MenuSearchActivity.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        // Intent로부터 Bundle을 가져옴(MenuSearchActivity 가져온 것)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Bundle에서 데이터를 추출
            newMenusList = bundle.getParcelableArrayList("newMenus");
            date = bundle.getString("date");
            type = bundle.getString("type");
            week = bundle.getString("week");
            set3Text(date, type, week);

            // 메뉴 데이터를 MenuResponseDTO 리스트로 변환하여 어댑터에 설정
            if (newMenusList != null) {
                List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
                dietDTO = new DietDTO();
                for (MenuParDTO menuParDTO : newMenusList) {
                    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
                    menuResponseDTO.setFoodName(menuParDTO.getFoodName());
                    menuResponseDTO.setCarbohydrate(menuParDTO.getCarbohydrate());
                    menuResponseDTO.setProtein(menuParDTO.getProtein());
                    menuResponseDTO.setFat(menuParDTO.getFat());
                    menuResponseDTO.setCalories(menuParDTO.getCalories());
                    menuResponseDTOList.add(menuResponseDTO);
                }
                adapter2 = new MyUDietAdapter(this, menuResponseDTOList);
                binding.dietUpdateRecyclerView.setAdapter(adapter2);
                Log.d("DietUpdateActivity1", newMenusList.toString());

                dietDTO.setDate(date);
                dietDTO.setType(type);
                dietDTO.setWeek(week);
                dietDTO.setMenus(menuResponseDTOList);
            }
        }

        binding.dietUpdateSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDiet(dietDTO);
                finish();
                Log.d("DietUpdateActivity", dietDTO.getMenus().toString());
            }
        });

        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            DialogInterface.OnClickListener alertHandler = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                        case DialogInterface.BUTTON_NEGATIVE:

                    }
                }
            };
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DietCreateActivity.this);
                builder.setTitle("정말 나가시겠습니까?");
                builder.setMessage("변경된 사항은 저장되지 않습니다.");
                builder.setNegativeButton("Cancel", alertHandler);
                builder.setPositiveButton("Yes", alertHandler);
                builder.show();

            }
        });

    }

    private void createDiet(DietDTO dietDTO){
        Call<ResponseBody> call = retrofitAPI.createDiet(dietDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    // 식단 일정 페이지로 넘어가기(수정중...)
                    Toast.makeText(DietCreateActivity.this, "식단 작성 성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DietCreateActivity.this, "식단 작성 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DietUpdateActivity",t.getMessage());
            }
        });
    }

    private void set3Text(String date, String type, String week){
        binding.dietDate.setText(date);
        binding.dietType.setText(type);
        binding.dietWeek.setText(week);
    }

    private void showTypeDialog() {
        final String[] options = {"조식", "중식", "석식"};

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
                DietCreateActivity.this,
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
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        return format.format(date);
    }
}
