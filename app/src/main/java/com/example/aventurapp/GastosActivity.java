package com.example.aventurapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.aventurapp.databinding.ActivityGastosBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GastosActivity extends AppCompatActivity {
    //Inicialización de variable
    DrawerLayout drawerLayout;
    ActivityGastosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGastosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);
        Intent intent = new Intent(GastosActivity.this, AgregarGastosActivity.class);

        binding.agregarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tipo", "Gasto");
                startActivity(intent);
            }
        });
        binding.agregarIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("tipo", "Ingreso");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Por favor");
        progressDialog.setMessage("espera");
        progressDialog.setCancelable(false);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            progressDialog.show();
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                        progressDialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(GastosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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
        //recrea actividad
        recreate();
    }

    public void ClickDivisas(View view) {
        //Redirecciona activity al panel divisas
        MainActivity.redireccionarActivity(this, DivisasActivity.class);
    }

    public void ClickConsultas(View view) {
        //Redirecciona activity al panel consultas
        MainActivity.redireccionarActivity(this, ConsultasActivity.class);
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