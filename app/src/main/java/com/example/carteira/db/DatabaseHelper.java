package com.example.carteira.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USUARIO = "usuario";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA_NASCIMENTO = "data_nascimento";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_MATRICULA = "matricula";
    public static final String COLUMN_CURSO = "curso";
    public static final String COLUMN_CPF = "cpf";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_NIVEL = "nivel";
    public static final String COLUMN_ROLE = "role";


    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_DATA_NASCIMENTO + " DATE," +
            COLUMN_NOME + " VARCHAR(100)," +
            COLUMN_MATRICULA + " VARCHAR(8)," +
            COLUMN_CURSO + " VARCHAR(100)," +
            COLUMN_CPF + " VARCHAR(15)," +
            COLUMN_EMAIL + " VARCHAR(70)," +
            COLUMN_FOTO + " BLOB," +
            COLUMN_NIVEL + " VARCHAR(100)," +
            COLUMN_ROLE + " VARCHAR(15)" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }
}
