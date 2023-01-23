package com.example.rubamazzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityGiocoClient extends AppCompatActivity {

    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;
    DatabaseReference dbRefPartita;
    Mazzo mazzo = new Mazzo();
    String idPartita;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        ivC1Server = findViewById(R.id.ivC1_sopra);
        ivC2Server = findViewById(R.id.ivC2_sopra);
        ivC3Server = findViewById(R.id.ivC3_sopra);
        ivMazzoServer = findViewById(R.id.ivMazzo_sopra);

        ivC1Client = findViewById(R.id.ivC1_sotto);
        ivC2Client = findViewById(R.id.ivC2_sotto);
        ivC3Client = findViewById(R.id.ivC3_sotto);
        ivMazzoClient = findViewById(R.id.ivMazzo_sotto);

        rvSopra = findViewById(R.id.rvSopra);
        rvSotto = findViewById(R.id.rvSotto);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        rvSopra.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        rvSotto.setLayoutManager(layoutManager);

        carteSotto = new ArrayList<>();
        adapterSotto = new CartaAdapter(carteSotto);
        rvSotto.setAdapter(adapterSotto);

        carteSopra = new ArrayList<>();
        adapterSopra = new CartaAdapter(carteSopra);
        rvSopra.setAdapter(adapterSopra);

        idPartita = getIntent().getStringExtra("idPartita");
        dbRefPartita = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Partita/"+idPartita);


        ivC1Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivC2Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivC3Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivMazzoClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //prova();
        ivC1Server.setImageResource(R.drawable.retro);
        ivC2Server.setImageResource(R.drawable.retro);
        ivC3Server.setImageResource(R.drawable.retro);

        dbReference.child("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {
        //dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d("TAG5", "snapshot: " + snapshot);
                        //  PROVA A METTERE UN ALTRO FOR INTERNO CHE SCORRE AD UN LIVELLO SOTTO!
                        //Log.d("TAG5", "snapshot.child(\"idServer\").getValue(): " + snapshot.child("idServer").getValue());
                        //Log.d("TAG5", "snapshot.child(\"c1client\").getValue(): " + snapshot.child("c1client").getValue());
                        //Log.d("TAG5", "idPartita: " + idPartita);
                        // Log.d("TAG5","snapshot.child(\"c1client\").getValue(): " + String.valueOf(snapshot.child("c1client").getValue()));//MANDA IN CRUSH L'APP!

                        //ivC1Client.setImageResource(mazzo.getCardById((String) snapshot.child("c1client").getValue()).getIdImmagine());//VERSIONE CON id invece che idImmagine
                        //ivC1Client.setImageResource((Integer) snapshot.child("c1client").getValue());
                        //Log.d("TAG5", "Dovrebbe restituire un valore a 10 cifre: " + (Integer) snapshot.child("c1client").getValue());
                        //ivC1Client.setImageResource((Integer) snapshot.child("c1client").getValue());
                        //ivC2Client.setImageResource((Integer) snapshot.child("c2client").getValue());//ivC2Client.setImageResource((Integer) dataSnapshot.child(idPartita).child("c2client").getValue());
                        //ivC3Client.setImageResource((Integer) snapshot.child("c3client").getValue());
                }
                //break;
                //dbRefPartita.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {   }
        });

    }

    /*void prova(){
        Mazzo mazzo = new Mazzo();
        ivC1Server.setImageResource(R.drawable.retro);
        ivC2Server.setImageResource(R.drawable.retro);
        ivC3Server.setImageResource(R.drawable.retro);
        ivC1Client.setImageResource(mazzo.estraiCarta().getIdImmagine());
        ivC2Client.setImageResource(mazzo.estraiCarta().getIdImmagine());
        ivC3Client.setImageResource(mazzo.estraiCarta().getIdImmagine());
        for(int i=0;i<6;i++){
            Carta carta = mazzo.estraiCarta();
            if(i%2==0){
                carteSopra.add(carta);
                adapterSopra.notifyItemInserted(carteSopra.size()-1);
            }else{
                carteSotto.add(carta);
                adapterSotto.notifyItemInserted(carteSotto.size()-1);
            }
        }
    }*/
}