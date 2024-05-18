package com.example.aventurapp.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.aventurapp.databinding.ActivityRegistroBinding;
import com.example.aventurapp.menu.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegistroActivity extends AppCompatActivity {
    ActivityRegistroBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        firebaseAuth = FirebaseAuth.getInstance();

//        una vez que me registro me loguea automáticamente y me lleva a la pantalla menú
        binding.irPantallaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                try {
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });

//        Me registro y puede que me cree el usuario o sea un usuario ya creado
        binding.btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailregistro.getText().toString();
                String contrasena = binding.contrasenaregistro.getText().toString();
                if (email.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                //                Verifico que el correo tenga el formato correcto de correo electrónico
                if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    Toast.makeText(RegistroActivity.this, "El formato del correo no es válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contrasena.length() < 6) {
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, contrasena).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegistroActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(RegistroActivity.this, "Las credenciales son inválidas", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistroActivity.this, "Error al registrar el usuario" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}