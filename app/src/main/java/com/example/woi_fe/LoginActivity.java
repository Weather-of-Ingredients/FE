package com.example.woi_fe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woi_fe.Retrofit.controller.RegistrationRetrofitAPI;
import com.example.woi_fe.Retrofit.dto.user.LoginRequestDTO;
import com.example.woi_fe.Retrofit.network.RetrofitClient;
import com.example.woi_fe.util.TokenManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private RegistrationRetrofitAPI registrationRetrofitAPI;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        Retrofit retrofit = RetrofitClient.getInstance(this);
//        registrationRetrofitAPI = retrofit.create(RegistrationRetrofitAPI.class);
//
//        TextView subMessage = findViewById(R.id.sub_message);
//        Spanned text = Html.fromHtml("<b>재료의 날씨</b>에 오신 것을 환영합니다!", Html.FROM_HTML_MODE_LEGACY);
//        subMessage.setText(text);
//
//        // "회원가입" 텍스트를 핑크색으로 설정
//        TextView registerPrompt = findViewById(R.id.register_prompt);
//        String htmlText = "아직 계정이 없으시다면 <font color='#FFC0CB'>회원가입</font>을 해주세요";
//        Spanned registerText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
//        registerPrompt.setText(registerText);
//
//        // Login button 클릭 시 호출
//        findViewById(R.id.login_button).setOnClickListener(v -> {
//            String loginId = ((EditText) findViewById(R.id.username)).getText().toString();
//            String password = ((EditText) findViewById(R.id.password)).getText().toString();
//
//
//            LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
//            loginRequestDTO.setLoginId(loginId);
//            loginRequestDTO.setPassword(password);
//
//            loginUser(loginId, password);
//        });
//
//        // 회원가입 버튼 누를시
//        Button goToRegisterButton = findViewById(R.id.register_button);
//        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    private void loginUser(String loginId, String password) {
//        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
//        loginRequestDTO.setLoginId(loginId);
//        loginRequestDTO.setPassword(password);
//
//        Call<ResponseBody> call = registrationRetrofitAPI.login(loginRequestDTO);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        String responseString = response.body().string();
//                        JSONObject jsonResponse = new JSONObject(responseString);
//                        String message = jsonResponse.getString("token");
//                        String token = jsonResponse.getString("token");
//                        saveToken(token); // 토큰 저장
//                        //Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, "로그인이 성공적으로 완료되었습니다. 환영합니다!", Toast.LENGTH_SHORT).show();
//                        // 로그인 성공 시 다음 페이지로 이동
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("TOKEN", token);
//                        startActivity(intent);
//                        finish();
//                    } catch (IOException | JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(LoginActivity.this, "Login Successful but failed to parse response", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 불일치합니다." + response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 불일치합니다." + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // 토큰 저장
//    private void saveToken(String token) {
//        SharedPreferences sharedPreferences = getSharedPreferences("WoI", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("jwtToken", token);
//        editor.apply();
//    }
//
//    // 토큰 읽기
//    private String getToken() {
//        SharedPreferences sharedPreferences = getSharedPreferences("WoI", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("jwtToken", null);
//    }
//}


public class LoginActivity extends AppCompatActivity {

    private RegistrationRetrofitAPI registrationRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("HTTP", "Login Activity");
        Retrofit retrofit = RetrofitClient.getInstance(this);
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

            LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
            loginRequestDTO.setLoginId(loginId);
            loginRequestDTO.setPassword(password);

            loginUser(loginRequestDTO);
        });

        // 회원가입 버튼 누를시
        Button goToRegisterButton = findViewById(R.id.register_button);
        goToRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loginUser(LoginRequestDTO loginRequestDTO) {
        Call<ResponseBody> call = registrationRetrofitAPI.login(loginRequestDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseString);
                        String token = jsonResponse.getString("token");
                        TokenManager.saveToken(LoginActivity.this, token); // 토큰 저장
                        Toast.makeText(LoginActivity.this, "로그인이 성공적으로 완료되었습니다. 환영합니다!", Toast.LENGTH_SHORT).show();
                        // 로그인 성공 시 다음 페이지로 이동
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("TOKEN", token);
                        startActivity(intent);
                        finish();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Login Successful but failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 불일치합니다." + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 불일치합니다." + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
