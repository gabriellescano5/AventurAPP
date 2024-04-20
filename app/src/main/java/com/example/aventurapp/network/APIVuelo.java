package com.example.aventurapp.network;

import com.example.aventurapp.models.Vuelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


//  Esta tiene como papel principal describir todas las operaciones que solicitaremos de la API,
//      como por ejemplo agregar, listar, ver, eliminar
public interface APIVuelo {
    @GET("api/Vuelos/lista")
    Call<List<Vuelo>> getVuelos();
}
