package com.example.aventurapp.gastos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.List;

public class GastosActivity extends AppCompatActivity {
    //Inicialización de variable
    DrawerLayout drawerLayout;
    ActivityGastosBinding binding;

    GastoAdaptador gastoAdaptador;
    GastoDB gastoDB;
    GastosDAO gastoDAO;

    long gasto = 0, ingreso=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGastosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);


        binding.newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GastosActivity.this, AgregarTransaccionActivity.class));
            }
        });
    }


    //        Método para refrescar los datos en GastosActivity
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

        gastoDB=GastoDB.getInstance(this);
        gastoDAO=gastoDB.getDao();
        gastoAdaptador= new GastoAdaptador((Context) this, (ClickEvent) this);
        binding.itemsRecycler.setAdapter(gastoAdaptador);
        binding.itemsRecycler.setLayoutManager(new LinearLayoutManager(this));

        gasto = 0;
        ingreso = 0;

        List<GastoTabla> expenseTables=gastoDAO.getAll();


        for(int i=0;i<expenseTables.size();i++){
            if(expenseTables.get(i).isIngreso()){
                ingreso=ingreso+expenseTables.get(i).getImporte();
            }else{
                gasto=gasto+expenseTables.get(i).getImporte();
            }
            gastoAdaptador.agregar(expenseTables.get(i));
        }
        binding.totalGasto.setText(gasto+"");
        binding.totalIngreso.setText(ingreso+"");
        long balance=ingreso-gasto;
        binding.totalImporte.setText(balance+"");
    }

    public void OnClick(int pos) {

        Intent intent=new Intent(GastosActivity.this, AgregarTransaccionActivity.class);
        intent.putExtra("update",true);
        intent.putExtra("id",gastoAdaptador.getId(pos));
        intent.putExtra("desc",gastoAdaptador.descripcion(pos));
        intent.putExtra("tipopago",gastoAdaptador.tipoPago(pos));
        intent.putExtra("importe",gastoAdaptador.importe(pos));
        intent.putExtra("ingreso",gastoAdaptador.isIngreso(pos));

        startActivity(intent);
    }

    public void OnLongPress(int pos) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Eliminar")
                .setMessage("¿Quieres eliminarlo?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id= gastoAdaptador.getId(pos);
                        gastoDAO.delete(id);
                        gastoAdaptador.delete(pos);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }

}