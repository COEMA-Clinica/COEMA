package com.example.coema.Index;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.R;
import com.example.coema.Registro.RegistroPacientes;
import com.example.coema.Conection.DatabaseConnection;

import java.sql.Connection;


public class MainActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin =(Button) findViewById(R.id.btnLogin);


    }

    //Metodos para cambiar de layout
    public void Register(View view){
        Intent iReg = new Intent(this, RegistroPacientes.class);
        startActivity(iReg);
    }

    public void Login(View view){
        Intent iInfo = new Intent(this, RegistroPacientes.class);
        startActivity(iInfo);
    }
}
