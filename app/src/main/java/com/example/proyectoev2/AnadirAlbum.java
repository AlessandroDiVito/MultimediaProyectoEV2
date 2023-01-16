package com.example.proyectoev2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class AnadirAlbum extends AppCompatActivity {

    EditText nombreAlbum,autor,discografia;
    Button botonAñadir;
    ImageView imagenAlbum;

    final int REQUEST_CODE_GALLERY = 999;

    public static BibliotecaAlbumesSQLite SQLiteAlbumes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_album);


        nombreAlbum = findViewById(R.id.nombreAlbum);
        autor = findViewById(R.id.autor);
        discografia = findViewById(R.id.discografia);
        botonAñadir = findViewById(R.id.botonAñadir);
        imagenAlbum = findViewById(R.id.imagenAlbum);

        SQLiteAlbumes = new BibliotecaAlbumesSQLite(this,"AlbumesRegistrados.sqlite",null,1);

        SQLiteAlbumes.queryData("CREATE TABLE IF NOT EXISTS AlbumesRegistrados(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, autor VARCHAR, discografia VARCHAR, imagen BLOB)");

        imagenAlbum.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ActivityCompat.requestPermissions(AnadirAlbum.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                       REQUEST_CODE_GALLERY );
           }
        });


        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteAlbumes.insertData(
                            nombreAlbum.getText().toString().trim(),
                            autor.getText().toString().trim(),
                            discografia.getText().toString().trim(),
                            imagenViewToByte(imagenAlbum));
                Toast.makeText(AnadirAlbum.this,"Añadido correctamente", Toast.LENGTH_SHORT).show();
                nombreAlbum.setText("");
                autor.setText("");
                discografia.setText("");
                imagenAlbum.setImageResource(R.drawable.anadirfoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static byte[] imagenViewToByte(ImageView imagenAlbum) {
        Bitmap bitmap = ((BitmapDrawable)imagenAlbum.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("imager/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this,"No tiene permisos para acceder a la carpeta de imagenes", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}