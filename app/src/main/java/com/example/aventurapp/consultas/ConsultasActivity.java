package com.example.aventurapp.consultas;

import android.os.Bundle;
import android.view.View;

import com.example.aventurapp.R;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.gastos.GastosActivity;
import com.example.aventurapp.menu.MainActivity;

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