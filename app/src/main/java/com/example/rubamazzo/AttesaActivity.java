package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttesaActivity extends AppCompatActivity {

    TextView tvAttesaPartita;
    Button btnAnnulla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attesa);

        if(getIntent().getStringExtra("pulsante") == "client"){
            //gioca come client
        }
        else {
            //indent gioca come server
        }
        tvAttesaPartita = findViewById(R.id.tvAttesaPartita);
        tvAttesaPartita.setText(getIntent().getStringExtra("testo"));

        btnAnnulla = findViewById(R.id.btnGioca);
        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(AttesaActivity.this, MenuActivity.class);
                startActivity(menu);
            }
        });

    }
}
