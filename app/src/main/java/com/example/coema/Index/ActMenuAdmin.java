package com.example.coema.Index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coema.Index.MenuRecibosActivity;
import com.example.coema.R;

public class ActMenuAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_administrador);
    }

    // Método que se llama cuando se hace clic en la imagen de "Recibo de Pagos"
    public void onReciboDePagosClick(View view) {
        // Iniciar la actividad MenuRecibosActivity
        Intent intent = new Intent(this, MenuRecibosActivity.class);
        startActivity(intent);
    }
}
