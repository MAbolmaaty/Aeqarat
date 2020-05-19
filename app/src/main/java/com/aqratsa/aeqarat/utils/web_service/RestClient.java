package com.aqratsa.aeqarat.utils.web_service;

import com.aqratsa.aeqarat.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static RestClient instance;
    private static AeqaratApi apiService;

    public RestClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(64, TimeUnit.SECONDS)
                .readTimeout(64, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(AeqaratApi.class);
    }

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();

        }
        return instance;
    }

    public AeqaratApi getApiClient() {
        return apiService;
    }
}
