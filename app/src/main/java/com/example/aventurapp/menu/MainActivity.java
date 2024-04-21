package com.example.aventurapp.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aventurapp.consultas.ConsultasActivity;
import com.example.aventurapp.databinding.ActivityMainBinding;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.R;
import com.example.aventurapp.gastos.GastosActivity;
import com.example.aventurapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //Inicialización de variables
    DrawerLayout drawerLayout;
    FirebaseFirestore firebaseFirestore;
    static FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
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

    public void ClickRefrescar(View view){
        recreate();
    }


    public static void cerrarSesion(Activity activity) {
        //Inicialización alert dialog
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Añadir título
        builder.setTitle("Cerrar Sesión");
        //añadir mensaje
        builder.setMessage("Está seguro de que desea cerrar sesión?");
        //botón positivo - SÍ
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
            }
        });
        //botón negativo - NO
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        //mostrar dialog
        builder.create().show();
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
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}