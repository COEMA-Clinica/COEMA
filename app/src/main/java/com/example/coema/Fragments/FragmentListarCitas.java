package com.example.coema.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.R;

import java.util.ArrayList;

public class FragmentListarCitas  extends Fragment {

    ArrayList<Citas> citas;
    ArrayList<Paciente>paciente;
    String pacActivo;
    ListView lstCita;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_listar_cita,null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstCita=getView().findViewById(R.id.lstCitas);
        citas=new ArrayList<>();
        citas=(ArrayList<Citas>)getArguments().getSerializable("dataCitas");
        paciente=(ArrayList<Paciente>)getArguments().getSerializable("dataPaciente");
        pacActivo = getArguments().getString("pacActivo");

        ArrayList<String>citasE=new ArrayList<>();

        for(Citas cit:citas){
            if(cit.getCorreo().equals(pacActivo))
            {
                citasE.add(cit.getNom());
            }
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,citasE);
        lstCita.setAdapter(adapter);
        lstCita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((IListarCitas)getActivity()).listarCitas(citas.get(position));

            }
        });

    }
    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }
}
