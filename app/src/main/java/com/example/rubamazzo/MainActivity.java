package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvTitolo;
    Button btnRegistratiMain, btnAccediMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding elementi layout con elementi java (oggetti)
        tvTitolo = findViewById(R.id.tvTiitolo);
        btnRegistratiMain = findViewById(R.id.btnRegistrati);
        btnAccediMain = findViewById(R.id.btnAccediMain);

        btnAccediMain.setOnClickListener(v -> {
            Intent prova = new Intent(MainActivity.this, AttesaActivity.class);
            startActivity(prova);
        });

    }
}