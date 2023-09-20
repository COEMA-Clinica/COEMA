package com.example.coema.Listas;

import java.io.Serializable;

public class Paciente implements Serializable {
    private String nombre, correo, contra, fecha, sexo;
    private int id_foto, id;

    public Paciente(int id_foto, String nombre, String correo, String contra, String fecha, String sexo) {
        this.id_foto=id_foto;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.fecha = fecha;
        this.sexo = sexo;
    }

    public Paciente(int id, String nombre, String correo, String contra, String fecha, String sexo, int id_foto) {
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.fecha = fecha;
        this.sexo = sexo;
        this.id_foto = id_foto;
        this.id = id;
    }

    public Paciente(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFoto() {
        return id_foto;
    }

    public void setIdFoto(int id_foto) {
        this.id_foto = id_foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
