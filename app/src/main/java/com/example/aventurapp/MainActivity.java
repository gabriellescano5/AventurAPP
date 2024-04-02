package com.example.aventurapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Spinner;
import android.widget.ImageButton;

import com.google.gson.annotations.SerializedName;

import retrofit2.converter.gson.GsonConverterFactory;

import android.widget.Toast;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import android.text.Editable;
import android.util.Log;
import android.widget.AdapterView;

import androidx.core.view.GravityCompat;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    //Inicialización de variables
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
        //Abrir barra
        abrirBarra(drawerLayout);

    }

    public static void abrirBarra(DrawerLayout drawerLayout) {
        //Abrir barra layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        //Cerrar barra
        cerrarBarra(drawerLayout);
    }

    public static void cerrarBarra(DrawerLayout drawerLayout) {
        //Cerrar barra layout
        //chequea condición
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //Cuando la barra está abierta
            //Cierra barra
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void ClickInicio(View view) {
        //recrea actividad
        recreate();
    }

    public void ClickGastos(View view) {
        //Redirecciona actividad al panel gastos
        redireccionarActivity(this, GastosActivity.class);
    }

    public void ClickDivisas(View view) {
        //Redirecciona actividad al panel divisas
        redireccionarActivity(this, DivisasActivity.class);
    }

    public void ClickConsultas(View view) {
        //Redirecciona actividad al panel Consultas
        redireccionarActivity(this, ConsultasActivity.class);
    }

    public void ClickLogout(View view) {
        //Cerrar aplicación
        cerrarSesion(this);

    }

    public static void cerrarSesion(Activity activity) {
        //Inicialización alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Añadir título
        builder.setTitle("Cerrar Sesión");
        //añadir mensaje
        builder.setMessage("Está seguro de que desea cerrar sesión?");
        //botón positivo - SÍ
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finalización de activity
                activity.finish();
                //salir de la aplicación
                System.exit(0);
            }
        });
        //botón negativo - NO
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //mostrar dialog
        builder.show();
    }

    public static void redireccionarActivity(Activity activity, Class aClass) {
        //Inicialización Intent
        Intent intent = new Intent(activity, aClass);
        // Establecer flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Iniciar activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //cerrar drawer
        cerrarBarra(drawerLayout);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}