package com.example.woi_fe;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.text.Spanned;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView subMessage = findViewById(R.id.sub_message);
        Spanned text = Html.fromHtml("<b>재료의 날씨</b>에 오신 것을 환영합니다!", Html.FROM_HTML_MODE_LEGACY);
        subMessage.setText(text);

        // "회원가입" 텍스트를 핑크색으로 설정
        TextView registerPrompt = findViewById(R.id.register_prompt);
        String htmlText = "아직 계정이 없으시다면 <font color='#FFC0CB'>회원가입</font>을 해주세요";
        Spanned registerText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        registerPrompt.setText(registerText);
    }
}