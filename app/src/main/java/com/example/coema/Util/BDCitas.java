package com.example.coema.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDCitas extends SQLiteOpenHelper {

    public BDCitas(@Nullable Context context) {
        super(context, ConstantesCitas.NOMBREDBC, null, ConstantesCitas.VERSIONC);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ConstantesCitas.NOMBRETABLAC +
        " (id INTEGER primary key autoincrement,id_foto INTEGER not null, nom TEXT not null, doc TEXT not null, clin TEXT not null, esp TEXT not null, fecCit TEXT not null, apePat TEXT not null, apeMat TEXT not null, sexo TEXT not null, correo TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
