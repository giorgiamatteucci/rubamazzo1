package com.example.rubamazzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ActivityGiocoServer extends AppCompatActivity {

    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;

    String idPartita, idClient, idServer;
    DatabaseReference dbRefPartita;
    Mazzo mazzo;
    int nCarteMazzoClient, nCarteMazzoServer;
    boolean mioTurno;
    int nMosse;
    //boolean fineManche;
    //int  c1client, c2client, c3client, c1server, c2server, c3server;

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

        idPartita = getIntent().getStringExtra("idPartita");
        idClient = getIntent().getStringExtra("idClient");
        idServer = getIntent().getStringExtra("idServer");
        dbRefPartita = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Partita/"+idPartita);

        mazzo = Mazzo.getIstance();
        ivC1Client.setImageResource(R.drawable.retro);
        ivC2Client.setImageResource(R.drawable.retro);
        ivC3Client.setImageResource(R.drawable.retro);

        nCarteMazzoClient=0;
        nCarteMazzoServer=0;

        nMosse=3;

        aggiornaStatoPartita();
        //estraiDalMazzo3CarteGiocatori();
        //setCarteCentrali();
        //FirebaseDatabase.getInstance().getReference("Partita/" + idPartita + "/").child(idClient);//per indicare il turno del client...?
        //allora il server può giocare  //if(dbRefPartita.child(idServer)){         }

        ivC1Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivC2Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivC3Server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
        ivMazzoServer.setImageResource(R.drawable.seleziona_carta);
        /*
        su Firebase aggiungere il campo Turno che sarà valorizzato 'client' oppure 'server'
        e in base al turno disabilitare gli onClick.

         * if() c'è una carta all'interno della rw che ha lo stesso valore di quella selezionata
         * (fare il controllo anche sul mazzo dell'avversario, salvare il numero di carte che ha in una varibile da creare)
         * togliere la carta selezionata tra le sue e metterla tra quelle del mazzo (eliminare anche la carta della rw)
         * altrimenti toast
         *
         * LA PARTITA FINISCE QUANDO SONO FINITE LE CARTE DEL MAZZO (creare il metodo isEmpty() nella classe Mazzo)
         * VINCE CHI HA PIU' CARTE NEL MAZZO
         * */
    }

    private void aggiornaStatoPartita(){

        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");
                String[] carteServer = String.valueOf(snapshot.child("carteServer").getValue()).split(" ");
                String[] carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue()).split(" ");
                String[] carteClient = String.valueOf(snapshot.child("carteClient").getValue()).split(" ");

                ivC1Client.setImageResource(carteClient[0].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC2Client.setImageResource(carteClient[1].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC3Client.setImageResource(carteClient[2].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);

                ivC1Server.setImageResource(carteServer[0].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[0]).getIdImmagine());
                ivC2Server.setImageResource(carteServer[1].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[1]).getIdImmagine());
                ivC3Server.setImageResource(carteServer[2].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[2]).getIdImmagine());

                Log.d("TAG-REFRESH"," step 1 ok");

                carteSotto.clear();
                carteSopra.clear();

                Log.d("TAG-REFRESH"," step 2 ok");

                for(int i=0;i<carteCentrali.length;i++) {
                    if(i%2==0){
                        carteSopra.add(mazzo.getCartaById(carteCentrali[i]));
                        adapterSopra.notifyItemInserted(carteSopra.size()-1);
                    }else{
                        carteSotto.add(mazzo.getCartaById(carteCentrali[i]));
                        adapterSotto.notifyItemInserted(carteSotto.size()-1);
                    }
                }

                Log.d("TAG-REFRESH"," step 3 ok");

                nCarteMazzoClient = snapshot.child("nCarteMazzoC").getValue(Integer.class);
                nCarteMazzoServer = snapshot.child("nCarteMazzoS").getValue(Integer.class);

                String idCartaMazzoClient = snapshot.child("cartaMazzoC").getValue(String.class);
                if(!idCartaMazzoClient.isEmpty()){
                    ivMazzoClient.setImageResource(mazzo.getCartaById(idCartaMazzoClient).getIdImmagine());
                }else{
                    ivMazzoClient.setImageResource(R.drawable.seleziona_carta);
                }

                String idCartaMazzoServer = snapshot.child("cartaMazzoS").getValue(String.class);
                if(!idCartaMazzoServer.isEmpty()){
                    ivMazzoServer.setImageResource(mazzo.getCartaById(idCartaMazzoServer).getIdImmagine());
                }else{
                    ivMazzoServer.setImageResource(R.drawable.seleziona_carta);
                }

                Log.d("TAG-REFRESH"," step 4 ok");

                mioTurno = snapshot.child("turno").getValue().equals("server");

                adapterSopra.notifyDataSetChanged();
                adapterSotto.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void estraiDalMazzo3CarteGiocatori() {
        dbRefPartita.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteClient").setValue(mazzo.estraiCarta().getId() + " " + mazzo.estraiCarta().getId()+ " " +mazzo.estraiCarta().getId());
                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteServer").setValue(mazzo.estraiCarta().getId() + " " + mazzo.estraiCarta().getId()+ " " +mazzo.estraiCarta().getId());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

}