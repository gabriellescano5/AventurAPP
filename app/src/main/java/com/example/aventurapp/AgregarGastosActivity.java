package com.example.aventurapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aventurapp.databinding.ActivityAgregarGastosBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.UUID;

public class AgregarGastosActivity extends AppCompatActivity {

    ActivityAgregarGastosBinding binding;
    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAgregarGastosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        tipo=getIntent().getStringExtra("Tipo");

        if (tipo.equals("Ingreso")){
            binding.ingresosRadio.setChecked(true);
        }else{
            binding.gastosRadio.setChecked(true);
        }

        binding.ingresosRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo="Ingreso";
            }
        });
        binding.gastosRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tipo="Gasto";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.guardarGastos){
            crearGastos();
            return true;
        }
        return false;
    }

    private void crearGastos() {
        String gastosID= UUID.randomUUID().toString();
        String importe=binding.importe.getText().toString();
        String nota=binding.nota.getText().toString();
        String categoria=binding.categoria.getText().toString();

        boolean chequeoIngreso=binding.ingresosRadio.isChecked();

        if (chequeoIngreso){
            tipo="Ingreso";
        }else {
            tipo="Gasto";
        }

        if (importe.trim().isEmpty()){
            binding.importe.setError("Vacío");
            return;
        }
        GastosModelo gastosModelo = new GastosModelo(gastosID, nota,categoria,tipo ,Long.parseLong(importe), Calendar.getInstance().getTimeInMillis());

        FirebaseFirestore
                .getInstance()
                .collection("Gastos")
                .document(gastosID)
                .set(gastosModelo);
        finish();
    }
}