/* package com.example.coema.RegistrarCitas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.coema.Fragments.FragmentSelectorFecha;
import com.example.coema.Index.ActPrincipalPaciente;
import com.example.coema.Listas.ActListarCitas;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.R;

import java.util.ArrayList;
import java.util.Calendar;


public class ActRegistrarCita extends AppCompatActivity {
    ArrayList<Citas>listarCitas;
    ArrayList<Paciente> listaPaciente;
    String pacActivo;
    EditText edtNomCita,edtApePatCita,edtApeMatCita,edtFecCita;
    Spinner sprAteCita,sprOdoCita,sprSexoCita;
    //DAOCitas daoCitas=new DAOCitas(this);
    int aActual, dActual, mActual;
    int fecA, fecM, fecD;

    Button btnRegistrarCita;
    String sexo[]={"HOMBRE","MUJER"};
    String atencion[]={"ANESTESIOLOGÍA","CARDIO PEDIATRÍA","CIRUGÍA CABEZA-CUELLO","CIRUGÍA GENERAL"};
    String odontologos[]={"DUEÑAS CARBAJAL, ROY","MEDINA PALOMINA, FELIX","TELEZ FARFAN, DAVID","RODRIGUEZ VALENCIA, GERMAN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cita);
        asignarReferencia();
        //daoCitas.openBD();
        recuperarData();

        btnRegistrarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomCita,apePa,apeMa,fec,clini = null,esp,doc,sexo, correo;
                int idFoto=0;

                nomCita="Nombre: "+edtNomCita.getText().toString();
                apePa="Apellido Paterno: "+edtApePatCita.getText().toString();
                apeMa="Apellido Materno: "+edtApeMatCita.getText().toString();
                fec="Fecha: "+edtFecCita.getText().toString();
                sexo="Sexo: "+sprSexoCita.getSelectedItem().toString();
                doc="Odontólogo: "+sprOdoCita.getSelectedItem().toString();
                esp="Atención: "+sprAteCita.getSelectedItem().toString();
                correo = pacActivo;
                if (sprSexoCita.getSelectedItem().toString().equals("HOMBRE")){
                    idFoto= R.drawable.hombre;
                } else if (sprSexoCita.getSelectedItem().toString().equals("MUJER")){
                    idFoto= R.drawable.mujer;
                }

                if(fecA > aActual || (fecA == aActual && fecM > mActual) || (fecA == aActual && fecM == mActual && fecD >= dActual)) {
                    Citas c=new Citas(idFoto, nomCita, doc, clini, esp, fec, apePa, apeMa, sexo,correo);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogoAlertaRegistrarCita da = new DialogoAlertaRegistrarCita();

                    Bundle bundle = new Bundle();
                    bundle.putString("pacActivo", pacActivo);
                    da.setArguments(bundle);
                    da.show(fragmentManager, "tagAlerta");

                }
                else
                {
                    Toast.makeText(getBaseContext(), "Por favor registre una fecha valida", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void asignarReferencia() {
        edtNomCita=findViewById(R.id.edtNomCita);
        edtApeMatCita=findViewById(R.id.edtApeMatCita);
        edtApePatCita=findViewById(R.id.edtApePatCita);
        edtFecCita=findViewById(R.id.edtFecCita);
        sprAteCita=findViewById(R.id.sprTratCita);
        sprOdoCita=findViewById(R.id.sprOdoCita);
        sprSexoCita=findViewById(R.id.sprSexoCita);
        ArrayAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,sexo);
        sprSexoCita.setAdapter(adapter);
        ArrayAdapter adapterB=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,atencion);
        sprAteCita.setAdapter(adapterB);
        ArrayAdapter adapterC=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,odontologos);
        sprOdoCita.setAdapter(adapterC);

        btnRegistrarCita = findViewById(R.id.btnRegCita);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal_registrado,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataPaciente",listaPaciente);
        //bundle.putSerializable("dataCitas", daoCitas.getCitas());
        bundle.putString("pacActivo", pacActivo);
        Intent intent;
        switch (item.getItemId()){
            case R.id.mnu02R:
                intent =new Intent(this, ActListarCitas.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu04R:
                SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.clear();
                editor.apply();

                intent = new Intent(this, ActPrincipalPaciente.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void recuperarData() {
        Bundle bundle= getIntent().getExtras();
        if (bundle==null){
            listarCitas=new ArrayList<>();
            listaPaciente = new ArrayList<>();
            pacActivo = "";
        }else{
            listarCitas= (ArrayList<Citas>)bundle.getSerializable("dataCitas");
            listaPaciente = (ArrayList<Paciente>) bundle.getSerializable("dataPaciente");
             pacActivo = bundle.getString("pacActivo");
        }
    }



    public void regresarPrincipal(View view){
        Intent intent= new Intent(this, ActPrincipalPaciente.class);
        Bundle bundle= new Bundle();
        //bundle.putSerializable("dataCitas",daoCitas.getCitas());
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putString("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void fechaNacimiento(View view){
        edtFecCita.setOnClickListener(this::fechaNacimiento);
        switch (view.getId()){
            case R.id.edtFecCita:
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


}
*/