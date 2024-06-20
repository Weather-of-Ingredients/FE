package com.example.woi_fe.Retrofit.network;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
public class RetrofitClient {

    private static final String BASE_URL="http://10.0.2.2:8080";
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if(retrofit == null){

            // TODO : 데이터 통신의 로그를 Logcat에서 확인할 수 있다.
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("HTTP", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}

