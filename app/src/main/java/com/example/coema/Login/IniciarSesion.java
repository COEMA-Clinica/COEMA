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
import com.example.coema.Index.ActPrincipalPaciente;
import com.example.coema.Index.MainActivity;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.Perfil.PerfilPaciente;
import com.example.coema.R;
import com.example.coema.Registro.RegistroPacientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IniciarSesion extends AppCompatActivity {
    ArrayList<Paciente> listaPaciente;
    ArrayList<Citas> listaCita;
    EditText txtCorreo, txtContra;

    Connection connection;

    Button btnIniSe;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        asignarReferencias();

        //new DatabaseConnectionTask().execute();
    }

    //Conexion a la BD
   /* private class DatabaseConnectionTask extends AsyncTask<Void, Void, Connection> {

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
    }*/

    private void asignarReferencias(){
        txtCorreo = findViewById(R.id.txtEmailR);
        txtContra = findViewById(R.id.txtContraR);
        btnIniSe = (Button) findViewById(R.id.btnIniciarSesion);

        btnIniSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    }


    private void iniciarSesion() {
        final String correo = txtCorreo.getText().toString();
        final String contra = txtContra.getText().toString();

        // Crear un nuevo hilo para realizar la operación de red
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // Realizar la conexión a la base de datos y consultas aquí
                    connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        // Realizar las operaciones de consulta y cierre de la conexión aquí
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("SELECT id_paciente FROM pacientes where correo='" + correo + "' and " +
                                "contrasena='" + contra + "'");

                        if (rs.next()) {
                            // El usuario se ha autenticado correctamente
                            int idPaciente = rs.getInt("id_paciente");

                            // Enviar el ID del paciente al hilo principal para iniciar la actividad
                            sendToMainThread(idPaciente);
                        }else {
                            showError("Usuario no encontrado");
                        }

                        rs.close();
                        st.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Manejar excepciones aquí (puedes mostrar un mensaje de error)
                    showError("Error al conectar a la base de datos");
                }
            }
            });

        // Iniciar el hilo
        thread.start();
    }

    private void showError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Mostrar un mensaje de error en el hilo principal
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToMainThread(final int idPaciente) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad o mostrar mensajes en el hilo principal
                Intent iRegistrar = new Intent(IniciarSesion.this, PerfilPaciente.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data", idPaciente);
                iRegistrar.putExtras(bundle);
                startActivity(iRegistrar);
                Toast.makeText(getApplicationContext(), "SE HA LOGEADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cerrar la conexión a la base de datos aquí cuando se pausa la actividad
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
