package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegoleActivity extends AppCompatActivity {

    TextView tvTitRegole, tvRegole;
    Button btnIndMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regole);
        tvTitRegole = findViewById(R.id.tvTitRegole);

        tvRegole = findViewById(R.id.tvRegole);
        tvRegole.append("Il giocatore che crea la partita distribuisce le carte partendo dall'avversario, e ciascuno riceve 3 carte. Le successive quattro carte del mazzo vengono invece disposte scoperte sul tavolo.\n");
        tvRegole.append("A questo punto ogni giocatore può giocare una sola carta ad ogni turno. Quando ogni giocatore ha finito le proprie carte vengono, di nuovo, distribuite tre carte a testa.\n");
        tvRegole.append("Il gioco si svolge in questo modo: il giocatore deve vedere se tra le proprie carte ce n'è almeno una dello stesso valore di una di quelle presenti sul tavolo. \n");
        tvRegole.append("Nel caso in cui fosse presente almeno una carta dello stesso valore nel tavolo allora basterà selezionare la propria carta, a questo punto il primo giocatore ha già iniziato a creare il proprio mazzo. \n");
        tvRegole.append("Qualora un altro giocatore, durante il proprio turno, avesse in mano una carta dello stesso valore di quella in cima al mazzetto dell'avversario, può rubargli il mazzo.\n");
        tvRegole.append("Infine, se non è possibile fare nessuna di queste due operazioni, il giocatore è costretto a scegliere tra le sue carte una da mettere al centro, tra le carte sul tavolo.\n");
        tvRegole.append("La partita finisce quando non ci sono più carte da distribuire, e vince chi ha il numero maggiore di carte nel proprio mazzo.");

        btnIndMenu = findViewById(R.id.btnIndMenu);
        btnIndMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegoleActivity.this, MenuActivity.class));
            }
        });
    }
}