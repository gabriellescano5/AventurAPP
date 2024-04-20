package com.example.aventurapp.models;



//Esta clase representa la estructura de datos de cada vuelo de la API desplegada.
// Atributos, Constructor y setters & getters
public class Vuelo {
    private String id;
    private String nomPaisOrigen;
    private String nomPaisDestino;
    private String fechaOrigen;
    private String horarioOrigen;
    private String fechaDestino;
    private String horarioDestino;

    public Vuelo(String id, String nomPaisOrigen, String nomPaisDestino, String fechaOrigen, String horarioOrigen, String fechaDestino, String horarioDestino) {
        this.id = id;
        this.nomPaisOrigen = nomPaisOrigen;
        this.nomPaisDestino = nomPaisDestino;
        this.fechaOrigen = fechaOrigen;
        this.horarioOrigen = horarioOrigen;
        this.fechaDestino = fechaDestino;
        this.horarioDestino = horarioDestino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
