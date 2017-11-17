package com.example.vaksys_android.lovepiceditor.Utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vaksys-42 on 1/8/17.
 */

public class ApiClient
{
    private static Retrofit retrofit = null;

    public static OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Appconfig.BASE_URL_List)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient1)
                    .build();
        }
        return retrofit;
    }
}
