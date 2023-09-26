package com.example.coema.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.R;
import com.example.coema.Registro.RegistroPacientes;

import java.util.ArrayList;

public class ActIniciarSesionAdmin extends AppCompatActivity {
    ArrayList<Paciente> listaPaciente;
    ArrayList<Citas> listaCita;
    EditText txtCorreo, txtContra;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion_admin);
        asignarReferencias();
        recuperarData();
    }
    private void recuperarData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            listaPaciente = new ArrayList<>();
            listaCita = new ArrayList<>();
        }
        else{
            listaPaciente = (ArrayList<Paciente>) bundle.getSerializable("dataPaciente");
            listaCita = (ArrayList<Citas>) bundle.getSerializable("dataCitas");
        }
    }
    private void asignarReferencias(){
        txtCorreo = findViewById(R.id.txtEmailR);
        txtContra = findViewById(R.id.txtContraR);
    }


    private boolean verificarRegistro(String correo, String contra){

        if(correo.equals("#admin@hotmail.com") && contra.equals("admin123"))
        {
            return true;
        }

        return false;
    }
    public void iniciarSesionAdmin(View view){
        String correo = txtCorreo.getText().toString();
        String contra = txtContra.getText().toString();
        boolean registrado = verificarRegistro(correo, contra);

        if(registrado)
        {
            Intent intent = new Intent(this, ActIniciarSesionAdmin.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataPaciente", listaPaciente);
            bundle.putSerializable("dataCitas", listaCita);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "El correo o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    public void redRegistrar(View view){
        Intent intent = new Intent(this, RegistroPacientes.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putSerializable("dataCitas", listaCita);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void redIniciarSesion(View view){
        Intent intent = new Intent(this, IniciarSesion.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putSerializable("dataCitas", listaCita);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
