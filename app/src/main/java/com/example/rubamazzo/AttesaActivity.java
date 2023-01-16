package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttesaActivity extends AppCompatActivity {

    TextView tvAttesaPartita;
    Button btnAnnulla;
    Button btnProva;//DA ELIMINARE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attesa);
        //PROVA da eliminare!
        btnProva = findViewById(R.id.button);
        btnProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("testo").equals("in attesa di essere aggiunto ad una partita")){
                    //gioca come client
                    startActivity(new Intent(AttesaActivity.this, ActivityGiocoClient.class));
                    // .putExtra();
                }
                else {
                    //indent gioca come server
                    startActivity(new Intent(AttesaActivity.this, ActivityGiocoServer.class));
                }
            }
        });


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
