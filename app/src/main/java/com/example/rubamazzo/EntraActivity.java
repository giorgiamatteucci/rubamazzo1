package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntraActivity extends AppCompatActivity {

    TextView etEmail, etPassword;
    Button btnAccedi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesso);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnAccedi = findViewById(R.id.btnAccedi);

        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO verificare sul db le credenziali inserite

                // se presenti
                Intent i = new Intent(EntraActivity.this, MenuActivity.class);
                startActivity(i);

                //altrimenti
                // TODO dialog -> messaggio credenziali errate
            }
        });

    }
}
