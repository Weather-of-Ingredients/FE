package com.example.woi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.user.UserRegisterDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        Call<ResponseBody> call = registrationRetrofitAPI.register(userRegisterDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String message = jsonObject.getString("message");
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        // 성공 시 추가적인 처리 (예: 로그인 페이지로 이동)
                        Intent intent = new Intent(RegisterActivity.this, RegisterCompleteActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Successful but failed to parse response", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Successful but response format is invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = "";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorMessage);
                            errorMessage = jsonObject.getString("message");
                        } else {
                            errorMessage = response.message();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorMessage = e.getMessage();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        errorMessage = "Failed to parse error response";
                    }
                    Toast.makeText(RegisterActivity.this, "Register Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
