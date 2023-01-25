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
import java.util.Objects;

public class ActivityGiocoClient extends AppCompatActivity {

    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;
    DatabaseReference dbRefPartita;
    Mazzo mazzo = Mazzo.getIstance();
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
            public void onClick(View v) { }
        });
        ivC2Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivC3Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivMazzoClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivMazzoClient.setImageResource(R.drawable.seleziona_carta);

        ivC1Server.setImageResource(R.drawable.retro);
        ivC2Server.setImageResource(R.drawable.retro);
        ivC3Server.setImageResource(R.drawable.retro);

        dbReference.child("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idPartita.equals(snapshot.getKey())) {
                        String[] carteClient = String.valueOf(snapshot.child("carteClient").getValue()).split(" ");
                        String[] carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue()).split(" ");
                        Log.d("TAG5", "snapshot.child(\"idClient\").getValue(): " + snapshot.child("idClient").getValue());
                        Log.d("TAG5", "snapshot.child(\"idServer\").getValue(): " + snapshot.child("idServer").getValue());
                        Log.d("TAG5", "idPartita: " + snapshot.getKey());
                        Log.d("TAG5", "carteClient: " + snapshot.child("carteClient").getValue());
                        Log.d("TAG5", "carteServer: " + snapshot.child("carteServer").getValue());
                        Log.d("TAG5", "c1client: " + carteClient[0]);
                        Log.d("TAG5", "c2client: " + carteClient[1]);
                        Log.d("TAG5", "c3client: " + carteClient[2]);
                        Log.d("TAG5", "carteCentrali: " + snapshot.child("carteCentrali").getValue());

                        ivC1Client.setImageResource(Integer.parseInt(carteClient[0]));
                        ivC2Client.setImageResource(Integer.parseInt(carteClient[1]));
                        ivC3Client.setImageResource(Integer.parseInt(carteClient[2]));
                        for(int i=0;i<4;i++){//for (String c : carteCentrali) {
                            if(i%2==0){
                                carteSopra.add(mazzo.getCartaById(carteCentrali[i]));
                                adapterSopra.notifyItemInserted(carteSopra.size()-1);
                            }else{
                                carteSotto.add(mazzo.getCartaById(carteCentrali[i]));
                                adapterSotto.notifyItemInserted(carteSotto.size()-1);
                            }
                        }
                        adapterSopra.notifyDataSetChanged();
                        adapterSotto.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {   }
        });

    }
}