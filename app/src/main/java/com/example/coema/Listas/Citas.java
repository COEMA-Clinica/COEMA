package com.example.coema.Listas;

import java.io.Serializable;

public class Citas implements Serializable {


    private int id_foto,id;
    private String nom, doc, clin, esp, fecCit, apePat, apeMat,sexo, correo;

    public Citas(int id_foto, String nom, String doc, String clin, String esp, String fecCit, String apePat, String apeMat, String sexo, String correo) {
        this.id_foto = id_foto;
        this.nom = nom;
        this.doc = doc;
        this.clin = clin;
        this.esp = esp;
        this.fecCit = fecCit;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.sexo = sexo;
        this.correo = correo;

    }

    public Citas(int id, int id_foto, String nom, String doc, String clin, String esp, String fecCit, String apePat, String apeMat, String sexo, String correo) {
        this.id=id;
        this.id_foto = id_foto;
        this.nom = nom;
        this.doc = doc;
        this.clin = clin;
        this.esp = esp;
        this.fecCit = fecCit;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.sexo = sexo;
        this.correo = correo;

    }

    public Citas() {

    }

    public int getIdFoto() {
        return id_foto;
    }

    public void setIdFoto(int id_foto) {
        this.id_foto = id_foto;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getClin() {
        return clin;
    }

    public void setClin(String clin) {
        this.clin = clin;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getFecCit() {
        return fecCit;
    }

    public void setFecCit(String fecCit) {
        this.fecCit = fecCit;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}