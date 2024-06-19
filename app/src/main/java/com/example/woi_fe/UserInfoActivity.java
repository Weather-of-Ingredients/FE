package com.example.woi_fe;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woi_fe.Retrofit.controller.UserAPI;
import com.example.woi_fe.Retrofit.dto.user.UserInfoResponse;
import com.example.woi_fe.Retrofit.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInfoActivity extends AppCompatActivity {

    private UserAPI userAPI;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userNameTextView = findViewById(R.id.user_name);

        Retrofit retrofit = RetrofitClient.getInstance(this);
        userAPI = retrofit.create(UserAPI.class);

        fetchUserInfo();
    }

    private void fetchUserInfo() {
        Call<UserInfoResponse> call = userAPI.getUserInfo();
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (response.isSuccessful()) {
                    UserInfoResponse userInfo = response.body();
                    if (userInfo != null) {
                        userNameTextView.setText(userInfo.getName());
                    }
                } else {
                    Toast.makeText(UserInfoActivity.this, "Failed to fetch user info: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(UserInfoActivity.this, "Failed to fetch user info: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
