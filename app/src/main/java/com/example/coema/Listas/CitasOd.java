package com.example.coema.Listas;

import java.io.Serializable;
import java.util.Date;

public class CitasOd implements Serializable {


    private String nom;
    private String trat;
    private Date fec;

    public CitasOd(String nom, String trat, Date fec) {
        this.nom = nom;
        this.trat = trat;
        this.fec = fec;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTrat() {
        return trat;
    }

    public void setTrat(String trat) {
        this.trat = trat;
    }

    public Date getFec() {
        return fec;
    }

    public void setFec(Date fec) {
        this.fec = fec;
    }

}
