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
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail=findViewById(R.id.mail);
        contrasena =findViewById(R.id.contraseña);

        spinner = findViewById(R.id.idiomas);
        Button pantallaregistro = (Button) findViewById(R.id.pantallaregistro);


        pantallaregistro.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Registro.class);
            startActivity(intent);
        });
    }

    public void consultaUsuarioPorMailYPassword(View v) {
        RegistroUsuarioSQLite admin = new RegistroUsuarioSQLite(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String eMail = mail.getText().toString();
        //String password = contraseña.getText().toString();
        Cursor fila = bd.rawQuery(
                "select mail,contraseña from UsuarioRegistrado where mail='" + eMail, null);
        if (fila.moveToFirst()) {
            mail.setText(fila.getString(0));
            contrasena.setText(fila.getString(1));
            Toast.makeText(this, "Correo"+mail.toString()+"Pass"+ contrasena.toString(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainUser.class);
            startActivity(intent);
        } else
            Toast.makeText(this, "Correo o contraseña incorrecta.",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }


}