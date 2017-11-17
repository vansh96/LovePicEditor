package com.example.vaksys_android.lovepiceditor.Utility;

import com.example.vaksys_android.lovepiceditor.Model.ApplistResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vaksys-42 on 22/7/17.
 */

public interface ApiInterface
{

    @GET(Appconfig.MORE_APPS)
    Call<ApplistResponse> MORE_APPS_CALL();

    @GET(Appconfig.TOP_APPS)
    Call<ApplistResponse>TOP_APPS_CALL();

}
