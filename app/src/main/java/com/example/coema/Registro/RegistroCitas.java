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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Perfil.PacientePrincipal;
import com.example.coema.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistroCitas extends AppCompatActivity {

    EditText edtFecCita;
    Spinner sprTratCita, sprHorCita, sprDocCita;  // Agregamos el Spinner para los doctores
    Button btnRegistrarCita;
    Button btnInicioCita;

    // Listas para las opciones de tratamientos, horarios y doctores
    ArrayList<String> listaTratamientos = new ArrayList<>();
    ArrayList<String> listaHorarios = new ArrayList<>();
    ArrayList<Integer> listaDoctoresConEspecialidad = new ArrayList<>(); // Lista de IDs de doctores



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cita);

        asignarReferencias();

        // Cargar las especialidades odontológicas desde la BD en un hilo en segundo plano
        new LoadEspecialidadesTask().execute();
    }

    private void asignarReferencias() {
        edtFecCita = findViewById(R.id.edtFecCita);
        sprTratCita = findViewById(R.id.sprTratCita);
        sprHorCita = findViewById(R.id.sprHorCita);
        sprDocCita = findViewById(R.id.sprDocCita); // Agregamos el Spinner para los doctores
        btnRegistrarCita = findViewById(R.id.btnRegistrar);
        btnInicioCita = findViewById(R.id.btnInicioCita);

        btnRegistrarCita.setOnClickListener(new View.OnClickListener() {
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

        sprTratCita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Cuando se selecciona una especialidad, consulta los doctores correspondientes
                int especialidadID = listaDoctoresConEspecialidad.get(position); // Obtiene el EspecialidadID
                new LoadDoctoresPorEspecialidadTask().execute(especialidadID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Puedes manejar esta situación si es necesario
            }
        });


        btnInicioCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se hace clic en el botón, redirige a la actividad InicioActivity
                Intent intent = new Intent(RegistroCitas.this, PacientePrincipal.class);
                startActivity(intent);
            }
        });
    }

    private void cargarListaHorarios(String fechaSeleccionada) {
        // Lógica para cargar los horarios en el Spinner
        listaHorarios.clear(); // Limpiamos la lista actual

        // Obtener el día de la semana de la fecha seleccionada
        Calendar calendar = Calendar.getInstance();
        String[] diasSemana = {"", "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

        // Si el día de la semana es sábado
        if (diaSemana == Calendar.SATURDAY) {
            // Cargar horarios de 1 hora desde las 9:00 AM hasta las 2:00 PM
            for (int hora = 9; hora <= 14; hora++) {
                String horario = String.format("%02d:00", hora);
                listaHorarios.add(horario);
            }
        } else {
            // Cargar horarios de 2 horas desde las 9:00 AM hasta las 6:00 PM
            for (int hora = 9; hora <= 16; hora += 2) {
                String horarioInicio = String.format("%02d:00", hora);
                String horarioFin = String.format("%02d:00", hora + 2);
                String horario = horarioInicio + " - " + horarioFin;
                listaHorarios.add(horario);
            }
        }

        // Actualizar el Spinner con la lista de horarios
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaHorarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprHorCita.setAdapter(adapter);
    }

    private void mostrarSelectorFecha() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Aquí obtienes la fecha seleccionada por el usuario (year, month, dayOfMonth) y la estableces en el EditText
                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                edtFecCita.setText(fechaSeleccionada);
                cargarListaHorarios(fechaSeleccionada);
            }
        }, year, month, day);

        // Establece la fecha mínima como la fecha actual
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Deshabilita la selección de los domingos
        final Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.DAY_OF_MONTH, 1); // Establece la fecha mínima para el día siguiente
        final Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DAY_OF_MONTH, 365); // Establece la fecha máxima para un año después

        datePickerDialog.getDatePicker().init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                if (selectedDate.before(minDate) || selectedDate.after(maxDate)) {
                    // Si se selecciona una fecha antes de la fecha mínima o después de la fecha máxima, restablecer la fecha mínima
                    view.updateDate(minDate.get(Calendar.YEAR), minDate.get(Calendar.MONTH), minDate.get(Calendar.DAY_OF_MONTH));
                } else if (selectedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    // Si se selecciona un domingo, aumentar la fecha en un día
                    selectedDate.add(Calendar.DAY_OF_MONTH, 1);
                    view.updateDate(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
                }
            }
        });

        datePickerDialog.show();
    }

    private void registrarCita() {
        int especialidadID = listaDoctoresConEspecialidad.get(sprTratCita.getSelectedItemPosition());
        int posicionSeleccionada = sprDocCita.getSelectedItemPosition();
        int doctorID = listaDoctoresConEspecialidad.get(posicionSeleccionada);
        String fechaCita = edtFecCita.getText().toString();
        String horaCita = sprHorCita.getSelectedItem().toString();

        // Realiza la inserción en la base de datos en un AsyncTask
        new InsertarCitaTask().execute(Integer.toString(especialidadID), String.valueOf(doctorID), fechaCita, horaCita);

    }

    private class InsertarCitaTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String especialidad = params[0];
            int doctor = Integer.parseInt(params[1]);
            String fecha = params[2];
            String hora = params[3];

            // Reformatea la fecha al formato "año-mes-día" (como se espera para un objeto Timestamp)
            String[] parts = fecha.split("/"); // Supongo que la fecha está en formato "día/mes/año"
            if (parts.length == 3) {
                String nuevaFecha = parts[2] + "-" + parts[1] + "-" + parts[0] + " 00:00:00"; // Establece la hora en 00:00:00// Añade la hora (en este caso, 00:00)

                // Realiza la inserción en la base de datos
                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DatabaseConnection.getConnection();
                    if (connection != null) {
                        String insertQuery = "INSERT INTO citas (id_paciente, id_doctor, id_tratamiento, fec_inic_cita, fec_fin_cita) " +
                                "VALUES (?, ?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, "73236940");
                        preparedStatement.setInt(2, doctor);
                        preparedStatement.setLong(3, Long.parseLong(especialidad));
                        preparedStatement.setTimestamp(4, Timestamp.valueOf(nuevaFecha)); // Usa la fecha reformateada
                        preparedStatement.setTimestamp(5, Timestamp.valueOf(nuevaFecha)); // Usa la misma fecha para inicio y fin

                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return false; // La fecha no tenía el formato esperado
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // La inserción se realizó con éxito, muestra un mensaje de éxito
                Toast.makeText(RegistroCitas.this, "Inserción exitosa", Toast.LENGTH_SHORT).show();
            } else {
                // Ocurrió un error durante la inserción o la conexión a la base de datos, muestra un mensaje de error
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
                    // Realizar la consulta para obtener las especialidades
                    String query = "SELECT EspecialidadID, NombreEspecialidad FROM EspecialidadesOdontologicas";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        String especialidad = resultSet.getString("NombreEspecialidad");
                        especialidadesOdontologicas.add(especialidad);

                        // Agregamos el ID de la especialidad a la lista de doctores
                        listaDoctoresConEspecialidad.add(resultSet.getInt("EspecialidadID"));
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


    private class LoadDoctoresPorEspecialidadTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            int especialidadID = params[0];
            Connection connection = null;
            ArrayList<String> doctoresPorEspecialidad = new ArrayList<>();

            try {
                connection = DatabaseConnection.getConnection();
                if (connection != null) {
                    // Realizar la consulta para obtener los doctores por especialidad
                    String query = "SELECT DoctorID, Nombre, Apellido FROM doctor " +
                            "WHERE DoctorID IN (SELECT DoctorID FROM DoctorEspecialidad WHERE EspecialidadID = " + especialidadID + ")";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        int doctorID = resultSet.getInt("DoctorID");
                        String nombre = resultSet.getString("Nombre");
                        String apellido = resultSet.getString("Apellido");
                        String nombreCompleto = nombre + " " + apellido;
                        doctoresPorEspecialidad.add(nombreCompleto);
                        listaDoctoresConEspecialidad.add(doctorID); // Agregar el ID del doctor a la lista
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return doctoresPorEspecialidad;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null) {
                cargarListaDoctores(result);
            }
        }
    }


    private void cargarListaEspecialidades(ArrayList<String> especialidades) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, especialidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprTratCita.setAdapter(adapter);
    }


    private void cargarListaDoctores(ArrayList<String> doctores) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprDocCita.setAdapter(adapter);
    }
}
