package com.example.aventurapp.login;

import android.content.Intent;
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

public class RegistroActivity extends AppCompatActivity {
    ActivityRegistroBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

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

        binding.btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailregistro.getText().toString();
                String contrasena = binding.contrasenaregistro.getText().toString();
                if (email.trim().length() <= 0 || contrasena.trim().length() <= 0) {
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, contrasena).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegistroActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}