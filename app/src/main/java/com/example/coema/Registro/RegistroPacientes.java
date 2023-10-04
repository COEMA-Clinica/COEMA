package com.example.coema.Registro;

import static com.example.coema.R.id.txtFechaR;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Fragments.FragmentSelectorFecha;
import com.example.coema.Login.IniciarSesion;
import com.example.coema.R;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistroPacientes extends AppCompatActivity {

    //Declaracion de variables
    EditText edtNom, edtApe, edtCor, edtCon, edtDni, edtTel, edtFec;
    TextView txtYaTienesCuenta;
    Spinner spnSexR;

    int aActual, dActual, mActual;
    int fecA, fecM, fecD;

    String spnSexVal[]={"Hombre", "Mujer"};
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_paciente);

        asignarReferencias();

    }

    //Declaracion de id's
    private void asignarReferencias() {

        edtCor = findViewById(R.id.txtEmailR);
        edtApe = findViewById(R.id.txtApeR);
        edtNom = findViewById(R.id.txtNomR);
        edtCon = findViewById(R.id.txtContraR);
        edtDni = findViewById(R.id.txtDniR);
        edtTel = findViewById(R.id.txtTelR);
        edtFec = findViewById(txtFechaR);

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
                    String sql = "INSERT INTO pacientes (id_paciente, nombres, apellidos, correo, contrasena, telefono, fecha_nacimiento, sexo) VALUES (?, ?, ?, ?, ?, ?, to_date(?,'dd-MM-yyyy'), ?)";

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

    public void fechaNacimiento(View view){
        edtFec.setOnClickListener(this::fechaNacimiento);
        if (view.getId() == R.id.txtFechaR) {
            mostrarSelector();
        }
    }

    private void mostrarSelector() {
        FragmentSelectorFecha nuevoFragmento = FragmentSelectorFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int a, int m , int d) {
                Calendar calendario = Calendar.getInstance();
                aActual = calendario.get(Calendar.YEAR);
                mActual = calendario.get(Calendar.MONTH);
                dActual = calendario.get(Calendar.DAY_OF_MONTH);


                final String fecha = d + "/" + (m+1) + "/" + a;
                fecA = a;
                fecM = m;
                fecD = d;
                edtFec.setText(fecha);
            }
        });
        nuevoFragmento.show(getSupportFragmentManager(),"selector");

    }



}