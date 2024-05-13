package com.example.aventurapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aventurapp.R;
import com.example.aventurapp.models.Vuelo;


import java.util.List;


//Esta clase tendrá como función principal incorporar dentro del Recycler View el diseño y
// componentes que contiene el archivo item_vuelo.xml
public class VueloAdapter extends RecyclerView.Adapter<VueloAdapter.ViewHolder> {

    //    Se encarga de guardar el listado de vuelos
    private List<Vuelo> vuelos;

    //    permite acceder al contexto de ConsultasActivity que muestra el Recycler View
    private Context context;

    public VueloAdapter(List<Vuelo> vuelos, Context context) {
        this.vuelos = vuelos;
        this.context = context;
    }

    @NonNull
    @Override
//    Este método se encargará de especificar el XML que se desea "inflar" en el Recycler View
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vuelo, parent, false);

        return new ViewHolder(view);
    }

    @Override
//    Este método tiene la función de colocar en los componentes del item_vuelo.xml los datos
//    que quiero mostrar del JSON
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.codigoVuelo.setText(vuelos.get(position).getId());
        holder.PaisOrigen.setText(vuelos.get(position).getNomPaisOrigen());
        holder.paisDestino.setText(vuelos.get(position).getNomPaisDestino());
        holder.fechaOrigen.setText(vuelos.get(position).getFechaOrigen());
        holder.horarioOrigen.setText(vuelos.get(position).getHorarioOrigen());
        holder.fechaDestino.setText(vuelos.get(position).getFechaDestino());
        holder.horarioDestino.setText(vuelos.get(position).getHorarioDestino());
    }

    //    Este método especifica la cantidad de items a devolver, para ello retorno la cantidad de
//    elementos que hay en la lista
    @Override
    public int getItemCount() {
            return vuelos.size();
    }


    //Esta clase especifica que componentes del archivo item_vuelo se utilizarán
//    para mostrar datos del JSON
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigoVuelo;
        TextView PaisOrigen;
        TextView paisDestino;
        TextView fechaOrigen;
        TextView horarioOrigen;
        TextView fechaDestino;
        TextView horarioDestino;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            Enlazo con los componentes que están en item_vuelo.xml
            codigoVuelo = itemView.findViewById(R.id.idVuelo);
            PaisOrigen = itemView.findViewById(R.id.paisOrigen);
            paisDestino = itemView.findViewById(R.id.paisDestino);
            fechaOrigen = itemView.findViewById(R.id.fechaOrigen);
            horarioOrigen = itemView.findViewById(R.id.horarioOrigen);
            fechaDestino = itemView.findViewById(R.id.fechaDestino);
            horarioDestino = itemView.findViewById(R.id.horarioDestino);
        }
    }
}
