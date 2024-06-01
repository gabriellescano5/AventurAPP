package com.example.aventurapp.gastos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aventurapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


//Adaptador para mostrar los datos en el Recycler View
public class GastoAdaptador extends RecyclerView.Adapter<GastoAdaptador.MyViewHolder> {

    private Context context;

    private ClickEvent clickEvent;

    private List<GastoTabla> gastoTablaList;

    public GastoAdaptador(Context context, ClickEvent clickEvent) {
        this.context = context;
        gastoTablaList = new ArrayList<>();
        this.clickEvent = clickEvent;
    }

    public void agregar(GastoTabla gastoTabla) {
        gastoTablaList.add(gastoTabla);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GastoTabla gastoTabla = gastoTablaList.get(position);
        holder.titulo.setText(gastoTabla.getTipoPago());
        holder.importe.setText(String.valueOf(gastoTabla.getImporte()));
        holder.descripcion.setText(gastoTabla.getDescripcion());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.fecha.setText(sdf.format(gastoTabla.getFecha()));


        if (gastoTabla.isIngreso()) {
            holder.estado.setText("Ingreso");
        } else {
            holder.estado.setText("Gasto");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.OnClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickEvent.OnLongPress(position);
                return true;
            }
        });
        holder.ubicacion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int gastoId = gastoTablaList.get(position).getId();
                GastoDAO gastoDAO = GastoDB.getInstance(context).getDao();

                gastoDAO.obtenerCoordenadasPorId(gastoId).observe((LifecycleOwner) context, GastoDB->{
                    if (GastoDB != null){

                        double latitud = GastoDB.getLatitud();
                        double longitud = GastoDB.getLongitud();

                        String urlGoogleMaps = "https://www.google.com/maps/search/?api=1&query=" + latitud +","+longitud;

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlGoogleMaps));
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return gastoTablaList.size();
    }

    public void eliminar(int pos) {
        gastoTablaList.remove(pos);
        notifyDataSetChanged();
    }

    public int getId(int pos) {
        return gastoTablaList.get(pos).getId();
    }

    public boolean isIngreso(int pos) {
        return gastoTablaList.get(pos).isIngreso();
    }

    public String tipoPago(int pos) {
        return gastoTablaList.get(pos).getTipoPago();
    }

    public long importe(int pos) {
        return gastoTablaList.get(pos).getImporte();
    }

    public String desc(int pos) {
        return gastoTablaList.get(pos).getDescripcion();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView estado, titulo, descripcion, importe, fecha, ubicacion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            estado = itemView.findViewById(R.id.isImporte);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            importe = itemView.findViewById(R.id.importe);

            fecha = itemView.findViewById(R.id.FechaDato);
            ubicacion = itemView.findViewById(R.id.abrirMapa);
        }
    }
}
