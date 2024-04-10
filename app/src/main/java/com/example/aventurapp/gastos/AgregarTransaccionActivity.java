package com.example.aventurapp.gastos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.aventurapp.consultas.ConsultasActivity;
import com.example.aventurapp.databinding.ActivityAgregarTransaccionBinding;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.menu.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class AgregarTransaccionActivity extends AppCompatActivity {
    ActivityAgregarTransaccionBinding binding;
    FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        binding.gastosCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "Gastos";
                binding.gastosCheckbox.setChecked(true);
                binding.importeCheckbox.setChecked(false);
            }
        });
        binding.importeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "Importe";
                binding.gastosCheckbox.setChecked(false);
                binding.importeCheckbox.setChecked(true);
            }
        });


        binding.btnAgregarTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String importe = binding.importeUsuario.getText().toString().trim();
                String descripcion = binding.descripcionUsuario.getText().toString().trim();
                if (importe.length() <= 0) {
                    return;
                }
                if (tipo.length() <= 0) {
                    Toast.makeText(AgregarTransaccionActivity.this, "Tipo de transacción seleccionada", Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy_HH:mm", Locale.getDefault());
                String fechayhoraActual = sdf.format(new Date());

                String id = UUID.randomUUID().toString();
                Map<String, Object> transaccion = new HashMap<>();
                transaccion.put("id", id);
                transaccion.put("Importe", importe);
                transaccion.put("Descripcion", descripcion);
                transaccion.put("tipo", tipo);
                transaccion.put("fecha", fechayhoraActual);

                fStore.collection("GASTOS").document(firebaseAuth.getUid()).collection("DESCRIPCION").document(id)
                        .set(transaccion)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AgregarTransaccionActivity.this, "Dato Agregado", Toast.LENGTH_SHORT).show();
                                binding.descripcionUsuario.setText("");
                                binding.importeUsuario.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AgregarTransaccionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}