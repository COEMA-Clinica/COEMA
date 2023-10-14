package com.example.coema.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Login.IniciarSesion;
import com.example.coema.R;

public class ActMenuOdonto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.odontologo_principal);
    }


    public void descansoListar(View view){
        Intent intent = new Intent(this, ListarDescanso.class);
        Bundle bundle=new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
