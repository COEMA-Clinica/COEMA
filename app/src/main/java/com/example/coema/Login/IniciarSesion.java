package com.example.coema.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Index.MainActivity;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.R;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class IniciarSesion extends AppCompatActivity {
    ArrayList<Paciente> listaPaciente;
    ArrayList<Citas> listaCita;
    EditText txtCorreo, txtContra;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        asignarReferencias();
        recuperarData();

        new DatabaseConnectionTask().execute();
    }

    private class DatabaseConnectionTask extends AsyncTask<Void, Void, Connection> {

        @Override
        protected Connection doInBackground(Void... voids) {
            try {
                return DatabaseConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Connection connection) {
            super.onPostExecute(connection);

            if (connection != null) {
                Log.d("ConexionBD", "Conexión a la base de datos realizada correctamente.");
                Toast.makeText(IniciarSesion.this, "Conexión realizada", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("ConexionBD", "Error al conectar a la base de datos.");
                Toast.makeText(IniciarSesion.this, "Conexión NO realizada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void asignarReferencias(){
        txtCorreo = findViewById(R.id.txtEmailR);
        txtContra = findViewById(R.id.txtContraR);
    }

    private boolean verificarRegistro(String correo, String contra){
        for(int i = 0; i < listaPaciente.size(); i++)
        {
            if(correo.equals(listaPaciente.get(i).getCorreo()) && contra.equals(listaPaciente.get(i).getContra())){
                return true;
            }
        }

        return false;
    }
    /*public void iniciarSesion(View view){
        String correo = txtCorreo.getText().toString();
        String contra = txtContra.getText().toString();
        Boolean registrado = verificarRegistro(correo, contra);

        if(registrado){
            Intent intent = new Intent(this, ActPrincipalPaciente.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataCitas", listaCita);
            bundle.putSerializable("dataPaciente", listaPaciente);
            bundle.putString("pacActivo", correo);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "El correo o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
        }
    }*/

    private void recuperarData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            listaPaciente = new ArrayList<>();
            listaCita = new ArrayList<>();
        } else {
            listaPaciente = (ArrayList<Paciente>) bundle.getSerializable("dataPaciente");
            listaCita = (ArrayList<Citas>) bundle.getSerializable("dataCitas");
        }
    }


    /*public void redRegistrar(View view){
        Intent intent = new Intent(this, ActNuevoPaciente.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putSerializable("dataCitas", listaCita);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/

    /*public void redIniciarSesionAdmin(View view){
        Intent intent = new Intent(this, ActIniciarSesionAdmin.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putSerializable("dataCitas", listaCita);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/
}
