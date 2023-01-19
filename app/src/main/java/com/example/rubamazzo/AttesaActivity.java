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


        //PROVA da eliminare!--------------------------------------------------------------------------------------------
        btnProva = findViewById(R.id.button);
        btnProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("testo").equals("in attesa di essere aggiunto ad una partita")){
                    //gioca come client
                    /*SE C'E' UNA CONNESSIONE CON UN SOLO FIGLIO ALLORA MI COLLEGO
                        //esempio per aggiungere un Giocatore, devo fare credo la stessa cosa per collegarmi ad una partita
                        Giocatore user = new Giocatore("enrico","enricoluc@gmail.com","enrico");
                        String userId = FirebaseDatabase.getInstance().getReference("Giocatore").push().getKey();
                        FirebaseDatabase.getInstance().getReference("Giocatore").child(userId).setValue(user);
                       * */
                    startActivity(new Intent(AttesaActivity.this, ActivityGiocoClient.class));
                }
                else {
                    //indent gioca come server
                    /* SI CREA LA SUA PARTITA E RIMANE IN ATTESA DI UN CLIENT quindi una volta creata la partita questa avrà 1 child
                        FirebaseDatabase.getInstance().getReference("Connessione/Partita")
                     Quando Partita avrà 2 child vuol dire che un client si è collegato quindi può iniziare la partita (passare alla prossima activity)
                    */
                    startActivity(new Intent(AttesaActivity.this, ActivityGiocoServer.class));
                }
            }
        });
        //--------------------------------------------------------------------------------------------------------------

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
