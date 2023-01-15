package com.example.proyectoev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText mail, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.mail);
        contrasena = findViewById(R.id.contraseña);

        Button pantallaregistro = (Button) findViewById(R.id.pantallaregistro);


        pantallaregistro.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Registro.class);
            startActivity(intent);
        });
    }

    public void consultaUsuarioPorMailYPassword(View view) {
        RegistroUsuarioSQLite admin = new RegistroUsuarioSQLite(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String eMail = mail.getText().toString();
        String password = contrasena.getText().toString();

        if (!eMail.isEmpty() && !password.isEmpty()) {
            // Hay que poner comillas simples para evitar errores y que SQLite capte bien la sentencia
            Cursor fila = bd.rawQuery("select mail, contraseña from UsuarioRegistrado where mail='" + eMail + "'"+"and "+"contraseña='"+password+"'", null);
            if (fila.moveToFirst()) {
                Intent login = new Intent(Login.this, MainUser.class);
                startActivity(login);
                bd.close();
            } else {
                Toast.makeText(this, "El email o el usuario es incorrecto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Introduce un correo y contraseña", Toast.LENGTH_SHORT).show();
        }

    }


}