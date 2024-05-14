package com.example.aventurapp.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.aventurapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarClaveActivity extends AppCompatActivity {

        Button recuperarClave;
        EditText correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        recuperarClave= findViewById(R.id.btnRecuperacion);
        correo= findViewById(R.id.emailLogin);

        recuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });
    }
    public void validar(){
        String email= correo.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Correo inválido");
            return;
        }
        sendEmail(email);
    }

    public void sendEmail(String email){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String emailAddress= email;
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RecuperarClaveActivity.this, "CORREO ENVIADO! REVISA CARPETA SPAM", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RecuperarClaveActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RecuperarClaveActivity.this, "CORREO INVÁLIDO", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}