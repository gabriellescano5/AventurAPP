package com.example.aventurapp.gastos;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gasto")
public class GastoTabla {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tipoPago;
    private long importe;
    private String descripcion;
    private boolean ingreso;

    public GastoTabla() {
    }

    public GastoTabla(int id, String tipoPago, long importe, String descripcion, boolean ingreso) {
        this.id = id;
        this.tipoPago = tipoPago;
        this.importe = importe;
        this.descripcion = descripcion;
        this.ingreso = ingreso;
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
}
