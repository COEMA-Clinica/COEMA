/*package com.example.coema.NuevoPaciente;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.Modelos.DAOPaciente;

import java.util.ArrayList;
import java.util.Calendar;

public class ActNuevoPaciente extends AppCompatActivity {
    ArrayList<Paciente> listaPaciente;
    ArrayList<Citas> listaCita;
    int aActual;
    EditText txtNombre, txtCorreo, txtContra, txtFec;
    RadioButton rdbHombre, rdbMujer;
    DAOPaciente daoPaciente = new DAOPaciente(this);
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_paciente);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        daoPaciente.openBD();
        asignarReferencias();
        recuperarData();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre, correo, contra, fecha, sexo = "";
                int idFoto=0;
                nombre = txtNombre.getText().toString();
                correo = txtCorreo.getText().toString();
                contra = txtContra.getText().toString();
                fecha = txtFec.getText().toString();
                int nac = Integer.parseInt(fecha.substring(fecha.length() - 4));

                if(rdbHombre.isChecked())
                {
                    idFoto= R.drawable.hombre;
                    sexo = "hombre";
                }
                else if(rdbMujer.isChecked())
                {
                    idFoto= R.drawable.mujer;
                    sexo = "mujer";
                }

                if(!nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty() && !fecha.isEmpty() && (rdbHombre.isChecked() || rdbMujer.isChecked())){
                    if(aActual-nac>=18){
                        Paciente p = new Paciente(idFoto,nombre,correo,contra,fecha, sexo);

                        Bundle bundle  = new Bundle();
                        bundle.putInt("idFoto", idFoto);
                        bundle.putString("nombre", nombre);
                        bundle.putString("correo", correo);
                        bundle.putString("contra", contra);
                        bundle.putString("fecha", fecha);
                        bundle.putString("sexo", sexo);
                        bundle.putSerializable("listaPacientes", listaPaciente);
                        FragmentManager fragmentManager=getSupportFragmentManager();
                        DialogoAlertasRegistro da=new DialogoAlertasRegistro();

                        da.setArguments(bundle);
                        da.show(fragmentManager,"tagAlerta");
                        listaPaciente = daoPaciente.getPaciente();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Tiene que ser mayor de 18 años para registrarse", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getBaseContext(), "Por favor, llenar todos los campos", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void asignarReferencias(){
        txtNombre = findViewById(R.id.txtNomte);
        txtCorreo = findViewById(R.id.txtEmailR);
        txtContra = findViewById(R.id.txtContraR);
        txtFec = findViewById(R.id.txtFechaR);
        rdbHombre = findViewById(R.id.R);
        rdbMujer = findViewById(R.id.rdbMujerR);
        btnRegistrar = findViewById(R.id.btnRegistrar);
    }

    private void recuperarData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            listaPaciente = daoPaciente.getPaciente();
            listaCita = new ArrayList<>();
        }
        else
        {
            listaCita = (ArrayList<Citas>) bundle.getSerializable("dataCitas");
            listaPaciente = (ArrayList<Paciente>) bundle.getSerializable("dataPaciente");
        }
    }


    public void redIniciarSesion(View view){
        Intent intent = new Intent(this, ActIniciarSesion.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataPaciente", daoPaciente.getPaciente());
        bundle.putSerializable("dataCitas", listaCita);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    public void fechaNacimiento(View view){
        txtFec.setOnClickListener(this::fechaNacimiento);
        switch (view.getId()){
            case R.id.txtFechaR:
                mostrarSelector();
                break;
        }
    }

    private void mostrarSelector() {
        FragmentSelectorFecha nuevoFragmento = FragmentSelectorFecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int a, int m , int d) {
                Calendar calendario = Calendar.getInstance();
                aActual = calendario.get(Calendar.YEAR);

                final String fecha = d + "/" + (m+1) + "/" + a;
                txtFec.setText(fecha);
            }
        });
        nuevoFragmento.show(getSupportFragmentManager(),"selector");

    }
}
*/