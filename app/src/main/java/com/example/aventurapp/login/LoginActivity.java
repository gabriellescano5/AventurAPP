package com.example.aventurapp.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aventurapp.databinding.ActivityLoginBinding;
import com.example.aventurapp.menu.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Inicialización de variables
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        //Asignación a la variable Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Una vez que inicio sesión, mediante el intent me lleva a la pantalla principal del menú y finalizo el ciclo de la actividad de login
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    try {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } catch (Exception e) {

                    }
                }
            }
        });


        //Si no tengo cuenta para loguearme, me registro, mediante un intent desde login me lleva a la pantalla de registro
        binding.irPantallaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                try {
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        //Si tengo cuenta me logueo y me lleva a la actividad main que es la principal en el menú
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailLogin.getText().toString().trim();
                String contrasena = binding.contrasenaLogin.getText().toString().trim();
                if (email.length() <= 0 || contrasena.length() <= 0) {
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, contrasena)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                try {
                                    Toast.makeText(LoginActivity.this, "Bienvenido/a", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                } catch (Exception e) {

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        binding.recuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RecuperarClaveActivity.class);
            startActivity(intent);
            finish();
            }
        });
    }
}