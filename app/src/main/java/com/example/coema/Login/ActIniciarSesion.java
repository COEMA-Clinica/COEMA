package com.example.coema.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;

import java.util.ArrayList;

public class ActPrincipalPaciente extends AppCompatActivity {
    ArrayList<Citas> listaCitas;
    ArrayList<Paciente> listaPaciente;
    String pacActivo;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_principal);
        recuperarData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal_paciente,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataPaciente",listaPaciente);
        bundle.putSerializable("dataCitas", listaCitas);
        bundle.putString("pacActivo", pacActivo);
        Intent intent;
        switch (item.getItemId()){
            case R.id.mnu01D:
                intent = new Intent(this, ActRegistrarCita.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu02D:
                intent =new Intent(this, ActListarCitas.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu03D:
                intent = new Intent(this, ActInfo.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu04D:
                SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.clear();
                editor.apply();

                intent = new Intent(this, ActPrincipal.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void registrarCita(View view) {
        Intent intent = new Intent(this, ActRegistrarCita.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataCitas", listaCitas);
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putString("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void listarCitasPaciente(View view) {
        Intent intent = new Intent(this, ActListarCitas.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataCitas", listaCitas);
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putString("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void salirPrincipalPaciente(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, ActPrincipal.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataCitas", listaCitas);
        bundle.putSerializable("dataPaciente", listaPaciente);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void nosotrosPrincipalPaciente(View view) {
        Intent intent = new Intent(this, ActInfo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataCitas", listaCitas);
        bundle.putSerializable("dataPaciente", listaPaciente);
        bundle.putString("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void recuperarData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
        {
            listaCitas = new ArrayList<>();
            listaPaciente = new ArrayList<>();
            pacActivo = "";
        }
        else
        {
            listaCitas = (ArrayList<Citas>)  bundle.getSerializable("dataCitas");
            listaPaciente = (ArrayList<Paciente>) bundle.getSerializable("dataPaciente");
            pacActivo = bundle.getString("pacActivo");
        }
    }

}
