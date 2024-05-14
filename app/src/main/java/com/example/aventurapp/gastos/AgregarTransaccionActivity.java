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


public class AgregarTransaccionActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    ActivityAgregarTransaccionBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitudActual;
    private double longitudActual;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        //Inicializando el cliente de ubicación
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

       locationRequest = LocationRequest.create();
       locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       locationRequest.setInterval(10000); // 10 segundos de intervalo

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
               if(locationResult == null){
                   return;
               }
               for (Location location : locationResult.getLocations()){
                   if(location != null){
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
                    Toast.makeText(AgregarTransaccionActivity.this, "Ubicación no disponible, puedes salir y volver a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Detiene la actualización de la ubicación cuando la Activity no está visible
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}