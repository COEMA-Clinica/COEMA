package com.example.coema.Listas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Fragments.FragmentItemCitas;
import com.example.coema.Fragments.FragmentListarCitas;
import com.example.coema.Fragments.IListarCitas;
import com.example.coema.Index.ActPrincipalPaciente;
import com.example.coema.Modelos.DAOCitas;
import com.example.coema.R;
import com.example.coema.RegistrarCitas.ActRegistrarCita;

import java.util.ArrayList;

import healthandtechnology.com.healthandtechnologyapp.modelos.DAOCitas;

public class ActListarCitas extends AppCompatActivity implements IListarCitas {
    FragmentListarCitas fragmentCitasListar;
    FragmentItemCitas fragmentCitasItem;
    ArrayList<Citas>listadoCitas;
    ArrayList<Paciente> listadoPaciente;
    String pacActivo;

//DAOCitas daoCitas=new DAOCitas(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_cita_principal);

        asignarReferencia();
        recuperarData();
    }

    private void asignarReferencia() {
        fragmentCitasListar=(FragmentListarCitas)getSupportFragmentManager().findFragmentById(R.id.fgtListarPrincipal) ;
        fragmentCitasItem=(FragmentItemCitas) getSupportFragmentManager().findFragmentById(R.id.fgtItemPrin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal_listar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataPaciente",listadoPaciente);
        bundle.putSerializable("dataCitas", listadoCitas);
        bundle.putSerializable("pacActivo", pacActivo);
        Intent intent;
        switch (item.getItemId()){
            case R.id.mnu01D:
                intent = new Intent(this, ActRegistrarCita.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu04D:
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
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            Log.d("Status:","No hay datos registrados");
            Toast.makeText(this, "Falta registrar datos", Toast.LENGTH_SHORT).show();
        }else{

            listadoCitas=(ArrayList<Citas>)bundle.getSerializable("dataCitas");
            listadoPaciente = (ArrayList<Paciente>)  bundle.getSerializable("dataPaciente");
            pacActivo = bundle.getString("pacActivo");

            Bundle listaCitasAdminFragment=new Bundle();
            listaCitasAdminFragment.putSerializable("dataCitas",listadoCitas);
            listaCitasAdminFragment.putSerializable("dataPaciente",listadoPaciente);
            listaCitasAdminFragment.putSerializable("pacActivo", pacActivo);

            fragmentCitasItem.setArguments(listaCitasAdminFragment);
            fragmentCitasListar.setArguments(listaCitasAdminFragment);
            fragmentCitasItem.setArguments(listaCitasAdminFragment);

        }
    }

    public void regresarMenuPrincipal(View view){
        Intent intent= new Intent(this,ActPrincipalPaciente.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("dataCitas",listadoCitas);
        bundle.putSerializable("dataPaciente", listadoPaciente);
        bundle.putSerializable("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void listarCitas(Citas citas) {
        fragmentCitasItem.mostrarLista(citas);
    }




}
