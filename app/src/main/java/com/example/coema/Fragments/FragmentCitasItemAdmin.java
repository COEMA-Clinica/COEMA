/* package com.example.coema.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.coema.Admin.DialogoAlertaEditarCit;
import com.example.coema.Admin.DialogoAlertaEliminarCit;
import com.example.coema.Index.ActPrincipalAdmin;
import com.example.coema.Listas.Citas;
import com.example.coema.Listas.Paciente;
import com.example.coema.Modelos.DAOCitas;
import com.example.coema.R;

import java.util.ArrayList;


public class FragmentCitasItemAdmin extends Fragment {
     TextView txtNom,txtApeP,txtApeM,txtSexo,txtFecha,txtClinica,txtEspecialidad,txtDocto;
    ImageView imgFoto;
    int id;
    String pacActivo;
    ArrayList<Citas> citas;
    ArrayList<Paciente>pacientes;
    Button btnEditar,btnBorrar,btnInicio;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_item_citas_admi,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        citas = new ArrayList<>();
        citas=(ArrayList<Citas>)getArguments().getSerializable("dataCitas");
        pacientes = (ArrayList<Paciente>) getArguments().getSerializable("dataPaciente");
        pacActivo=getArguments().getString("pacActivo");

        imgFoto=getView().findViewById(R.id.imgAdmin);
        txtNom=getView().findViewById(R.id.txtNomte);
        txtApeP=getView().findViewById(R.id.txtApelliPater);
        txtApeM=getView().findViewById(R.id.txtApelliMater);
        txtSexo=getView().findViewById(R.id.txtSexItem);
        txtClinica=getView().findViewById(R.id.txtClinIicatem);
        txtEspecialidad=getView().findViewById(R.id.txtEspecialidadItem);
        txtDocto=getView().findViewById(R.id.txtDoctItem);
        txtFecha=getView().findViewById(R.id.txtFechaCitaItem);

        btnEditar = getView().findViewById(R.id.btnEditarCitas);
        btnBorrar = getView().findViewById(R.id.btnBorrarCitas);
        btnInicio= getView().findViewById(R.id.btnRegresarAd);
        DAOCitas daoCitas = new DAOCitas(getActivity());
        daoCitas.openBD();

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), ActPrincipalAdmin.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("dataCitas",citas);
                bundle.putSerializable("dataPaciente", pacientes);
                bundle.putSerializable("pacActivo", pacActivo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                daoCitas.eliminarCita(id);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                DialogoAlertaEliminarCit da= new DialogoAlertaEliminarCit();
                da.show(fragmentManager,"tagAlerta");
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = 0;
                for(int i = 0; i < citas.size(); i++){
                    if(citas.get(i).getId() == id){
                        pos = i;
                    }
                }

                Citas cita = citas.get(pos);
                cita.setId(id);

                cita.setNom(txtNom.getText().toString());
                cita.setApeMat(txtApeM.getText().toString());

                cita.setApePat(txtApeP.getText().toString());

                cita.setFecCit(txtFecha.getText().toString());
                cita.setSexo(txtSexo.getText().toString());

                if(txtSexo.getText().toString().equals("Sexo: HOMBRE"))
                {
                    cita.setIdFoto(R.drawable.hombre);
                }
                else
                {
                    cita.setIdFoto(R.drawable.mujer);
                }
                cita.setCorreo(pacActivo);


                daoCitas.editarCita(cita);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                DialogoAlertaEditarCit da= new DialogoAlertaEditarCit();
                da.show(fragmentManager,"tagAlerta");


            }
        });


    }

    public void mostrarCitasAdmin(Citas citas){
        imgFoto.setImageResource(citas.getIdFoto());
        txtNom.setText(citas.getNom());
        txtApeM.setText(citas.getApeMat());
        txtApeP.setText(citas.getApePat());
        txtSexo.setText(citas.getSexo());
        txtFecha.setText(citas.getFecCit());

    }

    public void capturaId(Citas citas){
        id = citas.getId();

    }

}
*/