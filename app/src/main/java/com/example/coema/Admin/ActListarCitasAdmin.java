/* package com.example.coema.Admin;

import static com.example.coema.R.id.mnu03D;

import android.annotation.SuppressLint;
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

import com.example.coema.Fragments.FragementCitasListarAdmin;
import com.example.coema.Fragments.FragmentCitasItemAdmin;
import com.example.coema.Fragments.IListarCitasAdmin;
import com.example.coema.Index.ActPrincipal;
import com.example.coema.Index.ActPrincipalAdmin;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.Modelos.DAOCitas;
import com.example.coema.R;

import java.util.ArrayList;


public class ActListarCitasAdmin extends AppCompatActivity implements IListarCitasAdmin {
     FragementCitasListarAdmin fragementCitasListarAdmin;
     FragmentCitasItemAdmin fragmentCitasItemAdmin;
        ArrayList<Citas>listadoCitas =new ArrayList<>();
        ArrayList<Paciente> listadoPaciente;
        String pacActivo;
        DAOCitas daoCitas=new DAOCitas(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_citas__admin_principal);
        asignarReferencia();
        daoCitas.openBD();
        recuperarData();
    }

    private void asignarReferencia() {
        fragementCitasListarAdmin=(FragementCitasListarAdmin)getSupportFragmentManager().findFragmentById(R.id.fgtListarPrincipal) ;
        fragmentCitasItemAdmin=(FragmentCitasItemAdmin)getSupportFragmentManager().findFragmentById(R.id.fgtItemPrin);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_principal_listar_admin,menu);
            return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataPaciente",listadoPaciente);
        bundle.putSerializable("dataCitas", listadoCitas);
        bundle.putSerializable("pacActivo", pacActivo);
        Intent intent;
        switch (item.getItemId()){
            case R.id.mnu01D:
                intent = new Intent(this, ActListarPacientesAdmin.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu03D:
                intent = new Intent(this, ActInfoAdmin.class);

                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.mnu04D:
                SharedPreferences sharedPref = this.getSharedPreferences("correo-electronico", Context.MODE_PRIVATE);
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

    private void recuperarData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            Log.d("Status:","No hay datos registrados");
            Toast.makeText(this, "Falta registrar datos", Toast.LENGTH_SHORT).show();
        }else{
            listadoCitas= daoCitas.getCitas();
            listadoPaciente = (ArrayList<Paciente>)  bundle.getSerializable("dataPaciente");
            pacActivo=bundle.getString("pacActivo");
            Bundle listaCitasAdminFragment=new Bundle();
            listaCitasAdminFragment.putSerializable("dataCitas",listadoCitas);
            listaCitasAdminFragment.putSerializable("dataPaciente",listadoPaciente);
            listaCitasAdminFragment.putString("pacActivo", pacActivo);
            fragementCitasListarAdmin.setArguments(listaCitasAdminFragment);
            fragmentCitasItemAdmin.setArguments(listaCitasAdminFragment);

        }
    }

    public void regresarMenuAdmin(View view){
        Intent intent= new Intent(this, ActPrincipalAdmin.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("dataCitas",listadoCitas);
        bundle.putSerializable("dataPaciente", listadoPaciente);
        bundle.putSerializable("pacActivo", pacActivo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void mostrarListadoCitas(Citas citas){
        fragmentCitasItemAdmin.mostrarCitasAdmin(citas);
    }

    @Override
    public void capturarId(Citas citas) {
        fragmentCitasItemAdmin.capturaId(citas);

        }


}
*/