package com.example.exapraadmprodejesus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText etUsuario, etContraseña;
    private Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                if (usuario.equals("Angel") && contraseña.equals("12345")) {
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(login);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña inválidos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}