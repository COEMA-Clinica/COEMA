package Perfil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MiPerfilActivity extends AppCompatActivity {

    private TextView textViewMiPerfil;
    private ImageView imageViewProfile;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextBirthdate;
    private EditText editTextIdentification;
    private EditText editTextGender;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        // Inicializar elementos de la interfaz
        textViewMiPerfil = findViewById(R.id.textViewMiPerfil);
        imageViewProfile = findViewById(R.id.imageViewProfile);
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
}