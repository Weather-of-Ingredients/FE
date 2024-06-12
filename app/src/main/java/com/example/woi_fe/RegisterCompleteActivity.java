package com.example.woi_fe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complete);


        TextView subMessage = findViewById(R.id.welcome_message);
        Spanned text = Html.fromHtml("<b>재료의 날씨</b>와 함께해주셔서 감사합니다!", Html.FROM_HTML_MODE_LEGACY);
        subMessage.setText(text);

        TextView subMessage2 = findViewById(R.id.sub_message);
        Spanned text2 = Html.fromHtml("로그인 페이지로 돌아가 <b>로그인</b>을 진행해주세요!", Html.FROM_HTML_MODE_LEGACY);
        subMessage2.setText(text2);

        Button backToLoginButton = findViewById(R.id.back_to_login_button);
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCompleteActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
