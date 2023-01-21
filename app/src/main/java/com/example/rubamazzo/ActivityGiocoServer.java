package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityGiocoServer extends AppCompatActivity {

    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;

    DatabaseReference dbRefPartita = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Partita/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        ivC1Server = findViewById(R.id.ivC1_sotto);
        ivC2Server = findViewById(R.id.ivC2_sotto);
        ivC3Server = findViewById(R.id.ivC3_sotto);
        ivMazzoServer = findViewById(R.id.ivMazzo_sotto);

        ivC1Client = findViewById(R.id.ivC1_sopra);
        ivC2Client = findViewById(R.id.ivC2_sopra);
        ivC3Client = findViewById(R.id.ivC3_sopra);
        ivMazzoClient = findViewById(R.id.ivMazzo_sopra);

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

        dbRefPartita.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAGNUOVO",dbRefPartita.child("idServer").getKey());
                Log.d("TAGNUOVO", String.valueOf(dbRefPartita.child("idServer").get()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ivC1Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivC2Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivC3Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivMazzoServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}