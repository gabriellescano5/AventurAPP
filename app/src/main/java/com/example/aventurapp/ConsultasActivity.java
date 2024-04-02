package com.example.aventurapp;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class ConsultasActivity extends AppCompatActivity {



    //Inicialización de variables
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);


    }

    public void ClickMenu(View view) {
        //abrir barra
        MainActivity.abrirBarra(drawerLayout);
    }

    public void ClickLogo(View view) {
        //Cerrar barra
        MainActivity.cerrarBarra(drawerLayout);
    }

    public void ClickInicio(View view) {
        //Redirecciona activity al panel inicio
        MainActivity.redireccionarActivity(this, MainActivity.class);
    }

    public void ClickGastos(View view) {
        //Redirecciona activity al panel gastos
        MainActivity.redireccionarActivity(this, GastosActivity.class);
    }

    public void ClickDivisas(View view) {
//Redirecciona activity al panel consultas
        MainActivity.redireccionarActivity(this, DivisasActivity.class);
    }

    public void ClickConsultas(View view) {
        //recrea actividad
        recreate();
    }

    public void ClickLogout(View view) {
        //Cerrar app
        MainActivity.cerrarSesion(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //cierra barra
        MainActivity.cerrarBarra(drawerLayout);
    }


}