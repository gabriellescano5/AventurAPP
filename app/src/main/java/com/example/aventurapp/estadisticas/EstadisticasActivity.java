package com.example.aventurapp.estadisticas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.aventurapp.R;

import com.example.aventurapp.consultas.ConsultasActivity;
import com.example.aventurapp.divisas.DivisasActivity;
import com.example.aventurapp.gastos.BalanceData;
import com.example.aventurapp.gastos.GastosActivity;
import com.example.aventurapp.menu.MainActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class EstadisticasActivity extends AppCompatActivity {

    private PieChart pieChart;
    DrawerLayout drawerLayout;

    private long ingreso;
    private long gasto;
    private long balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        drawerLayout = findViewById(R.id.drawer_layout);

        BalanceData balanceData = getIntent().getParcelableExtra("balanceData");
        ingreso = balanceData.getIngreso();
         gasto = balanceData.getGasto();
        balance = balanceData.getBalance();

        pieChart = findViewById(R.id.pieChart);
        setupPieChart();


    }
        private void setupPieChart(){
        int colorRojo= Color.parseColor("#B80000");
        int colorVerde= Color.parseColor("#0E8F1F");
        int colorAzul= Color.parseColor("#1e6091");
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(gasto,"Gastos"));
        entries.add(new PieEntry(ingreso,"Ingresos"));
        entries.add(new PieEntry(balance,"Balance"));

        PieDataSet dataSet = new PieDataSet(entries,"Estadísticas");
        dataSet.setColors(colorRojo, colorVerde, colorAzul);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Balance: " + balance);
        pieChart.animateY(1000);
        }
    public void ClickRefrescar(View view) {
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
        MainActivity.redireccionarActivity(this, ConsultasActivity.class);
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