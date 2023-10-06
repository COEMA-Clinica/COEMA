package com.example.coema.Perfil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.R;
import com.example.coema.Registro.RegistroCitas;

import java.util.ArrayList;

public class PacientePrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cita);
//        obtenerDatos();


        // Puedes agregar aquí la lógica para manejar la entrada del usuario y la funcionalidad de guardado en la base de datos.

    }


    public void registrarCita(View view) {
        Intent intent = new Intent(this, RegistroCitas.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void perfilPaciente(View view) {
        Intent intent = new Intent(this, PerfilPaciente.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /*public void salirPrincipalPaciente(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, ActPrincipal.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void nosotrosPrincipalPaciente(View view) {
        Intent intent = new Intent(this, ActInfo.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
    */


}

