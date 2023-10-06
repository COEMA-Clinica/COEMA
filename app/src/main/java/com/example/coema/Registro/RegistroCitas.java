package com.example.coema.Registro;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Fragments.FragmentSelectorFecha;
import com.example.coema.Listas.Doctor;
import com.example.coema.Listas.Paciente;
import com.example.coema.Listas.Tratamientos;
import com.example.coema.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistroCitas extends AppCompatActivity {

    EditText edtDniCita, edtFecCita;
    Spinner sprHorCita, sprTratCita, sprDocCita;
    Button btnRegistrarCita;

    ArrayList<Doctor> listaDoctor;
    ArrayList<Tratamientos> listaTratamiento;

    int aActual, dActual, mActual;
    int fecA, fecM, fecD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cita);

        asignarReferencias();

        // Aquí debes cargar la lista de doctores desde tu fuente de datos (base de datos, etc.)
        cargarListaDoctores();
        cargarListaTratamientos();
        cargarListaHorarios();
    }

    // Declaracion de id's
    private void asignarReferencias() {
        edtDniCita = findViewById(R.id.edtNomCita);
        sprTratCita = findViewById(R.id.sprTratCita);
        sprDocCita = findViewById(R.id.sprDocCita);
        sprHorCita = findViewById(R.id.sprHorCita);
        edtFecCita=findViewById(R.id.edtFecCita);
        btnRegistrarCita = findViewById(R.id.btnRegistrar);

        // Funcion del boton Registrar
        btnRegistrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseConnectionTask().execute();
            }
        });
    }

    public void fechaNacimiento(View view){
        edtFecCita.setOnClickListener(this::fechaNacimiento);
        if (view.getId() == R.id.edtFecCita) {
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
                edtFecCita.setText(fecha);
            }
        });
        nuevoFragmento.show(getSupportFragmentManager(),"selector");

    }

    // Método para cargar la lista de doctores (debe ser implementado)
    private void cargarListaDoctores() {
        // Aquí debes obtener la lista de doctores desde tu fuente de datos (base de datos, etc.)
        // y luego configurar el adaptador una vez que los datos estén disponibles.
        // Por simplicidad, se asume que ya tienes la lista de doctores en 'listaDoctor'.
        ArrayList<String> nombresDoctores = new ArrayList<>();
        for (Doctor doctor : listaDoctor) {
            nombresDoctores.add(doctor.getNom());
        }

        // Crea un ArrayAdapter para el Spinner utilizando la lista de nombres de doctores
        ArrayAdapter<String> adapterDoctores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresDoctores);
        adapterDoctores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configura el Spinner para utilizar el ArrayAdapter
        sprDocCita.setAdapter(adapterDoctores);
    }

    private void cargarListaTratamientos() {
        // Aquí debes obtener la lista de doctores desde tu fuente de datos (base de datos, etc.)
        // y luego configurar el adaptador una vez que los datos estén disponibles.
        // Por simplicidad, se asume que ya tienes la lista de doctores en 'listaDoctor'.
        ArrayList<String> nombresTratamientos = new ArrayList<>();
        for (Tratamientos tratamientos : listaTratamiento) {
            nombresTratamientos.add(tratamientos.getNom());
        }

        // Crea un ArrayAdapter para el Spinner utilizando la lista de nombres de doctores
        ArrayAdapter<String> adapterT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresTratamientos);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configura el Spinner para utilizar el ArrayAdapter
        sprTratCita.setAdapter(adapterT);
    }

    private void cargarListaHorarios() {
        // Aquí debes obtener la lista de doctores desde tu fuente de datos (base de datos, etc.)
        // y luego configurar el adaptador una vez que los datos estén disponibles.
        // Por simplicidad, se asume que ya tienes la lista de doctores en 'listaDoctor'.
        ArrayList<String> horarios = new ArrayList<>();
        for (Tratamientos tratamientos : listaTratamiento) {
            horarios.add(tratamientos.getNom());
        }

        // Crea un ArrayAdapter para el Spinner utilizando la lista de nombres de doctores
        ArrayAdapter<String> horariosC = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horarios);
        horariosC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configura el Spinner para utilizar el ArrayAdapter
        sprHorCita.setAdapter(horariosC);
    }



    //Clase Asincrona para las conexion y el registro de pacientes
    private class DatabaseConnectionTask extends AsyncTask<Void, Void, Connection> {
        protected Connection doInBackground(Void... voids) {
            try {
                Connection connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    // Crear una sentencia SQL INSERT
                    String sql = "INSERT INTO citas ( id_paciente, id_doctor, id_tratamiento, fec_inic_cita, fec_fin_cita) VALUES ( ?, ?, ?, to_date(?,'dd-MM-yyyy'), to_date(?, 'HH24:MI:SS'))";

                    // Crear un PreparedStatement
                    PreparedStatement pst = connection.prepareStatement(sql);

                    // Establecer los valores para los parámetros
                    pst.setString(1,edtDniCita.getText().toString());
                    pst.setString(2,sprDocCita.getSelectedItem().toString());
                    pst.setString(3,sprTratCita.getSelectedItem().toString());
                    pst.setString(4,edtFecCita.getText().toString());
                    pst.setString(5,sprHorCita.getSelectedItem().toString());

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
    private class ObtenerDatosDeTablaAsyncTask extends AsyncTask<Void, Void, Paciente> {
        private Paciente paciente = new Paciente();

        private Integer idPaciente;

        public ObtenerDatosDeTablaAsyncTask(Integer idPaciente) {
            this.idPaciente = idPaciente;
        }

        @Override
        protected Paciente doInBackground(Void... voids) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM pacientes WHERE id_paciente='" + idPaciente + "'");
                if (rs.next()) {
                    // El usuario se ha autenticado correctamente
                    String dni = rs.getString("id_paciente");
                    paciente.setNombre(dni);

                    edtDniCita.setText(paciente.getNombre());
                }
                return paciente;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}