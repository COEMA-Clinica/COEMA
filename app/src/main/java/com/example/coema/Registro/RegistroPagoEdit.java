package com.example.coema.Registro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Index.MenuRecibosActivity;
import com.example.coema.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroPagoEdit extends AppCompatActivity {

    private EditText patientEditText;
    private EditText dateEditText;
    private EditText amountEditText;
    private EditText reasonEditText;
    private Button updatePaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_pago_edit);

        // Obtener los datos ingresados por el usuario desde la actividad anterior
        Intent intent = getIntent();
        String paciente = intent.getStringExtra("paciente");
        String fecha = intent.getStringExtra("fecha");
        String monto = intent.getStringExtra("monto");
        String motivoCobro = intent.getStringExtra("motivoCobro");
        long selectedReceiptId = intent.getLongExtra("selectedReceiptId", -1);

        // Enlazar vistas
        patientEditText = findViewById(R.id.patientEditText);
        dateEditText = findViewById(R.id.dateEditText);
        amountEditText = findViewById(R.id.amountEditText);
        reasonEditText = findViewById(R.id.reasonEditText);
        updatePaymentButton = findViewById(R.id.addPaymentButton);

        // Establecer los valores de los EditText con los datos recibidos
        patientEditText.setText(paciente);
        dateEditText.setText(fecha);
        amountEditText.setText(monto);
        reasonEditText.setText(motivoCobro);

        // Configurar el botón para actualizar el pago
        updatePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos datos ingresados por el usuario
                String newPaciente = patientEditText.getText().toString();
                String newFecha = dateEditText.getText().toString();
                String newMonto = amountEditText.getText().toString();
                String newMotivoCobro = reasonEditText.getText().toString();

                // Iniciar una tarea asincrónica para actualizar el recibo en la base de datos
                new ActualizarReciboAsyncTask(selectedReceiptId, newPaciente, newFecha, newMonto, newMotivoCobro).execute();
            }
        });
    }

    // Tarea asincrónica para actualizar el recibo en la base de datos
    private class ActualizarReciboAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private long selectedReceiptId;
        private String newPaciente;
        private String newFecha;
        private String newMonto;
        private String newMotivoCobro;

        public ActualizarReciboAsyncTask(long selectedReceiptId, String newPaciente, String newFecha, String newMonto, String newMotivoCobro) {
            this.selectedReceiptId = selectedReceiptId;
            this.newPaciente = newPaciente;
            this.newFecha = newFecha;
            this.newMonto = newMonto;
            this.newMotivoCobro = newMotivoCobro;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Connection conn = null;
            PreparedStatement statement = null;

            try {
                conn = DatabaseConnection.getConnection();

                if (conn != null) {
                    // Query para actualizar el recibo
                    String query = "UPDATE receipt SET name = ?, date = ?, amount = ? WHERE id = ?";
                    statement = conn.prepareStatement(query);
                    statement.setString(1, newPaciente);
                    statement.setString(2, newFecha);
                    statement.setDouble(3, Double.parseDouble(newMonto));
                    statement.setLong(4, selectedReceiptId);

                    // Ejecutar la actualización
                    int rowsAffected = statement.executeUpdate();

                    // Verificar si se realizó la actualización correctamente
                    return rowsAffected > 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Cerrar la conexión y el statement
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                // Actualización exitosa
                Toast.makeText(RegistroPagoEdit.this, "Actualización realizada", Toast.LENGTH_LONG).show();

                // Redirigir de vuelta a MenuRecibosActivity
                Intent intent = new Intent(RegistroPagoEdit.this, MenuRecibosActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual para evitar que el usuario vuelva atrás
            } else {
                // Mostrar un mensaje de error si la actualización falla
                Toast.makeText(RegistroPagoEdit.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
