package com.example.coema.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.coema.Admin.DialogoAlertaEditarPac;
import com.example.coema.Admin.DialogoAlertaEliminarPac;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.Modelos.DAOPaciente;
import com.example.coema.R;

import java.util.ArrayList;


public class FragmentPacienteItemAdmin extends Fragment {
    EditText txtCor, txtNom;
    ImageView imgFoto;

    int id;

    ArrayList<Citas> citas;
    ArrayList<Paciente>pacientes;


    Button btnEditar, btnEliminar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_pacientes_admin,container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pacientes = new ArrayList<>();
        pacientes = (ArrayList<Paciente>) getArguments().getSerializable("dataPaciente");
        citas=(ArrayList<Citas>)getArguments().getSerializable("dataCitas");

        imgFoto = getView().findViewById(R.id.imgPaciente);
        txtNom = getView().findViewById(R.id.txtNom);
        txtCor = getView().findViewById(R.id.txtCorreo);


        btnEditar = getView().findViewById(R.id.btnEditarPaciente);
        btnEliminar = getView().findViewById(R.id.btnEliminarPaciente);

        DAOPaciente daoPaciente = new DAOPaciente(getActivity());
        daoPaciente.openBD();

        btnEliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                daoPaciente.eliminarPaciente(id);
                Intent intent = getActivity().getIntent();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                DialogoAlertaEliminarPac da= new DialogoAlertaEliminarPac();
                da.show(fragmentManager,"tagAlerta");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = 0;
                for(int i = 0; i < pacientes.size(); i++){
                    if(pacientes.get(i).getId() == id){
                        pos = i;
                    }
                }
                Paciente paciente = pacientes.get(pos);
                paciente.setId(id);
                paciente.setCorreo(txtCor.getText().toString());
                paciente.setNombre(txtNom.getText().toString());
                daoPaciente.editarPaciente(paciente);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                DialogoAlertaEditarPac da= new DialogoAlertaEditarPac();
                da.show(fragmentManager,"tagAlerta");
            }
        });
    }




    public void mostrarPacientesAdmin(Paciente paciente){
        imgFoto.setImageResource(paciente.getIdFoto());
        txtNom.setText(paciente.getNombre());
        txtCor.setText(paciente.getCorreo());
    }

    public void capturarId(Paciente paciente){
        id = paciente.getId();

    }


}
