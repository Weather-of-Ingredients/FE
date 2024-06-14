package com.example.woi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.user.LoginRequestDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private RegistrationRetrofitAPI registrationRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = RetrofitClient.getInstance();
        registrationRetrofitAPI = retrofit.create(RegistrationRetrofitAPI.class);

        TextView subMessage = findViewById(R.id.sub_message);
        Spanned text = Html.fromHtml("<b>재료의 날씨</b>에 오신 것을 환영합니다!", Html.FROM_HTML_MODE_LEGACY);
        subMessage.setText(text);

        // "회원가입" 텍스트를 핑크색으로 설정
        TextView registerPrompt = findViewById(R.id.register_prompt);
        String htmlText = "아직 계정이 없으시다면 <font color='#FFC0CB'>회원가입</font>을 해주세요";
        Spanned registerText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        registerPrompt.setText(registerText);

        // Login button 클릭 시 호출
        findViewById(R.id.login_button).setOnClickListener(v -> {
            String loginId = ((EditText) findViewById(R.id.username)).getText().toString();
            String password = ((EditText) findViewById(R.id.password)).getText().toString();

            loginUser(loginId, password);
        });
    }

    private void loginUser(String loginId, String password) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setLoginId(loginId);
        loginRequestDTO.setPassword(password);

        Call<ResponseBody> call = registrationRetrofitAPI.login(loginRequestDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseString);
                        String message = jsonResponse.getString("token");
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        // 로그인 성공 시 다음 페이지로 이동
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Login Successful but failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
