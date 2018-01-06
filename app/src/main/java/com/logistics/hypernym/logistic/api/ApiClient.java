package com.logistics.hypernym.logistic.api;

import com.google.android.gms.maps.MapView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Metis on 29-Dec-17.
 */

public class ApiClient {

    public static final String BASE_URL = "http://188.166.226.185/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(ApiInterface.MyOkHttpClient.getHttpClient())
                    .build();
        }
        return retrofit;
    }
}