package com.example.woi_fe.Retrofit.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import java.io.IOException;

// AuthInterceptor 클래스
class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (token == null) {
            return chain.proceed(originalRequest);
        }
        Request.Builder builder = originalRequest.newBuilder().header("Authorization", "Bearer " + token);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}


public class RetrofitClient {

    private static final String BASE_URL="http://10.0.2.2:8080";

    private static Retrofit retrofit;

    public static Retrofit getInstance(Context context){
        if(retrofit == null){

            SharedPreferences sharedPreferences = context.getSharedPreferences("WoI", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("jwtToken", null);

            // TODO : 데이터 통신의 로그를 Logcat에서 확인할 수 있다.
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

