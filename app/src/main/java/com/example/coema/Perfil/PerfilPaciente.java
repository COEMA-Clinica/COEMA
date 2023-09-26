package com.example.coema.Perfil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.R;

public class PerfilPaciente extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName,  editTextBirthdate,
            editTextIdentification, editTextGender, editTextAddress, editTextPhoneNumber;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        // Inicializar elementos de la interfaz
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextBirthdate = findViewById(R.id.editTextBirthdate);
        editTextIdentification = findViewById(R.id.editTextIdentification);
        editTextGender = findViewById(R.id.editTextGender);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSave = findViewById(R.id.btnSave);

        // Puedes agregar aquí la lógica para manejar la entrada del usuario y la funcionalidad de guardado en la base de datos.

    }
    //try
    private void recuperarData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int idPaciente = bundle.getInt("data");
            // Ahora tienes el valor idPaciente que pasaste desde la actividad anterior
            // Puedes usarlo como sea necesario en esta actividad
        }
    }


}