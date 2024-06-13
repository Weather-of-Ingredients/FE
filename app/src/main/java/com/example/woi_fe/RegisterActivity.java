package com.example.woi_fe;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.user.UserRegisterDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private RegistrationRetrofitAPI registrationRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = RetrofitClient.getInstance();
        registrationRetrofitAPI = retrofit.create(RegistrationRetrofitAPI.class);

        // Register button 클릭 시 호출
        findViewById(R.id.register_button).setOnClickListener(v -> {
            UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
            userRegisterDTO.setName(((EditText) findViewById(R.id.name)).getText().toString());
            userRegisterDTO.setLoginId(((EditText) findViewById(R.id.username)).getText().toString());
            userRegisterDTO.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
            userRegisterDTO.setCheckedPassword(((EditText) findViewById(R.id.confirm_password)).getText().toString());
            userRegisterDTO.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
            userRegisterDTO.setPhoneNum(((EditText) findViewById(R.id.phone)).getText().toString());
            userRegisterDTO.setSchool(((EditText) findViewById(R.id.school)).getText().toString());

            registerUser(userRegisterDTO);
        });
    }

    private void registerUser(UserRegisterDTO userRegisterDTO) {
        Call<String> call = registrationRetrofitAPI.register(userRegisterDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    // 성공 시 추가적인 처리 (예: 로그인 페이지로 이동)
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
