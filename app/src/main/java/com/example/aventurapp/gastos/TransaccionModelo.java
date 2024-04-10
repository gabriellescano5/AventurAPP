package com.example.aventurapp.gastos;

public class TransaccionModelo {
    private String id, descripcion, importe, tipo, fecha;

    public TransaccionModelo() {
    }

    public TransaccionModelo(String id, String descripcion, String importe, String tipo, String fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.importe = importe;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
