package com.nutangel.woi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutangel.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.user.UserRegisterDTO;
import com.nutangel.woi_fe.Retrofit.network.RetrofitClient;

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

        Retrofit retrofit = RetrofitClient.getInstance(this);
        registrationRetrofitAPI = retrofit.create(RegistrationRetrofitAPI.class);

        // Register button 클릭 시 호출
        findViewById(R.id.register_button).setOnClickListener(v -> {
            if (validateInput()) {
                UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
                userRegisterDTO.setName(((EditText) findViewById(R.id.name)).getText().toString());
                userRegisterDTO.setLoginId(((EditText) findViewById(R.id.username)).getText().toString());
                userRegisterDTO.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
                userRegisterDTO.setCheckedPassword(((EditText) findViewById(R.id.confirm_password)).getText().toString());
                userRegisterDTO.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
                userRegisterDTO.setPhoneNum(((EditText) findViewById(R.id.phone)).getText().toString());
                userRegisterDTO.setSchool(((EditText) findViewById(R.id.school)).getText().toString());

                registerUser(userRegisterDTO);
            }
        });
    }

    private boolean validateInput() {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirm_password)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();

        if (username.length() < 6) {
            Toast.makeText(this, "아이디는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$")) {
            Toast.makeText(this, "비밀번호는 대소문자와 숫자를 포함하여 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.matches("^(01[0-9])\\d{3,4}\\d{4}$")) {
            Toast.makeText(this, "전화번호 형식이 맞지 않습니다. 01XXXXXXXX 또는 01XXXXXXXXX 형식을 사용하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
                        // 성공 시 로그인 페이지로 이동
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
