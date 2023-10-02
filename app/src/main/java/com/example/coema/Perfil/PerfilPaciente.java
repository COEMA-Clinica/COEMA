package com.example.coema.Perfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    int idPaciente;

    Paciente p=new Paciente();

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    ListView lstOpciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        asignarReferencias();
        recuperarData();
        obtenerDatos();


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
                OnclickButtonSelectImage();;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });
    }
    private void recuperarData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPaciente = bundle.getInt("data");
            // Ahora tienes el valor idPaciente que pasaste desde la actividad anterior
            // Puedes usarlo como sea necesario en esta actividad

        }

    }


    private Paciente buscarDato() {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PerfilPaciente.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                // La imagen fue capturada con la cámara
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap selectedImage = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(selectedImage);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                // La imagen fue seleccionada desde la galería
                Uri imageUri = data.getData();
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageView.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //obtener Foto
    private void OnclickButtonSelectImage() {
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String[] items={"Cámara","Galería"};
                AlertDialog.Builder alerta=
                        new AlertDialog.Builder(PerfilPaciente.this);

                alerta.setTitle("Seleccione la forma en que desea subir la imagen")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int item) {
                                switch (item){
                                    case 1:
                                        // Abre la cámara para tomar una foto
                                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                        }
                                        break;
                                    case 2:
                                        // Abre la galería para seleccionar una imagen
                                        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                                        break;
                                }
                            }
                        });
                AlertDialog titulo=alerta.create();
                titulo.setTitle("Subir Imagen");
                titulo.show();
            }
        });
    }
    private void obtenerDatos() {

        // Crear un nuevo hilo para realizar la operación de red
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection!= null) {
                        p = buscarDato(); // Reemplaza esto con tu lógica real de obtención de datos
                        if (p != null) {
                            editTextFirstName.setText(p.getNombre());
                            editTextLastName.setText(p.getApellido());
                            editTextIdentification.setText(p.getContra());
                            editTextBirthdate.setText(p.getFecNac());
                            editTextGender.setText(p.getSexo());
                            editTextPhoneNumber.setText(p.getTelefono());
                            editTextAddress.setText(p.getCorreo());
                            // Establecer otras propiedades de manera similar
                        } else {
                            // Manejar la situación en la que p es nulo
                            showError("No se pudo obtener el paciente");
                        }
                        // Realizar las operaciones de consulta y cierre de la conexión aquí

                        //sendToMainThread(recuperarData().intValue());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            });

        // Iniciar el hilo
        thread.start();
    }

    private void guardarDatos() {

        // Crear un nuevo hilo para realizar la operación de red
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    if (connection!= null) {
                        // Realizar las operaciones de consulta y cierre de la conexión aquí
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("UPDATE pacientes set id_paciente="+idPaciente
                                +", nombres="+editTextFirstName.getText().toString()
                                +", apellidos="+editTextLastName.getText().toString()
                                +", correo="+editTextAddress.getText().toString()
                                +", contrasena"+editTextIdentification.getText().toString()
                                +", telefono="+editTextPhoneNumber.getText().toString()
                                +", fecha_nacimiento=to_date("+editTextBirthdate.getText().toString()+",'yyyy-MM-dd')"
                                +", sexo="+editTextGender.getText().toString()
                                +",null) where id_paciente="+idPaciente);
                        sendToMainThread(idPaciente);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Manejar excepciones aquí (puedes mostrar un mensaje de error)
                    showError("Error al conectar a la base de datos");
                }
            }
        });

        // Iniciar el hilo
        thread.start();
    }

    private void showError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Mostrar un mensaje de error en el hilo principal
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToMainThread(final int idPaciente) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad o mostrar mensajes en el hilo principal
                Intent iRegistrar = new Intent(PerfilPaciente.this, PerfilPaciente.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data", idPaciente);
                iRegistrar.putExtras(bundle);
                startActivity(iRegistrar);
                Toast.makeText(getApplicationContext(), "SE HA LOGEADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                onPause();
            }
        });
    }



}