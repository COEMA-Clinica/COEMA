package com.example.coema.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Login.IniciarSesion;
import com.example.coema.R;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroPacientes extends AppCompatActivity {

    //Declaracion de variables
    EditText edtNom, edtApe, edtCor, edtCon, edtDni, edtTel, edtFec;
    TextView txtYaTienesCuenta;
    Spinner spnSexR;
    String spnSexVal[]={"Hombre", "Mujer"};
    Button btnRegistrar;

//    private static final String DB_URL = "jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema";
//    private static final String DB_USER = "codema_user";
//    private static final String DB_PASSWORD = "TL35jKeo53oDQkPDsw6c587xWAMSCmd6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_paciente);

        asignarReferencias();

    }

//    public void registrarPaciente(Integer id, String nombres, String apellidos,
//                                  String correo, String contra, String telefono,
//                                  String fechaNacimiento, String sexo) {
//        boolean exito = DatabaseConnection.insertarPaciente(id, nombres, apellidos, correo,contra,telefono,fechaNacimiento,sexo);
//
//        if (exito) {
//            // La inserción fue exitosa
//            Toast.makeText(this, "Paciente registrado con éxito", Toast.LENGTH_SHORT).show();
//        } else {
//            // Ocurrió un error al registrar el paciente
//            Toast.makeText(this, "Error al registrar al paciente", Toast.LENGTH_SHORT).show();
//        }
//    }
    //Conexion a la BD
   /*private class DatabaseConnectionTask extends AsyncTask<Void, Void, Connection> {

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
                Toast.makeText(RegistroPacientes.this, "Conexión realizada", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("ConexionBD", "Error al conectar a la base de datos.");
                Toast.makeText(RegistroPacientes.this, "Conexión NO realizada", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    /*public Connection conexionBD(){
        Connection conexion= null;
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            conexion= DriverManager.getConnection("jdbc:postgresql://dpg-ck7g9u7q54js73famlu0-a.oregon-postgres.render.com:5432/codema",
                    "codema_user", "TL35jKeo53oDQkPDsw6c587xWAMSCmd6");

        }catch(Exception e){
            Log.e("ConexionBD", "Error al conectar a la base de datos.");
            Toast.makeText(getApplicationContext(),"Conexion fallida",Toast.LENGTH_SHORT).show();

        }
        return conexion;
    }*/

    //Declaracion de id's
    private void asignarReferencias() {

        edtCor = findViewById(R.id.txtEmailR);
        edtApe = findViewById(R.id.txtApeR);
        edtNom = findViewById(R.id.txtNomR);
        edtCon = findViewById(R.id.txtContraR);
        edtDni = findViewById(R.id.txtDniR);
        edtTel = findViewById(R.id.txtTelR);
        edtFec = findViewById(R.id.txtFechaR);

        txtYaTienesCuenta = findViewById(R.id.txtYaTienesCuenta);

        spnSexR=findViewById(R.id.spnSexR);
        ArrayAdapter adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                spnSexVal);
        spnSexR.setAdapter(adapter);

        txtYaTienesCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLog = new Intent(RegistroPacientes.this, IniciarSesion.class);
                startActivity(iLog);
            }
        });

        btnRegistrar= findViewById(R.id.btnRegistrar);

        //Funcion del boton Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseConnectionTask().execute();
            }
        });

    }

    //Clase Asincrona para las conexion y el registro de pacientes
    private class DatabaseConnectionTask extends AsyncTask<Void, Void, Connection> {
       protected Connection doInBackground(Void... voids) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    String selectedGender = spnSexR.getSelectedItem().toString();
                    // Crear una sentencia SQL INSERT
                    String sql = "INSERT INTO pacientes (id_paciente, nombres, apellidos, correo, contrasena, telefono, fecha_nacimiento, sexo) VALUES (?, ?, ?, ?, ?, ?, to_date(?,'yyyy-MM-dd'), ?)";

                    // Crear un PreparedStatement
                    PreparedStatement pst = connection.prepareStatement(sql);

                    // Establecer los valores para los parámetros
                    pst.setString(1,edtDni.getText().toString());
                    pst.setString(2,edtNom.getText().toString());
                    pst.setString(3,edtApe.getText().toString());
                    pst.setString(4,edtCor.getText().toString());
                    pst.setString(5,edtCon.getText().toString());
                    pst.setString(6,edtTel.getText().toString());
                    pst.setString(7,edtFec.getText().toString());
                    pst.setString(8,selectedGender);

                    // Ejecutar la sentencia INSERT
                    int rowsAffected = pst.executeUpdate();

                    // Cerrar la conexión y el PreparedStatement
                    pst.close();
                    connection.close();

                    if (rowsAffected > 0) {
                        return connection;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

    }


   /*public void agregarUsuario(){
        try {
            Connection con=DatabaseConnection.getConnection();
            String selectedGender = spnSexR.getSelectedItem().toString();
            PreparedStatement pst=conexionBD().prepareStatement("insert into pacientes values(?,?,?,?,?,?,to_date(?,'YYYY-MM-DD'),?,null)");
            pst.setString(1,edtDni.getText().toString());
            pst.setString(2,edtNom.getText().toString());
            pst.setString(3,edtApe.getText().toString());
            pst.setString(4,edtCor.getText().toString());
            pst.setString(5,edtCon.getText().toString());
            pst.setString(6,edtTel.getText().toString());
            pst.setString(7,edtFec.getText().toString());
            pst.setString(8,selectedGender);

            pst.executeUpdate();
            Toast.makeText(getApplicationContext(),"REGISTRO AGREGADO",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }*/

}