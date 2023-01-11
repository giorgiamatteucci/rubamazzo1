package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrazioneActivity extends AppCompatActivity {

    TextView etNome, etCognome, etEmail, etPassword;
    Button btnRegistrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        etNome = findViewById(R.id.etNome);
        etCognome = findViewById(R.id.etCognome);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrati = findViewById(R.id.btnRegistrati);
        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(RegistrazioneActivity.this, MenuActivity.class);
                startActivity(menu);
            }
        });

    }
}
