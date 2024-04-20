package com.example.aventurapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Esta clase tendrá como papel principal establecer el llamado a la API,
//además de convertir y pasar los datos traídos en formato JSON a objeto JAVA mediante GSON
public class APIClient {
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.aventurapp2024.somee.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
