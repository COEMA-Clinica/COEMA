package com.example.coema.Perfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import com.example.coema.Conection.DatabaseConnection;
import com.example.coema.Listas.Paciente;
import com.example.coema.Login.IniciarSesion;
import com.example.coema.R;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PerfilPaciente extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName,  editTextBirthdate,
            editTextIdentification, editTextGender, editTextAddress, editTextPhoneNumber;
    Button btnSave, btnSelect;

    private ImageView imageView;

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    ListView lstOpciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        asignarReferencias();
        recuperarData();
//        obtenerDatos();


        // Puedes agregar aquí la lógica para manejar la entrada del usuario y la funcionalidad de guardado en la base de datos.

    }


    public void asignarReferencias(){
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextBirthdate = findViewById(R.id.editTextBirthdate);
        editTextIdentification = findViewById(R.id.editTextIdentification);
        editTextGender = findViewById(R.id.editTextGender);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        imageView = (ImageView) findViewById(R.id.imPerfil);
        btnSave = findViewById(R.id.btnSave);
        btnSelect = findViewById(R.id.btnSeleccionarImagen);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnclickButtonSelectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        PerfilPaciente.ObtenerDatosDeTablaAsyncTask task = new PerfilPaciente.ObtenerDatosDeTablaAsyncTask(recuperarData());
        task.execute();
    }
    private Integer recuperarData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Integer idPaciente = Integer.valueOf(bundle.getInt("data"));
            // Ahora tienes el valor idPaciente que pasaste desde la actividad anterior
            // Puedes usarlo como sea necesario en esta actividad
            return idPaciente;
        }
        return null;
    }

    private class ObtenerDatosDeTablaAsyncTask extends AsyncTask<Void, Void, Paciente> {
        private Paciente paciente = new Paciente();

        private Integer idPaciente;

        public ObtenerDatosDeTablaAsyncTask(Integer idPaciente) {
            this.idPaciente=idPaciente;
        }

        @Override
        protected Paciente doInBackground(Void... voids) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM pacientes WHERE id_paciente='" + idPaciente+"'" );
                if (rs.next()) {
                    // El usuario se ha autenticado correctamente
                    String nom=rs.getString("nombres");
                    paciente.setNombre(nom);
                    String ape=rs.getString("apellidos");
                    paciente.setApellido(ape);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fecNac=sdf.format(rs.getDate("fecha_nacimiento"));
                    paciente.setFecNac(fecNac);
                    String cor=rs.getString("correo");
                    paciente.setCorreo(cor);
                    String con=rs.getString("contrasena");
                    paciente.setContra(con);
                    String gen=rs.getString("sexo");
                    paciente.setSexo(gen);
                    String tel=rs.getString("telefono");
                    paciente.setTelefono(tel);

                    editTextFirstName.setText(paciente.getNombre());
                    editTextLastName.setText(paciente.getApellido());
                    editTextIdentification.setText(paciente.getContra());
                    editTextBirthdate.setText(paciente.getFecNac());
                    editTextGender.setText(paciente.getSexo());
                    editTextPhoneNumber.setText(paciente.getTelefono());
                    editTextAddress.setText(paciente.getCorreo());
                }
                return paciente;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /*@Override
        protected void onPostExecute(Integer idPaciente) {
            super.onPostExecute(idPaciente);

            if (idPaciente != null) {
                // Haz lo que necesites con idPaciente
                Intent iRegistrar = new Intent(PerfilPaciente.this, PerfilPaciente.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data", idPaciente);
                iRegistrar.putExtras(bundle);
                startActivity(iRegistrar);
                Toast.makeText(getApplicationContext(), "SE HA LOGEADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();

            }
        }*/


    }

    /*private Paciente buscarDato() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement st = connection.createStatement();
            Paciente pa= new Paciente();
            ResultSet rs = st.executeQuery("SELECT * FROM pacientes WHERE id_paciente='" + idPaciente+"'" );
            if (rs.next()) {
            // El usuario se ha autenticado correctamente
                String nom=rs.getString("nombres");
                pa.setNombre(nom);
                String ape=rs.getString("apellidos");
                pa.setApellido(ape);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fecNac=sdf.format(rs.getDate("fecha_nacimiento"));
                pa.setFecNac(fecNac);
                String cor=rs.getString("correo");
                pa.setCorreo(cor);
                String con=rs.getString("contrasena");
                pa.setContra(con);
                String gen=rs.getString("sexo");
                pa.setSexo(gen);
                String tel=rs.getString("telefono");
                pa.setTelefono(tel);

                Log.d("TAG", "Nombre: " + nom);
                Log.d("TAG", "Apellido: " + ape);
            }
            return pa;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }*/

    //obtener Foto
    private void OnclickButtonSelectImage() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar una opción");
        builder.setItems(new CharSequence[]{"Cámara", "Galería"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        checkCameraPermission();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        builder.show();

    }
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(this, "La cámara no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // La imagen de la cámara se capturó exitosamente
                // Puedes manejar la imagen aquí
                // Por ejemplo, puedes obtener la imagen de data y mostrarla en un ImageView
                // Bundle extras = data.getExtras();
                // Bitmap imageBitmap = (Bitmap) extras.get("data");
            } else if (requestCode == REQUEST_GALLERY) {
                // La imagen de la galería se seleccionó exitosamente
                // La URI de la imagen seleccionada se encuentra en data.getData()
                Uri selectedImageUri = data.getData();
                // Puedes manejar la URI aquí
            }
        }
    }

}