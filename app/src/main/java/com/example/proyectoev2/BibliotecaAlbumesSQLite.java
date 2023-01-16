package com.example.proyectoev2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class BibliotecaAlbumesSQLite extends SQLiteOpenHelper {

    public BibliotecaAlbumesSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String titulo, String autor, String discografia, byte[] imagen){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "INSERT INTO AlbumesRegistrados VALUES (NULL, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,titulo);
        statement.bindString(2,autor);
        statement.bindString(3,discografia);
        statement.bindBlob(4,imagen);

        statement.executeInsert();
    }

    public void updateData(String titulo, String autor, String discografia, byte[] imagen, int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE AlbumesRegistrados SET titulo=?, autor=?, discografia=?, imagen=? WHERE id=? ";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,titulo);
        statement.bindString(2,autor);
        statement.bindString(3,discografia);
        statement.bindBlob(4,imagen);
        statement.bindDouble(5,(double)id);

        statement.execute();
        database.close();

    }

    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM AlbumesRegistrados WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double) id);
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
