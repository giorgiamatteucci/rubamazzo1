package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrazioneActivity extends AppCompatActivity {

    EditText etNome, etCognome, etEmail, etPassword;
    Button btnRegistrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        etNome = findViewById(R.id.etNome);
        etCognome = findViewById(R.id.etCognome);
        etEmail = findViewById(R.id.etEmailRegistrazione);
        etPassword = findViewById(R.id.etPasswordRegistrati);

        btnRegistrati = findViewById(R.id.btnAccedi);

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO creare e registrare su db l'account creato
                    /*
                     TODO (controlli)
                      - check sulla presenza della email inserita
                      - stabilire requisiti minimi della password

                     */


                Intent menu = new Intent(RegistrazioneActivity.this, MenuActivity.class);

                startActivity(menu);
            }
        });

    }
}
