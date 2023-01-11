package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnGioca, btnCrea, btnClassifica, btnRegole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnGioca = findViewById(R.id.btnGioca);
        btnCrea = findViewById(R.id.btnCrea);
        btnClassifica = findViewById(R.id.btnClassifica);
        btnRegole = findViewById(R.id.btnCome);

        btnGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //andrà ad attendere di essere aggiunto ad una partita già creata

                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di essere aggiunto ad una partita");
                startActivity(i);
            }
        });

        btnCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //verrà creata una partita e verrà messo in attesa che un altro giocatore venga aggiunto ad essa
                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di uno sfidante");
                startActivity(i);
            }
        });

    }
}