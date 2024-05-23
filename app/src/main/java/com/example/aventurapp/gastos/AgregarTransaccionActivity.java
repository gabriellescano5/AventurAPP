package com.example.aventurapp.gastos;

import android.Manifest;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import android.location.Location;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.aventurapp.databinding.ActivityAgregarTransaccionBinding;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.Date;


public class AgregarTransaccionActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    ActivityAgregarTransaccionBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitudActual;
    private double longitudActual;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        //Inicializando el cliente de ubicación
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitudActual = location.getLatitude();
                        longitudActual = location.getLongitude();
//                       Detiene la actualización de la ubicación si ya no es necesaria
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };
//        Verifico los permisos y comienzo a solicitar actualizaciones de ubicación
        startLocationUpdates();

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

//                Verifico que el importe no se ingrese vacío
                if (importe.isEmpty()) {
                    Toast.makeText(AgregarTransaccionActivity.this, "por favor, ingresa el importe", Toast.LENGTH_SHORT).show();
                    return;
                }

                GastoTabla gastoTabla = new GastoTabla();
                gastoTabla.setImporte(Long.parseLong(importe));
                gastoTabla.setDescripcion(desc);
                gastoTabla.setIngreso(isIngreso);
                gastoTabla.setTipoPago(tipo);
                gastoTabla.setFecha(new Date()); //Asigna la fecha actual


                GastoDB gastoDB = GastoDB.getInstance(view.getContext());
                GastoDAO gastoDAO = gastoDB.getDao();

                if (!update) {
//                    Si no es una actualización, maneja la ubicación
                    if (latitudActual != 0.00 && longitudActual != 0.00) {
                        gastoTabla.setLatitud(latitudActual);
                        gastoTabla.setLongitud(longitudActual);
                    } else {
//                        Si no hay permiso o la ubicación no se ha obtenido correctamente,
//                        Establece valores prederteminados
                        gastoTabla.setLatitud(-1);
                        gastoTabla.setLongitud(-1);
                    }
                    gastoDAO.insertGasto(gastoTabla);
                } else {
//                    Si es una actualización, no actualiza la ubicación
                    GastoTabla gastoExistente = gastoDAO.getGastoById(id);
                    if (gastoExistente != null) {
                        gastoTabla.setLatitud(gastoExistente.getLatitud());
                        gastoTabla.setLongitud(gastoExistente.getLongitud());
                    }
                    gastoTabla.setId(id);
                    gastoDAO.updateGasto(gastoTabla);
                }
                finish();
            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(500)
                    .build();

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Permiso concedido
                startLocationUpdates();
            } else {
//                Permiso denegado
                Toast.makeText(this, "Permiso de ubicación denegado. No se guardará la ubicación", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Detiene la actualización de la ubicación cuando la Activity no está visible
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}