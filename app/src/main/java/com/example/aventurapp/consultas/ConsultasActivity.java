package com.example.aventurapp.consultas;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aventurapp.R;

import com.example.aventurapp.adapter.VueloAdapter;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.gastos.GastosActivity;
import com.example.aventurapp.menu.MainActivity;
import com.example.aventurapp.models.Vuelo;
import com.example.aventurapp.network.APIClient;
import com.example.aventurapp.network.APIVuelo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultasActivity extends AppCompatActivity {
    //    Para almacenar el listado de vuelos del JSON
    private List<Vuelo> vueloList;
    //    para guardar los datos en recycler view
    private RecyclerView recyclerView;
    //    para utilizar el adaptador
    private VueloAdapter vueloAdapter;

    private ProgressDialog progressDialog;
    //Inicialización de variables
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);
//        Enlazo el recycler view con el componente id del activity_consultas.xml
        recyclerView = findViewById(R.id.rv_vuelos);
//        creo un objeto tipo lista
        vueloList = new ArrayList<>();
//        Aquí alineo los elementos que se agregarán en el recycler view, en este caso
//        en forma de lista
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        Aquí defino que el recycler view tendrá un tamaño fijo
        recyclerView.setHasFixedSize(true);

//        Inicializo ProgressDialog
        progressDialog = new ProgressDialog(ConsultasActivity.this);
        progressDialog.setMessage("Recuperando datos, por favor espere");
        progressDialog.setCancelable(false);
//Los datos serán mostrados al abrir la aplicación
        showVuelos();

    }

    // Este método es el encargado de ejecutar el llamado a la API
    public void showVuelos() {
//        Muestro el ProgressDialog antes de la llamada a la API
        progressDialog.show();
        Call<List<Vuelo>> call = APIClient.getClient().create(APIVuelo.class).getVuelos();
        call.enqueue(new Callback<List<Vuelo>>() {
            @Override
            public void onResponse(Call<List<Vuelo>> call, Response<List<Vuelo>> response) {
//                Cierro el ProgressDialog al recibir la respuesta
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    vueloList = response.body();
                    vueloAdapter = new VueloAdapter(vueloList, getApplicationContext());
                    recyclerView.setAdapter(vueloAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Vuelo>> call, Throwable t) {
//                Cierro el ProgressDialog si hay un fallo en la llamada a la API
                progressDialog.dismiss();
                Toast.makeText(ConsultasActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        //Redirecciona activity al panel gastos
        MainActivity.redireccionarActivity(this, GastosActivity.class);
    }



    public void ClickDivisas(View view) {
//Redirecciona activity al panel consultas
        MainActivity.redireccionarActivity(this, DivisasActivity.class);
    }

    public void ClickConsultas(View view) {
        //recrea actividad
        recreate();
    }

    public void ClickLogout(View view) {
            MainActivity.cerrarSesion(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //cierra barra
        MainActivity.cerrarBarra(drawerLayout);
    }


}