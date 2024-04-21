package com.example.aventurapp.gastos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aventurapp.R;
import com.example.aventurapp.consultas.ConsultasActivity;
import com.example.aventurapp.databinding.ActivityGastosBinding;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.menu.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GastosActivity extends AppCompatActivity {
    //Inicialización de variable
    DrawerLayout drawerLayout;
    ActivityGastosBinding binding;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    int sumGastos = 0;
    int sumImporte = 0;

    ArrayList<TransaccionModelo> transaccionModeloArrayList;
    TransaccionAdaptador transaccionAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGastosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transaccionModeloArrayList = new ArrayList<>();
        binding.historialRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.historialRecycler.setHasFixedSize(true);

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            // permite observar los cambios en el estado de autenticación del usuario, proporcionado por
//            Firebase
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(GastosActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
// Método para agregar datos
        binding.btnFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(GastosActivity.this, AgregarTransaccionActivity.class));
                } catch (Exception e) {

                }
            }
        });

//        Método para refrescar los datos en GastosActivity
    /*    binding.refrescarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(GastosActivity.this, GastosActivity.class));
                    finish();
                } catch (Exception e) {

                }
            }
        });*/
        cargarDato();
    }

    //Agregar datos en documentos a Firebase Firestore, una base de datos NoSQL
    private void cargarDato() {
        firebaseFirestore.collection("GASTOS").document(firebaseAuth.getUid()).collection("DESCRIPCION")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot ds : task.getResult()) {
                            TransaccionModelo modelo = new TransaccionModelo(
                                    ds.getString("id"),
                                    ds.getString("Descripcion"),
                                    ds.getString("Importe"),
                                    ds.getString("tipo"),
                                    ds.getString("fecha"));
                            int importe = Integer.parseInt(ds.getString("Importe"));
                            if (ds.getString("tipo").equals("Gastos")) {
                                sumGastos = sumGastos + importe;
                            } else {
                                sumImporte = sumImporte + importe;
                            }
                            transaccionModeloArrayList.add(modelo);

                        }
                        binding.totalIngreso.setText(String.valueOf(sumImporte));
                        binding.totalGastos.setText(String.valueOf(sumGastos));
                        binding.totalBalance.setText(String.valueOf(sumImporte - sumGastos));
                        sumImporte = 0;
                        sumGastos = 0;

                        transaccionAdaptador = new TransaccionAdaptador(GastosActivity.this, transaccionModeloArrayList);

                        binding.historialRecycler.setAdapter(transaccionAdaptador);
                    }
                });
    }

    public void ClickRefrescar(View view){
        finish();
        startActivity(getIntent());
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
    }
}