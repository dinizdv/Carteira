package com.example.carteira.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.carteira.db.DatabaseHelper;
import com.example.carteira.models.UsuarioModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsuarioRepository {
    private DatabaseHelper databaseHelper;

    public UsuarioRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long adicionarUsuario(UsuarioModel usuario) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataNascimentoFormatted = usuario.getData_nascimento().format(formatter);

            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_DATA_NASCIMENTO, dataNascimentoFormatted);
            values.put(DatabaseHelper.COLUMN_NOME, usuario.getNome());
            values.put(DatabaseHelper.COLUMN_MATRICULA, usuario.getMatricula());
            values.put(DatabaseHelper.COLUMN_CURSO, usuario.getCurso());
            values.put(DatabaseHelper.COLUMN_CPF, usuario.getCpf());
            values.put(DatabaseHelper.COLUMN_EMAIL, usuario.getEmail());
            values.put(DatabaseHelper.COLUMN_FOTO, usuario.getFoto());
            values.put(DatabaseHelper.COLUMN_NIVEL, usuario.getNivel());
            values.put(DatabaseHelper.COLUMN_ROLE, usuario.getRole());

            long id = db.insert(DatabaseHelper.TABLE_USUARIO, null, values);
            db.close();

            return id;
    }

    public UsuarioModel getByNome(String nome) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        UsuarioModel usuario = null;

        //WHERE
        String selecao = DatabaseHelper.COLUMN_CPF + " = ?";
        String[] argumentos = {nome};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USUARIO,
                null,
                selecao,
                argumentos,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {

            usuario = new UsuarioModel(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA_NASCIMENTO))),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MATRICULA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CURSO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CPF)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NIVEL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROLE))
            );
            cursor.close();
        }

        db.close();
        return usuario;
    }


}
