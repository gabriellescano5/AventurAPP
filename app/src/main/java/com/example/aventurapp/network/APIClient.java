package com.example.aventurapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.aventurapp2024.somee.com/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
