package com.example.coema.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Index.MainActivity;
import com.example.coema.R;
import com.example.coema.Registro.RegistroPacientes;

public class Login extends AppCompatActivity {
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
    }

    //Metodos para cambiar de layout

    public void Ingresar(View view){
        Intent iInfo = new Intent(this, MainActivity.class);
        startActivity(iInfo);
    }
}
