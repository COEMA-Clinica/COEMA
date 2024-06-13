package com.example.coema.Registro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Conection.GlobalVariables;
import com.example.coema.Perfil.PacientePrincipal;
import com.example.coema.R;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistroCitas extends AppCompatActivity {

    EditText edtFecCita, edtNomCita, edtApePatCita, edtApeMatCita;
    Spinner sprSexoCita, sprEspCita, sprDocCita, sprClinCita;
    Button btnRegCita, btnInicioCita;
    ArrayList<String> listaTratamientos = new ArrayList<>();
    ArrayList<String> listaHorarios = new ArrayList<>();
    private ArrayList<Integer> listaIdTratamientos = new ArrayList<>();
    String pacAct;
    int idActual = GlobalVariables.getInstance().getUserId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cita);
        asignarReferencias();
        new LoadEspecialidadesTask().execute();
    }

    private void asignarReferencias() {
        edtFecCita = findViewById(R.id.edtFecCita);
        edtNomCita = findViewById(R.id.edtNomCita);
        edtApePatCita = findViewById(R.id.edtApePatCita);
        edtApeMatCita = findViewById(R.id.edtApeMatCita);
        //sprSexoCita = findViewById(R.id.sprSexoCita);
        sprEspCita = findViewById(R.id.sprEspCita);
        //sprDocCita = findViewById(R.id.sprDocCita);
        sprClinCita = findViewById(R.id.sprClinCita);
        btnRegCita = findViewById(R.id.btnRegCita);
        btnInicioCita = findViewById(R.id.btnInicioCita);

        btnRegCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCita();
            }
        });

        edtFecCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorFecha();
            }
        });

        btnInicioCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroCitas.this, PacientePrincipal.class);
                startActivity(intent);
            }
        });
    }

    private void cargarListaHorarios(String fechaSeleccionada) {
        listaHorarios.clear();

        Calendar calendar = Calendar.getInstance();
        String[] diasSemana = {"", "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

        if (diaSemana == Calendar.SATURDAY) {
            for (int hora = 9; hora <= 14; hora++) {
                String horario = String.format("%02d:00", hora);
                listaHorarios.add(horario);
            }
        } else {
            for (int hora = 9; hora <= 16; hora += 2) {
                String horarioInicio = String.format("%02d:00", hora);
                String horarioFin = String.format("%02d:00", hora + 2);
                String horario = horarioInicio + " - " + horarioFin;
                listaHorarios.add(horario);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaHorarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprClinCita.setAdapter(adapter);
    }

    private void mostrarSelectorFecha() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                edtFecCita.setText(fechaSeleccionada);
                cargarListaHorarios(fechaSeleccionada);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        final Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, 1);
        final Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, 365);

        datePickerDialog.getDatePicker().init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                if (selectedDate.before(minDate) || selectedDate.after(maxDate)) {
                    view.updateDate(minDate.get(Calendar.YEAR), minDate.get(Calendar.MONTH), minDate.get(Calendar.DAY_OF_MONTH));
                } else if (selectedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    selectedDate.add(Calendar.DAY_OF_MONTH, 1);
                    view.updateDate(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
                }
            }
        });

        datePickerDialog.show();
    }

    private void registrarCita() {
        String nombre = edtNomCita.getText().toString();
        String apePat = edtApePatCita.getText().toString();
        String apeMat = edtApeMatCita.getText().toString();
//        String sexo = sprSexoCita.getSelectedItem().toString();
        String especialidad = sprEspCita.getSelectedItem().toString();
//        String odontologo = sprDocCita.getSelectedItem().toString();
        String fechaCita = edtFecCita.getText().toString();
        String horaCita = sprClinCita.getSelectedItem().toString();

        new InsertarCitaTask().execute(nombre, "pedro", especialidad, fechaCita, horaCita, apePat, apeMat, "masculino");
    }

    private class InsertarCitaTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String nombre = params[0];
           // String odontologo = params[1];
            String especialidad = params[2];
            String fecha = params[3];
            String hora = params[4];
            String apePat = params[5];
            String apeMat = params[6];
          //  String sexo = params[7];

            String[] parts = fecha.split("/");
            if (parts.length == 3) {
                String nuevaFecha = parts[2] + "-" + parts[1] + "-" + parts[0];

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        String insertQuery = "INSERT INTO cita (nombre, odontologo, tratamiento, hora, feccit, apepat, apemat, sexo,correo) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
                        preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, nombre);
                        preparedStatement.setString(2, "Javier");
                        preparedStatement.setString(3, especialidad);
                        preparedStatement.setString(4, "5:00");
                        preparedStatement.setString(5, nuevaFecha);
                        preparedStatement.setString(6, apePat);
                        preparedStatement.setString(7, apeMat);
                        preparedStatement.setString(8, "Masculino");
                        preparedStatement.setString(9, "aaa");

                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(RegistroCitas.this, "Inserción exitosa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegistroCitas.this, "No se pudo realizar la inserción", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class LoadEspecialidadesTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Connection connection = null;
            ArrayList<String> especialidadesOdontologicas = new ArrayList<>();

            try {
                connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    String query = "SELECT id_tratamiento, nombre FROM tratamientos";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        String especialidad = resultSet.getString("nombre");
                        int idTratamiento = resultSet.getInt("id_tratamiento");
                        especialidadesOdontologicas.add(especialidad);
                        listaIdTratamientos.add(idTratamiento);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return especialidadesOdontologicas;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null) {
                cargarListaEspecialidades(result);
            }
        }
    }

    private void cargarListaEspecialidades(ArrayList<String> especialidades) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, especialidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprEspCita.setAdapter(adapter);
    }

    private void cargarListaDoctores(ArrayList<String> doctores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprDocCita.setAdapter(adapter);
    }
}
