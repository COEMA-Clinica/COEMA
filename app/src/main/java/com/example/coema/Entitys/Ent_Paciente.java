package com.example.coema.Entitys;

import java.util.Date;

public class Ent_Paciente {
    private String nom,ape,gen,dir;
    private Date fecNac;

    private Integer tel, doc;

    public Ent_Paciente(Integer doc, String nom, String ape, String gen, String dir, Integer tel,Date FecNac) {
        this.doc = doc;
        this.nom = nom;
        this.ape = ape;
        this.gen = gen;
        this.dir = dir;
        this.tel = tel;
        this.fecNac = fecNac;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public Integer getDoc() {
        return doc;
    }

    public void setDoc(Integer doc) {
        this.doc = doc;
    }
}
