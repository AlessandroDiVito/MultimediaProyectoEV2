package com.example.proyectoev2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaAlbumes extends AppCompatActivity {

    ListView listView;
    ArrayList<Model> lista;
    ListaAlbumesAdaptado adaptado = null;

    ImageView imagenViewIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_albumes);

        listView = findViewById(R.id.listView);
        lista = new ArrayList<>();
        adaptado = new ListaAlbumesAdaptado(this,R.layout.row, lista);
        listView.setAdapter(adaptado);

        Cursor cursor = AnadirAlbum.SQLiteAlbumes.getData("SELECT * FROM AlbumesRegistrados");
        lista.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String autor = cursor.getString(2);
            String discografica = cursor.getString(2);
            byte[] imagen = cursor.getBlob(4);

            lista.add(new Model(id,titulo,autor,discografica,imagen));
        }
        adaptado.notifyDataSetChanged();
        if (lista.size()==0){
            Toast.makeText(this,"No record found...", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ListaAlbumes.this);

                dialog.setTitle("Elige una accion");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarImagen();

                        if (which == 0){
                          Cursor c = AnadirAlbum.SQLiteAlbumes.getData("SELECT id FROM AlbumesRegistrados");
                          ArrayList<Integer> arraID = new ArrayList<Integer>();
                          while (c.moveToNext()){
                              arraID.add(c.getInt(0));
                          }
                          //enseñar actualizacion
                      }
                      if (which==1){
                          //eliminar
                      }
                    }
                });

                return false;
            }
        });
    }



    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_OK){
            Uri path=data.getData();
            imagenViewIcono.setImageURI(path);
        }
    }
}