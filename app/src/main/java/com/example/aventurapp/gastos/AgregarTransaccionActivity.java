package com.example.aventurapp.gastos;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.aventurapp.databinding.ActivityAgregarTransaccionBinding;

public class AgregarTransaccionActivity extends AppCompatActivity {

    ActivityAgregarTransaccionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarTransaccionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean update = getIntent().getBooleanExtra("update",false);
        Toast.makeText(this, ""+update, Toast.LENGTH_SHORT).show();
        String desc= getIntent().getStringExtra("desc");
        long importe=getIntent().getLongExtra("importe",-1);
        int id = getIntent().getIntExtra("id",-1);
        String tpago= getIntent().getStringExtra("tipopago");
        boolean isIngreso = getIntent().getBooleanExtra("isingreso",false);

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