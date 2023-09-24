package com.example.coema.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAOPaciente {
    BDPaciente dbPaciente;
    SQLiteDatabase database;

    public DAOPaciente(Context context){
        dbPaciente = new BDPaciente(context);
    }

    public void openBD()
    {

        database = dbPaciente.getWritableDatabase();
    }

    public void close(){
        dbPaciente.close();
        database.close();
    }


    public void registrarPaciente(Paciente paciente){
        try
        {
            ContentValues values = new ContentValues();
            values.put("fecha", paciente.getFecha());
            values.put("sexo", paciente.getSexo());
            values.put("id_foto", paciente.getIdFoto());
            values.put("nombre", paciente.getNombre());
            values.put("contra", paciente.getContra());
            values.put("correo", paciente.getCorreo());
            database.insert(ConstantesPaciente.NOMBRETABLAP, null, values);
        }
        catch (Exception e)
        {


        }
    }

    public void editarPaciente(Paciente paciente){
        try
        {
            ContentValues values = new ContentValues();
            values.put("id_foto", paciente.getIdFoto());
            values.put("nombre", paciente.getNombre());
            values.put("correo", paciente.getCorreo());
            values.put("contra", paciente.getContra());
            values.put("fecha", paciente.getFecha());

            database.update(ConstantesPaciente.NOMBRETABLAP, values, "id = ?", new String[] {paciente.getId() + ""});

        }
        catch (Exception e)
        {


        }
    }

    public void eliminarPaciente(int id){
        try
        {
            database.delete(ConstantesPaciente.NOMBRETABLAP, "id=" + id, null);
        }
        catch (Exception e)
        {

        }
    }

    public ArrayList<Paciente> getPaciente(){
        ArrayList<Paciente> listaPaciente = new ArrayList<>();
        try
        {
            Cursor c = database.rawQuery("select * from " + ConstantesPaciente.NOMBRETABLAP, null);
            while (c.moveToNext())
            {
                listaPaciente.add(new Paciente(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            }
        }
        catch (Exception e)
        {

        }

        return listaPaciente;
    }
}
