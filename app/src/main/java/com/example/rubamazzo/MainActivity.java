package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvTitolo;
    Button btnRegistrati, btnAccedi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding elementi layout con elementi java (oggetti)
        tvTitolo = findViewById(R.id.tvTitolo);

        btnRegistrati = findViewById(R.id.btnRegistratiMain);
        btnAccedi = findViewById(R.id.btnAccediMain);

        btnRegistrati.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RegistrazioneActivity.class);
            startActivity(i);
        });

        btnAccedi.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, EntraActivity.class);
            startActivity(i);
        });

    }
}