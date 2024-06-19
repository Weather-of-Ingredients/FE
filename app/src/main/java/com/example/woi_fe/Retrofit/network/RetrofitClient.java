package com.example.woi_fe.Retrofit.network;

import android.content.Context;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.woi_fe.util.AuthInterceptor;
import com.example.woi_fe.util.TokenManager;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static Retrofit retrofit;

    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {

            String token = TokenManager.getToken(context);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
