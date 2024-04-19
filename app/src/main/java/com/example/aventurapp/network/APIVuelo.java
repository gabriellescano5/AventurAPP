package com.example.aventurapp.network;

import com.example.aventurapp.models.Vuelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIVuelo {
    @GET("api/Vuelos/lista")
    Call<List<Vuelo>> getVuelos();
}
