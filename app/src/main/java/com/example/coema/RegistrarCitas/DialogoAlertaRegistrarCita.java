package com.example.coema.RegistrarCitas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.coema.R;


public class DialogoAlertaRegistrarCita extends DialogFragment {
    EditText edtNomCita, edtApePatCita, edtApeMatCita, edtFecCita;
    Spinner sprSexoCita,  sprClinCita, sprEspCita, sprDocCita;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=
                new AlertDialog.Builder(getActivity());

        edtNomCita = getActivity().findViewById(R.id.edtNomCita);
        edtApePatCita = getActivity().findViewById(R.id.edtApePatCita);
        edtApeMatCita = getActivity().findViewById(R.id.edtApeMatCita);
        edtFecCita = getActivity().findViewById(R.id.edtFecCita);
        sprSexoCita = getActivity().findViewById(R.id.sprSexoCita);
        sprClinCita = getActivity().findViewById(R.id.sprClinCita);
        sprEspCita =getActivity().findViewById(R.id.sprTratCita);
        sprDocCita = getActivity().findViewById(R.id.sprOdoCita);



        builder.setMessage("¿Estás seguro que deseas registrar la cita con los siguientes datos?")
                .setTitle("Confirmación")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Cita registrada", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();

                        String nomCita,apePa,apeMa,fec,clini,esp,doc,sexo, correo;
                        int idFoto=0;

                        nomCita="Nombre: "+edtNomCita.getText().toString();
                        apePa="Apellido Paterno: "+edtApePatCita.getText().toString();
                        apeMa="Apellido Materno: "+edtApeMatCita.getText().toString();
                        fec="Fecha: "+edtFecCita.getText().toString();
                        sexo="Sexo: "+sprSexoCita.getSelectedItem().toString();
                        clini="Clínica: "+sprClinCita.getSelectedItem().toString();
                        doc="Doctor: "+sprDocCita.getSelectedItem().toString();
                        esp="Especialidad: "+sprEspCita.getSelectedItem().toString();
                        correo = getArguments().getString("pacActivo");
                        if (sprSexoCita.getSelectedItem().toString().equals("HOMBRE")){
                            idFoto= R.drawable.hombre;
                        } else if (sprSexoCita.getSelectedItem().toString().equals("MUJER")){
                            idFoto= R.drawable.mujer;
                        }

                        /*DAOCitas daoCitas = new DAOCitas(getContext());
                        daoCitas.openBD();
                        Citas c = new Citas(idFoto, nomCita, doc, clini, esp, fec, apePa, apeMa, sexo, correo);
                        daoCitas.registrarCita(c);*/
                        Toast.makeText(getContext(), "Cita registrada Exitosamente", Toast.LENGTH_LONG).show();
                        limpiar();
                        dialogInterface.dismiss();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Cita cancelada", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    public void limpiar(){
        edtNomCita.setText("");
        edtApePatCita.setText("");
        edtApeMatCita.setText("");
        edtFecCita.setText("");
        sprSexoCita.setSelection(0);
        sprClinCita.setSelection(0);
        sprEspCita.setSelection(0);
        sprDocCita.setSelection(0);
    }

}
