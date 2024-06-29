// AuthInterceptor.java
package com.nutangel.woi_fe.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
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
