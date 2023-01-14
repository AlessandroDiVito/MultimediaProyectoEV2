package com.example.proyectoev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private EditText nombreUsuario, mail, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreUsuario = findViewById(R.id.nombreUsuario);
        mail = findViewById(R.id.mail);
        contrasena = findViewById(R.id.contraseña);

        Button volverlogin = (Button) findViewById(R.id.volverlogin);

        volverlogin.setOnClickListener(view -> {
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
        });
    }

    public void registrarusuario(View v) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(mail.getText());
        if (mather.find()) {
            RegistroUsuarioSQLite admin = new RegistroUsuarioSQLite(this,
                    "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String userName = nombreUsuario.getText().toString();
            String eMail = mail.getText().toString();
            String password = contrasena.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("nombreUsuario", userName);
            registro.put("mail", eMail);
            registro.put("contraseña", password);
            bd.insert("UsuarioRegistrado", null, registro);
            bd.close();
            nombreUsuario.setText("");
            mail.setText("");
            contrasena.setText("");
            Toast.makeText(this, "¡Cuenta creada correctamente!.",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "El email ingresado es inválido.",
                    Toast.LENGTH_SHORT).show();
        }

    }


}