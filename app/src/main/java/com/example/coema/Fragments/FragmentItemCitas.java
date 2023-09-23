package com.example.coema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coema.Listas.Citas;
import com.example.coema.R;

public class FragmentItemCitas extends Fragment {
    TextView txtNom,txtApeP,txtFechaCita,txtOdonto,txtHora,txtAtencion;
    ImageView imgFotos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_item_cita,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imgFotos= getView().findViewById(R.id.imgFoto);
        txtNom=getView().findViewById(R.id.txtNomItem);
        txtApeP=getView().findViewById(R.id.txtApePaItem);
        txtAtencion=getView().findViewById(R.id.txtAtencionItem);
        txtOdonto=getView().findViewById(R.id.txtOdontoItem);
        txtFechaCita=getView().findViewById(R.id.txtFechaCitaItem);
        txtHora=getView().findViewById(R.id.txtHoraItem);

    }


    public void mostrarLista(Citas citas){
        imgFotos.setImageResource(citas.getIdFoto());
        txtNom.setText(citas.getNom());
        txtApeP.setText(citas.getApePat());
        txtAtencion.setText(citas.getAte());
        txtOdonto.setText(citas.getOdo());
        txtFechaCita.setText(citas.getFecCit());
        txtHora.setText(citas.getHor());
    }
}
