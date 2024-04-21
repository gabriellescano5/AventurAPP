package com.example.aventurapp.gastos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aventurapp.databinding.ActivityActualizarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ActualizarActivity extends AppCompatActivity {

    ActivityActualizarBinding binding;
    String nuevoTipo;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActualizarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        String id = getIntent().getStringExtra("id");
        String descripcion = getIntent().getStringExtra("Descripcion");
        String importe = getIntent().getStringExtra("Importe");
        String tipo = getIntent().getStringExtra("tipo");

        binding.importeUsuario.setText(importe);
        binding.descripcionUsuario.setText(descripcion);
//el switch va a indicar que es lo que se quiere actualizar
//        Si el importe o el gasto, sería el checkbox
        switch (tipo) {
            case "Importe":
                nuevoTipo = "Importe";
                binding.importeCheckbox.setChecked(true);
                break;

            case "Gastos":
                nuevoTipo = "Gastos";
                binding.gastosCheckbox.setChecked(true);
                break;
        }
        binding.importeCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoTipo = "Importe";
                binding.importeCheckbox.setChecked(true);
                binding.gastosCheckbox.setChecked(false);
            }
        });

        binding.gastosCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoTipo = "Gastos";
                binding.importeCheckbox.setChecked(false);
                binding.gastosCheckbox.setChecked(true);
            }
        });

//        aquí los demás datos que quiero actualizar (en caso de querer)
        binding.btnActualizarTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String importe = binding.importeUsuario.getText().toString();
                String descripcion = binding.descripcionUsuario.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy_HH:mm", Locale.getDefault());
                String fechayhoraActual = sdf.format(new Date());

                firebaseFirestore.collection("GASTOS").document(firebaseAuth.getUid())
                        .collection("DESCRIPCION").document(id)
                        .update("Descripcion", descripcion, "Importe", importe, "fecha", fechayhoraActual, "tipo", nuevoTipo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(ActualizarActivity.this, "Dato Actualizado", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActualizarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

//        método para eliminar un dato
        binding.btnEliminarTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("GASTOS").document(firebaseAuth.getUid())
                        .collection("DESCRIPCION")
                        .document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(ActualizarActivity.this, "Dato Eliminado", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActualizarActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}