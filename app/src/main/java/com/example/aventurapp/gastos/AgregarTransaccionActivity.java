package com.example.aventurapp.gastos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.aventurapp.R;
import com.example.aventurapp.databinding.ActivityAgregarTransaccionBinding;
import com.example.aventurapp.menu.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class AgregarTransaccionActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    EditText lat, lon, dir;
    FusedLocationProviderClient fusedLocationProviderClient;
    ActivityAgregarTransaccionBinding binding;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

// Para actualizar un dato
        boolean update = getIntent().getBooleanExtra("update",false);
        Toast.makeText(this, ""+update, Toast.LENGTH_SHORT).show();
        String desc= getIntent().getStringExtra("desc");
        long importe=getIntent().getLongExtra("importe",-1);
        int id = getIntent().getIntExtra("id",-1);
        String tpago= getIntent().getStringExtra("tipopago");
        boolean isIngreso = getIntent().getBooleanExtra("isingreso",false);

        //Para verificar si el usuario pretende hacer un update, entonces el botón lo cambia a update y habilita
//        el checkbox
        if(update){
            binding.agregarTexto.setText("ACTUALIZAR");
            binding.importe.setText(importe+"");
            binding.tipoPago.setText(tpago);
            binding.descripcion.setText(desc);

            if(isIngreso){
                binding.ingresoRadio.setChecked(true);
            }else {
                binding.gastoRadio.setChecked(true);
            }

        }

//        Función para ingresar un gasto / ingreso
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
                GastoDAO gastoDAO = gastoDB.getDao();

                if(!update){
                    gastoDAO.insertGasto(gastoTabla);
                } else {
                    gastoTabla.setId(id);
                    gastoDAO.updateGasto(gastoTabla);
                }
                finish();
            }
        });


    }
}