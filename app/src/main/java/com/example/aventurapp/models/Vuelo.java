package com.example.aventurapp.models;

public class Vuelo {
    private int id;
    private String nomPaisOrigen;
    private String nomPaisDestino ;
    private String fechaOrigen;
    private String horarioOrigen;
    private String fechaDestino;
    private String  horarioDestino;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomPaisOrigen() {
        return nomPaisOrigen;
    }

    public void setNomPaisOrigen(String nomPaisOrigen) {
        this.nomPaisOrigen = nomPaisOrigen;
    }

    public String getNomPaisDestino() {
        return nomPaisDestino;
    }

    public void setNomPaisDestino(String nomPaisDestino) {
        this.nomPaisDestino = nomPaisDestino;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public String getHorarioOrigen() {
        return horarioOrigen;
    }

    public void setHorarioOrigen(String horarioOrigen) {
        this.horarioOrigen = horarioOrigen;
    }

    public String getFechaDestino() {
        return fechaDestino;
    }

    public void setFechaDestino(String fechaDestino) {
        this.fechaDestino = fechaDestino;
    }

    public String getHorarioDestino() {
        return horarioDestino;
    }

    public void setHorarioDestino(String horarioDestino) {
        this.horarioDestino = horarioDestino;
    }
}
