package com.example.coema.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Login.IniciarSesionAdmin;
import com.example.coema.Perfil.PerfilPaciente;
import com.example.coema.R;

public class ActMenuOdonto extends AppCompatActivity {

    Button btnVerCitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.odontologo_principal);
        btnVerCitas=(Button)findViewById(R.id.btnVerCitas);
        btnVerCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVerCitas = new Intent(ActMenuOdonto.this, ListarCitas.class);
                Bundle bundle = new Bundle();
                iVerCitas.putExtras(bundle);
                startActivity(iVerCitas);          }
        });
    }


}
