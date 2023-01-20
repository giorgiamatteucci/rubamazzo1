package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AttesaActivity extends AppCompatActivity {

    TextView tvAttesaPartita;
    Button btnAnnulla;
    Giocatore giocatore;
    Button btnProva;//DA ELIMINARE


    private void giocaClient(){
        FirebaseDatabase.getInstance().getReference("Connessione/Partita").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //if()
                    String userId = FirebaseDatabase.getInstance().getReference("Giocatore").push().getKey();
                    FirebaseDatabase.getInstance().getReference("Connessione/Partita").child(userId).setValue(giocatore);
                    Toast.makeText(AttesaActivity.this, "Ti sei unito alla partita.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            }
        );
        //if()
            //startActivity(new Intent(AttesaActivity.this, ActivityGiocoClient.class));
    }
    private void giocaServer(){
        FirebaseDatabase.getInstance().getReference("Connessione/Partita/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Partita(giocatore));

        Toast.makeText(AttesaActivity.this, "Partita creata.", Toast.LENGTH_SHORT).show();

            /*FirebaseDatabase.getInstance().getReference("Partita").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      Log.d("attesa", "onDataChange");
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                      Log.d("attesa", "onCancelled");
                  }
              }
            );*/
        //if()
        //startActivity(new Intent(AttesaActivity.this, ActivityGiocoServer.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attesa);

        FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    giocatore = Utils.getGiocatoreFromHashMap((HashMap) dataSnapshot.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        /*
            S.E.

            la struttura che farei io è questa:
               Partita
                - server: id
                - client: id
                - idComunicazione? non lo so, devi capire come fare la comunicazione

                le comunicazioni da fare sono ad esempio:
                - le tue carte sono..
                - il tuo avversario ho fatto questa mossa...

                e chi riceve la comunicazione deve far vedere sul display la modifica

            if(client){
                forech(){
                    if(client==""){
                        "mi collego" alla prima partita
                        break;
                    }
            }else{
                //server
                creo una partita e metto il mio id nella voce server
            }
        */


        if(getIntent().getStringExtra("testo").equals("in attesa di essere aggiunto ad una partita")){
            giocaClient();
            //gioca come client
                    /*SE C'E' UNA CONNESSIONE CON UN SOLO FIGLIO ALLORA MI COLLEGO
                        //esempio per aggiungere un Giocatore, devo fare credo la stessa cosa per collegarmi ad una partita
                        Giocatore user = new Giocatore("enrico","enricoluc@gmail.com","enrico");
                        String userId = FirebaseDatabase.getInstance().getReference("Giocatore").push().getKey();
                        FirebaseDatabase.getInstance().getReference("Giocatore").child(userId).setValue(user);
                       *
            startActivity(new Intent(AttesaActivity.this, ActivityGiocoClient.class));*/
        }
        else {
            giocaServer();
            //indent gioca come server
                    /* SI CREA LA SUA PARTITA E RIMANE IN ATTESA DI UN CLIENT quindi una volta creata la partita questa avrà 1 child
                        FirebaseDatabase.getInstance().getReference("Connessione/Partita")
                     Quando Partita avrà 2 child vuol dire che un client si è collegato quindi può iniziare la partita (passare alla prossima activity)

            startActivity(new Intent(AttesaActivity.this, ActivityGiocoServer.class));*/
        }

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
