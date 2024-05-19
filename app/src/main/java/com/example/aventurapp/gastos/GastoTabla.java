package com.example.aventurapp.gastos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


//Clase Gasto con sus atributos, constructores, setters y getters y con el nombre de tabla definida y su
//clave primaria
@Entity(tableName = "gasto")
public class GastoTabla {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tipoPago;
    private long importe;
    private String descripcion;
    private boolean ingreso;
    private double latitud;
    private double longitud;
    private Date fecha;


    public GastoTabla() {
    }

    public GastoTabla(int id, String tipoPago, long importe, String descripcion, boolean ingreso, double latitud, double longitud, Date fecha) {
        this.id = id;
        this.tipoPago = tipoPago;
        this.importe = importe;
        this.descripcion = descripcion;
        this.ingreso = ingreso;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

