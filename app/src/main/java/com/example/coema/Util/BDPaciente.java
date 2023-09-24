package com.example.coema.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDPaciente extends SQLiteOpenHelper {
    public BDPaciente(@Nullable Context context) {
        super(context, ConstantesPaciente.NOMBREDBP, null, ConstantesPaciente.VERSIONP);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ConstantesPaciente.NOMBRETABLAP +
                " (id INTEGER primary key autoincrement, nombre TEXT not null, correo TEXT not null, contra TEXT not null, fecha TEXT not null, sexo TEXT not null, id_foto INTEGER not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.setVersion(oldVersion);
    }
}
