package com.example.aventurapp.gastos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aventurapp.R;

import java.util.ArrayList;

public class TransaccionAdaptador extends RecyclerView.Adapter<TransaccionAdaptador.MiSoporteVista>{
    Context context;
    ArrayList<TransaccionModelo> transaccionModeloArrayList;

    public TransaccionAdaptador(Context context, ArrayList<TransaccionModelo> transaccionModeloArrayList) {
        this.context = context;
        this.transaccionModeloArrayList = transaccionModeloArrayList;
    }

    @NonNull
    @Override
    public MiSoporteVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MiSoporteVista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiSoporteVista holder, @SuppressLint("RecyclerView") int position) {
        TransaccionModelo modelo = transaccionModeloArrayList.get(position);
        String prioridad = modelo.getTipo();
        if (prioridad.equals("Gastos")) {
            holder.prioridad.setBackgroundResource(R.drawable.red_shape);
        } else {
            holder.prioridad.setBackgroundResource(R.drawable.green_shape);
        }
        holder.importe.setText(modelo.getImporte());
        holder.fecha.setText(modelo.getFecha());
        holder.descripcion.setText(modelo.getDescripcion());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActualizarActivity.class);
                intent.putExtra("id", transaccionModeloArrayList.get(position).getId());
                intent.putExtra("Descripcion", transaccionModeloArrayList.get(position).getDescripcion());
                intent.putExtra("Importe", transaccionModeloArrayList.get(position).getImporte());
                intent.putExtra("tipo", transaccionModeloArrayList.get(position).getTipo());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaccionModeloArrayList.size();
    }

    public class MiSoporteVista extends RecyclerView.ViewHolder {
        TextView descripcion, importe, fecha;
        View prioridad;

        public MiSoporteVista(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.nota_uno);
            importe = itemView.findViewById(R.id.importe_uno);
            fecha = itemView.findViewById(R.id.dato_uno);
            prioridad = itemView.findViewById(R.id.prioridad_uno);
        }
    }
}
