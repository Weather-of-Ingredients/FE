package com.example.woi_fe.ui.Diet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietResponseDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.Retrofit.dto.recommendation.BadCropMenuDTO;
import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.Retrofit.dto.response.CropResponseDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.Retrofit.repository.RecommendationRepository;
import com.example.woi_fe.databinding.ActivityDietDetailBinding;
import com.example.woi_fe.ui.dietcal.DietCalFragment;
import com.example.woi_fe.ui.dietcal.MyTDietAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DietDetailActivity extends AppCompatActivity {

    private Calendar calendar;
    private ActivityDietDetailBinding binding;
    private MyDDietAdapter adapter1;
    private DietRetrofitAPI retrofitAPI;
    private RecommendationRepository recommendationRepository;
    private int itemSize = 0;
    private List<CropItem> cropItems = null;

    String type, date, week;
    int dietId, year, month, day;
    ArrayList<String> menusList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = RetrofitClient.getInstance(this);
        retrofitAPI = retrofit.create(DietRetrofitAPI.class);

        recommendationRepository = new RecommendationRepository(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietRecyclerView.setLayoutManager(layoutManager);

        // Intent로부터 Bundle을 가져옴(MyDietAdapter에서 가져온 것)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Bundle에서 데이터를 추출
            type = bundle.getString("type");
            date = bundle.getString("date");
            week = bundle.getString("week");
            dietId = bundle.getInt("dietId");
            menusList = bundle.getStringArrayList("menus");

            binding.dietType.setText(type);
            binding.dietDate.setText(date);

            // 메뉴 데이터를 MenuDTO 리스트로 변환하여 어댑터에 설정
            if (menusList != null) {
                List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();
                for (String menuName : menusList) {
                    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
                    menuResponseDTO.setFoodName(menuName);
                    // 필요한 경우 다른 필드도 설정
                    menuResponseDTOList.add(menuResponseDTO);
                }
                adapter1 = new MyDDietAdapter(this, menuResponseDTOList);
                binding.dietRecyclerView.setAdapter(adapter1);
                Log.d("DietDetailActivity1", menusList.toString());
                Log.d("DietDetailActivity1", String.valueOf(dietId));
            }
        }

        if (date != null && date.length() == 10) { // 간단한 유효성 검사
            // 연도 추출 (yyyy)
            String yearString = date.substring(0, 4);
            year = Integer.parseInt(yearString);

            // 월 추출 (MM)
            String monthString = date.substring(5, 7);
            month = Integer.parseInt(monthString);

            // day는 필요에 따라 추가로 추출 가능
             String dayString = date.substring(8, 10);
             day = Integer.parseInt(dayString);

        } else {
            // 유효하지 않은 date 형식 처리
            Log.e("MainActivity", "Invalid date format: " + date);
        }


        binding.dietEditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("dietId", dietId);

                Intent intent = new Intent(DietDetailActivity.this, DietUpdateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        binding.dietAnalysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAltCrop(year, month, day);
            }
        });

        binding.dietDeleteBtn.setOnClickListener(new View.OnClickListener() {
            DialogInterface.OnClickListener alertHandler = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            deleteDiet(dietId);
                            finish();
                        case DialogInterface.BUTTON_NEGATIVE:

                    }
                }
            };
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DietDetailActivity.this);
                builder.setTitle("정말 삭제하시겠습니까?");
                builder.setMessage("삭제한 식단은 복구할 수 없습니다.");
                builder.setNegativeButton("Cancel", alertHandler);
                builder.setPositiveButton("Yes", alertHandler);
                builder.show();
            }
        });

        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Log.d("MainActivity", "[UpdateActivity] 닫기 버튼 클릭");
            }
        });

    }

    private void showAltCrop(int year, int month, int day){
        recommendationRepository.getCropItems(year, month, "bad_crop").enqueue(new Callback<CropResponseDTO<List<CropItem>>>() {
            @Override
            public void onResponse(Call<CropResponseDTO<List<CropItem>>> call, Response<CropResponseDTO<List<CropItem>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        CropResponseDTO<List<CropItem>> ResponseCropItems = response.body();
                        cropItems = ResponseCropItems.getData();

                        if (cropItems != null && !cropItems.isEmpty()) {
                            binding.altLayout.setVisibility(View.VISIBLE);
                            binding.textNoBadCrop.setVisibility(View.GONE);
                            binding.badCropText.setText(cropItems.get(0).getIngredient_name());
                        } else {
                            binding.altLayout.setVisibility(View.GONE);
                            binding.textNoBadCrop.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }
                }
            }
            @Override
            public void onFailure(Call<CropResponseDTO<List<CropItem>>> call, Throwable t) {

            }
        });

        recommendationRepository.getCropItems(year, month, "alt_crop").enqueue(new Callback<CropResponseDTO<List<CropItem>>>() {
            @Override
            public void onResponse(Call<CropResponseDTO<List<CropItem>>> call, Response<CropResponseDTO<List<CropItem>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        CropResponseDTO<List<CropItem>> ResponseCropItems = response.body();
                        cropItems = ResponseCropItems.getData();

                        if (cropItems != null && !cropItems.isEmpty()) {
                            binding.altLayout.setVisibility(View.VISIBLE);
                            binding.textNoBadCrop.setVisibility(View.GONE);
                            binding.altCropText.setText(cropItems.get(0).getIngredient_name());
                        } else {
                            binding.altLayout.setVisibility(View.GONE);
                            binding.textNoBadCrop.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }
                }
            }
            @Override
            public void onFailure(Call<CropResponseDTO<List<CropItem>>> call, Throwable t) {

            }
        });

        // 메뉴당 작물 비교는 나중에 개발
    }

    private void deleteDiet(int dietId) {
        Call<Void> call = retrofitAPI.deleteDiet(dietId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DietDetailActivity.this, "식단 삭제 성공", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // 삭제 성공 결과 설정
                    finish();
                } else {
                    Toast.makeText(DietDetailActivity.this, "식단 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DietUpdateActivity", t.getMessage());
                Toast.makeText(DietDetailActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
