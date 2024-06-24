package com.example.woi_fe.ui.Diet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.woi_fe.Dialog.CustomDialog;
import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.diet.DietDTO;
import com.example.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.example.woi_fe.databinding.ActivityDietDetailBinding;
import com.example.woi_fe.databinding.ActivityDietUpdateBinding;
import com.example.woi_fe.ui.Diet.ItemMove.ItemMoveCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietDetailActivity extends AppCompatActivity {

    private Calendar calendar;
    private ActivityDietDetailBinding binding;
    private MyUDietAdapter adapter1;
    private DietRetrofitAPI retrofitAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dietRecyclerView.setLayoutManager(layoutManager);

        // Intent로부터 Bundle을 가져옴(MyDietAdapter에서 가져온 것)
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Bundle에서 데이터를 추출
            String type = bundle.getString("type");
            String date = bundle.getString("date");
            String week = bundle.getString("week");
            ArrayList<String> menusList = bundle.getStringArrayList("menus");

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
                adapter1 = new MyUDietAdapter(this, menuResponseDTOList);
                binding.dietRecyclerView.setAdapter(adapter1);
                Log.d("DietUpdateActivity1", menusList.toString());
            }
            Log.d("DietUpdateActivity2", menusList.toString());
        }

        binding.dietEditBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietDetailActivity.this, DietUpdateActivity.class);
                startActivity(intent);
            }
        });
        binding.dietCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "[UpdateActivity] 닫기 버튼 클릭");
            }
        });

        setInitDate();
    }

    private void setInitDate() {
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);

        binding.dietDate.setText(year + "년 " + month + "월 " + date + "일");
    }

}
