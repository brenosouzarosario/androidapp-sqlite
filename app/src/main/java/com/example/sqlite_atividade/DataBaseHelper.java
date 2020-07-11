package com.example.sqlite_atividade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context)
    {
        super(context, "Kanban", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE Atividades" +
                "(codigo INTEGER PRIMARY KEY," +
                "nome_atividade TEXT," +
                "responsavel TEXT," +
                "prioridade INTEGER)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean inserir(Atividade atividade)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("codigo", atividade.getCodigo());
        cv.put("nome_atividade", atividade.getNomeAtividade());
        cv.put("responsavel", atividade.getResponsavel());
        cv.put("prioridade", atividade.getPrioridade());
        long resultado = db.insert("Atividades", null, cv);
        db.close();

        if(resultado == -1)
        {
            return false;
        }
        return true;
    }

    public List<Atividade> pesquisar(int codigo)
    {
        String sql = "SELECT * FROM Atividades WHERE codigo = " +codigo;
        List<Atividade> vetorAtividades = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                Atividade atividade = new Atividade();
                atividade.setCodigo(cursor.getInt(0));
                atividade.setNomeAtividade(cursor.getString(1));
                atividade.setResponsavel(cursor.getString(2));
                atividade.setPrioridade(cursor.getInt(3));
                vetorAtividades.add(atividade);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vetorAtividades;
    }

    public List<Atividade> pesquisarTodos()
    {
        String sql = "SELECT * FROM Atividades";
        List<Atividade> vetorAtividades = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                Atividade atividade = new Atividade();
                atividade.setCodigo(cursor.getInt(0));
                atividade.setNomeAtividade(cursor.getString(1));
                atividade.setResponsavel(cursor.getString(2));
                atividade.setPrioridade(cursor.getInt(3));
                vetorAtividades.add(atividade);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vetorAtividades;
    }

    public void alterar(Atividade atividade)
    {
        ContentValues cv = new ContentValues();
        cv.put("codigo", atividade.getCodigo());
        cv.put("nome_atividade", atividade.getNomeAtividade());
        cv.put("responsavel", atividade.getResponsavel());
        cv.put("prioridade", atividade.getPrioridade());

        SQLiteDatabase db = this.getWritableDatabase();

        db.update("Atividades", cv, "codigo=" + atividade.getCodigo(), null);
        db.close();
    }

    public void excluir(int codigo)
    {
        String sql = "DELETE FROM Atividades WHERE codigo=" + codigo;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void excluirTodos()
    {
        String sql = "DELETE FROM Atividades";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
}
