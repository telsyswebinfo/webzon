package com.webzon.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestInterface {
    public static final String BASE_URL_WEB = "http://webzon.in/";
    private static Retrofit retrofit = null;

    public static Retrofit getClients() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(120, TimeUnit.MINUTES)
                    .readTimeout(120, TimeUnit.MINUTES)
                    .writeTimeout(120, TimeUnit.MINUTES)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WEB)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
