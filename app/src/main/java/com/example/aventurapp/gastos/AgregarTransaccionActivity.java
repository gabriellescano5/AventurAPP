package com.example.aventurapp.gastos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import java.util.Objects;


public class AgregarTransaccionActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    ActivityAgregarTransaccionBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitudActual;
    private double longitudActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Inicializando el cliente de ubicación
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Verifica los permisos y obtiene la última ubicación conocida
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // En algunos casos raros, la ubicación puede ser nula
                            if(location != null){
                                latitudActual = location.getLatitude();
                                longitudActual = location.getLongitude();
                            }
                        }
                    });
        } else {
            //Solicita los permisos si aún no están concedidos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

            // Para actualizar un dato
        boolean update = getIntent().getBooleanExtra("update", false);
        Toast.makeText(this, "" + update, Toast.LENGTH_SHORT).show();
        String desc = getIntent().getStringExtra("desc");
        long importe = getIntent().getLongExtra("importe", -1);
        int id = getIntent().getIntExtra("id", -1);
        String tpago = getIntent().getStringExtra("tipopago");
        boolean isIngreso = getIntent().getBooleanExtra("isingreso", false);

        //Para verificar si el usuario pretende hacer un update, entonces el botón lo cambia a update y habilita
        // el checkbox
        if (update) {
            binding.agregarTexto.setText("ACTUALIZAR");
            binding.importe.setText(importe + "");
            binding.tipoPago.setText(tpago);
            binding.descripcion.setText(desc);

            if (isIngreso) {
                binding.ingresoRadio.setChecked(true);
            } else {
                binding.gastoRadio.setChecked(true);
            }

        }

//        Función para ingresar un gasto / ingreso y la ubicación.
        binding.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String importe = binding.importe.getText().toString();
                String tipo = binding.tipoPago.getText().toString();
                String desc = binding.descripcion.getText().toString();
                boolean isIngreso = binding.ingresoRadio.isChecked();

                //Me aseguro de que la latitud y la longitud no sean 0.00, lo que podría indicar que
                //  la ubicación no se ha obtenido correctamente
                if (latitudActual != 0.00 && longitudActual != 0.00) {


                    GastoTabla gastoTabla = new GastoTabla();

                    gastoTabla.setImporte(Long.parseLong(importe));
                    gastoTabla.setDescripcion(desc);
                    gastoTabla.setIngreso(isIngreso);
                    gastoTabla.setTipoPago(tipo);
                    gastoTabla.setLatitud((latitudActual));
                    gastoTabla.setLongitud(longitudActual);

                    GastoDB gastoDB = GastoDB.getInstance(view.getContext());
                    GastoDAO gastoDAO = gastoDB.getDao();

                    if (!update) {
                        gastoDAO.insertGasto(gastoTabla);
                    } else {
                        gastoTabla.setId(id);
                        gastoDAO.updateGasto(gastoTabla);
                    }
                    finish();
                } else {
                    // Maneja el caso en que la ubicación no esté disponible
                    Toast.makeText(AgregarTransaccionActivity.this, "Ubicación no disponible, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}