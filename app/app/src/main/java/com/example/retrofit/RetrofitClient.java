package com.example.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String baseUrl = "http://220.69.208.119:9000";

    public static Retrofit getClient() {
        if (retrofit == null) {
            // HttpLoggingInterceptor 설정
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient에 인터셉터 추가
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Retrofit 인스턴스 생성
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client) // OkHttpClient 설정 추가
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
