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


//Esta clase tendrá como función principal incorporar dentro del Recycler View el diseño y
// componentes que contiene el archivo item_recycler.xml
public class TransaccionAdaptador extends RecyclerView.Adapter<TransaccionAdaptador.MiSoporteVista> {
    //    permite acceder al contexto de GastosActivity que muestra el Recycler View
    Context context;

    //    Se encarga de guardar el listado de transacciones
    ArrayList<TransaccionModelo> transaccionModeloArrayList;

    public TransaccionAdaptador(Context context, ArrayList<TransaccionModelo> transaccionModeloArrayList) {
        this.context = context;
        this.transaccionModeloArrayList = transaccionModeloArrayList;
    }

    @NonNull
    @Override
    //    Este método se encargará de especificar el XML que se desea "inflar" en el Recycler View
    public MiSoporteVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MiSoporteVista(view);
    }

    //    Este método tiene la función de colocar en los componentes del item_recycler.xml los datos
//    que quiero mostrar del JSON
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

    //    Este método especifica la cantidad de items a devolver, para ello retorno la cantidad de
//    elementos que hay en la lista
    @Override
    public int getItemCount() {
        return transaccionModeloArrayList.size();
    }

    //Esta clase especifica que componentes del archivo item_recycler se utilizarán
//    para mostrar datos del JSON

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
