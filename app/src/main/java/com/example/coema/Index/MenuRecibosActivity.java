package com.example.coema.Index;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coema.Adapter.ReceiptAdapter;
import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Modelos.Receipt;
import com.example.coema.R;
import com.example.coema.Registro.RegistroPagoEdit;
import com.example.coema.Registro.RegitroPago;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuRecibosActivity extends AppCompatActivity {

    private Button editHistoryButton;
    private Button addPaymentButton;
    private RecyclerView recyclerView;
    private ReceiptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_recibos);

        // Encuentra las referencias a los botones
        editHistoryButton = findViewById(R.id.editHistoryButton);
        addPaymentButton = findViewById(R.id.addPaymentButton);

        // Configura el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceiptAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Configura los listeners de los botones
        editHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén el ID del recibo seleccionado directamente desde el adaptador
                long selectedReceiptId = adapter.getSelectedReceiptId();

                if (selectedReceiptId != -1) {
                    // Abre la actividad de edición y pasa el ID seleccionado
                    Intent intent = new Intent(MenuRecibosActivity.this, RegistroPagoEdit.class);
                    intent.putExtra("selectedReceiptId", selectedReceiptId);
                    startActivity(intent);
                } else {
                    // Mostrar un mensaje de que ningún recibo está seleccionado
                    Toast.makeText(MenuRecibosActivity.this, "Selecciona un recibo primero", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega aquí la lógica para el botón "Agregar Pago"
                // Por ejemplo, puedes abrir la actividad RegistroPagoActivity.
                Intent intent = new Intent(MenuRecibosActivity.this, RegitroPago.class);
                startActivity(intent);
            }
        });

        // Iniciar una tarea asincrónica para obtener datos de la base de datos
        new ObtenerDatosDeTablaAsyncTask().execute();
    }

    // Tarea asincrónica para obtener datos de la tabla
    private class ObtenerDatosDeTablaAsyncTask extends AsyncTask<Void, Void, List<Receipt>> {

        @Override
        protected List<Receipt> doInBackground(Void... voids) {
            List<Receipt> receipts = new ArrayList<>();

            try {
                // Obtener una conexión a la base de datos
                Connection conn = DatabaseConnection.getConnection();

                if (conn != null) {
                    // Ejecutar una consulta SQL para obtener datos
                    String consultaSQL = "SELECT id, name, date, amount FROM receipt";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(consultaSQL);

                    // Procesar los resultados de la consulta y agregarlos a la lista
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");
                        String date = resultSet.getString("date");
                        double amount = resultSet.getDouble("amount");

                        receipts.add(new Receipt(id, name, date, amount));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return receipts;
        }

        @Override
        protected void onPostExecute(List<Receipt> receiptList) {
            super.onPostExecute(receiptList);

            // Obtén una referencia al LinearLayout que contendrá las vistas de los recibos
            LinearLayout receiptsLinearLayout = findViewById(R.id.receiptsLinearLayout);

            // Limpia cualquier vista previamente agregada al LinearLayout
            receiptsLinearLayout.removeAllViews();

            // Verifica si receiptList no está vacío antes de mostrar los datos
            if (receiptList != null && !receiptList.isEmpty()) {
                // Itera a través de la lista de recibos y muestra cada uno
                for (Receipt receipt : receiptList) {
                    // Crea una nueva vista para mostrar el recibo
                    View receiptView = getLayoutInflater().inflate(R.layout.item_recibo, null);

                    // Obtén referencias a los TextViews en la vista del recibo
                    TextView nameTextView = receiptView.findViewById(R.id.nameTextView);
                    TextView dateTextView = receiptView.findViewById(R.id.dateTextView);
                    TextView amountTextView = receiptView.findViewById(R.id.amountTextView);

                    // Configura los TextViews con los datos del recibo actual
                    nameTextView.setText(receipt.getName());
                    dateTextView.setText(receipt.getDate());
                    amountTextView.setText(String.valueOf(receipt.getAmount()));

                    // Agrega la vista del recibo al LinearLayout
                    receiptsLinearLayout.addView(receiptView);
                }
            }
        }
    }
}
