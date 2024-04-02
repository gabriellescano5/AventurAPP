package com.example.aventurapp;

public class GastosModelo {
     String gastosID;
    String nota;
     String categoria;
    String name;
    String email;
    String usuario;
    String contrasena;
    String tipo;
    long importe;
    long tiempo;

    public GastosModelo() {
    }

    public GastosModelo(String gastosID, String nota, String categoria, String name, String email, String usuario, String contrasena, String tipo, long importe, long tiempo) {
        this.gastosID = gastosID;
        this.nota = nota;
        this.categoria = categoria;
        this.name = name;
        this.email = email;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.importe = importe;
        this.tiempo = tiempo;
    }

    public GastosModelo(String gastosID, String nota, String categoria, String tipo, long l, long timeInMillis) {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGastosID() {
        return gastosID;
    }

    public void setGastosID(String gastosID) {
        this.gastosID = gastosID;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
