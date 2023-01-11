package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        tvTitolo = findViewById(R.id.tvTiitolo);
        btnRegistrati = findViewById(R.id.btnRegistrati);
        btnAccedi = findViewById(R.id.btnAccedi);

        btnAccedi.setOnClickListener(v -> {
            //tvTitolo.setText(" hai scelto accedi ");
            Intent prova = new Intent(MainActivity.this, AttesaActivity.class);
            startActivity(prova);
        });

    }
}