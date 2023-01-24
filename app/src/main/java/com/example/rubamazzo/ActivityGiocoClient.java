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
    //Mazzo mazzo = new Mazzo();
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

        /*dbReference.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    HashMap dataMap = (HashMap ) dataSnapshot.getValue();
                    for (Chiave stringa: dataMap.keySet()){
                        Dati oggetto = dataMap.get(chiave);
                        try{
                            HashMap userData = (HashMap ) dati;
                            Utente mUser = new User((String) userData.get("name"), (int) (long) userData.get("age"));
                            addTextToView(mUser.getName() + " - " + Integer.toString(mUser.getAge()));
                        }catch (ClassCastException cce){ // Se non è possibile eseguire il cast dell'oggetto in HashMap, significa che è di tipo String.
                            try{
                                String mString = String.valueOf(dataMap.get(chiave));
                                addTextToView(mString);
                            }catch (ClassCastException cce2){ } } }
                       }
                 }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });*/


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
                    if (idPartita.equals(snapshot.getKey())) {
                        //String[] carteClient = String.valueOf(snapshot.child("carteClient").getValue()).split(" ");
                        Log.d("TAG5", "snapshot.child(\"idClient\").getValue(): " + snapshot.child("idClient").getValue());
                        Log.d("TAG5", "snapshot.child(\"idServer\").getValue(): " + snapshot.child("idServer").getValue());
                        Log.d("TAG5", "idPartita: " + snapshot.getKey());
                        Log.d("TAG5", "carteClient: " + snapshot.child("carteClient").getValue());
                        Log.d("TAG5", "carteServer: " + snapshot.child("carteServer").getValue());
                        //Log.d("TAG5", "c1client: " + carteClient[0]);
                        //Log.d("TAG5", "c2client: " + carteClient[1]);
                        //Log.d("TAG5", "c3client: " + carteClient[2]);

                        //ivC1Client.setImageResource(Integer.parseInt(carteClient[0]));
                        //ivC2Client.setImageResource(Integer.parseInt(carteClient[1]));
                        //ivC3Client.setImageResource(Integer.parseInt(carteClient[2]));
                    }
                }
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