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

public class FragmentPacienteListaAdmin extends Fragment {
    ArrayList<Citas>citas;
    ArrayList<Paciente>pacientes;
    ListView lstPaciente;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_pacientes_admin, null);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstPaciente = getView().findViewById(R.id.lstPacienteAdmin);
        pacientes = new ArrayList<>();
        pacientes = (ArrayList<Paciente>) getArguments().getSerializable("dataPaciente");
        citas=(ArrayList<Citas>)getArguments().getSerializable("dataCitas");
        ArrayList<String> pacienteE = new ArrayList<>();


        for(Paciente paciente: pacientes)
        {
            pacienteE.add(paciente.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, pacienteE);
        lstPaciente.setAdapter(adapter);
        lstPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ((IListarPacienteAdmin)getActivity()).mostrarListadoPaciente(pacientes.get(position));
                ((IListarPacienteAdmin)getActivity()).capturarId(pacientes.get(position));

            }
        });


    }


}
