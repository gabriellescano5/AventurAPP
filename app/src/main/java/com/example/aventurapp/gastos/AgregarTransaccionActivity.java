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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean update = getIntent().getBooleanExtra("update", true);
        Toast.makeText(this, ""+update, Toast.LENGTH_SHORT).show();
        String desc = getIntent().getStringExtra("desc");
        long importe = getIntent().getLongExtra("importe",-1);
        int id = getIntent().getIntExtra("id",-1);
        String tPago = getIntent().getStringExtra("tipopago");
        boolean isImporte = getIntent().getBooleanExtra("isimporte", false);
        if(update){
            binding.agregarTexto.setText("ACTUALIZAR");
            binding.importe.setText(importe+"");
            binding.tipoPago.setText(tPago);
            binding.descripcion.setText(desc);

            if(isImporte){
                binding.ingresoRadio.setChecked(true);
            }else {
                binding.gastoRadio.setChecked(true);
            }
        }
        binding.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String importe = binding.importe.getText().toString();
                String tipo = binding.tipoPago.getText().toString();
                String desc = binding.descripcion.getText().toString();
                boolean isIngreso = binding.ingresoRadio.isChecked();

                GastoTabla gastoTabla = new GastoTabla();

                gastoTabla.setImporte(Long.parseLong(importe));
                gastoTabla.setDescripcion(desc);
                gastoTabla.setIngreso(isIngreso);
                gastoTabla.setTipoPago(tipo);

                GastoDB gastoDB = GastoDB.getInstance(view.getContext());
                GastosDAO gastosDAO = gastoDB.getDao();

                if(!update){
                    gastosDAO.insertGasto(gastoTabla);
                } else {
                    gastoTabla.setId(id);
                    gastosDAO.updateGasto(gastoTabla);
                }
                finish();
            }
        });
    }

}