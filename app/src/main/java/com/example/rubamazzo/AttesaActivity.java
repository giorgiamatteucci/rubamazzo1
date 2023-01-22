package com.example.rubamazzo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AttesaActivity extends AppCompatActivity {

    TextView tvAttesaPartita;
    Button btnAnnulla;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/");
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attesa);

        tvAttesaPartita = findViewById(R.id.tvAttesaPartita);
        tvAttesaPartita.setText(getIntent().getStringExtra("testo"));
        btnAnnulla = findViewById(R.id.btnGioca);
        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(AttesaActivity.this, MenuActivity.class);
                startActivity(menu);
                finish();
            }
        });

        if(getIntent().getStringExtra("testo").equals("in attesa di essere aggiunto ad una partita")){//CLIENT

            dbReference.child("Partita/").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0){
                        Toast.makeText(AttesaActivity.this, "Ancora non ci sono partite a cui unirsi.", Toast.LENGTH_SHORT).show();
                    }
                    if(dataSnapshot.getChildrenCount()==1){//CONTROLLARE CHE IL CLIENT NON SIA UGUALE AL SERVER
                        //dbReference.child("Partita/").child("idClient").push().setValue(id);
                        //dbReference.child("Partita/").child("idClient").child("id_giocatore").getRef().setValue(id);
                        dbReference.child("Partita/").child(id).child("tipo_giocatore").getRef().setValue("client");
                        Toast.makeText(AttesaActivity.this, "Ti sei unito alla partita.", Toast.LENGTH_SHORT).show();
                    }
                    if(dataSnapshot.getChildrenCount()==2){
                        Intent i = new Intent(AttesaActivity.this, ActivityGiocoClient.class);
                        //i.putExtra();
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
        else {//SERVER

            dbReference.child("Partita/").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0){
                    /*String idConnessione = String.valueOf(System.currentTimeMillis());
                    String idGiocatore = String.valueOf(System.currentTimeMillis());
                    dataSnapshot.child(idConnessione).child(idGiocatore).child("id_giocatore").getRef().setValue(id);*/

                        //dbReference.child("Partita/").child("idServer").push().setValue(id);
                        //dbReference.child("Partita/").child("idServer").child("id_giocatore").getRef().setValue(id);
                        dbReference.child("Partita/").child(id).child("tipo_giocatore").getRef().setValue("server");
                        Toast.makeText(AttesaActivity.this, "Partita creata.", Toast.LENGTH_SHORT).show();
                    }
                    if(dataSnapshot.getChildrenCount()==1){//CONTROLLARE CHE IL SEREVER NON SIA UGUALE AL CLIENT
                        Toast.makeText(AttesaActivity.this, "Attendi un giocatore.", Toast.LENGTH_SHORT).show();
                    }
                    if(dataSnapshot.getChildrenCount()==2){
                        startActivity(new Intent(AttesaActivity.this, ActivityGiocoServer.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

    }
}
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