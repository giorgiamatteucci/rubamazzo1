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
    int  c1client, c2client, c3client, c1server, c2server, c3server;

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

        visualizzaCarte();
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

    private void visualizzaCarte(){
        FirebaseDatabase.getInstance().getReference("Partita/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idPartita.equals(snapshot.getKey())) {
                        Log.d("TAG5","sono dentro visualizzaCarte()");
                        String[] carteServer = String.valueOf(snapshot.child("carteServer").getValue()).split(" ");
                        String[] carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue()).split(" ");
                        ivC1Server.setImageResource(Integer.parseInt(carteServer[0]));
                        ivC2Server.setImageResource(Integer.parseInt(carteServer[1]));
                        ivC3Server.setImageResource(Integer.parseInt(carteServer[2]));
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
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void estraiDalMazzo3CarteGiocatori() {
        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c1client = mazzo.estraiCarta().getIdImmagine();//c1client = mazzo.estraiCarta().getId();
                c2client = mazzo.estraiCarta().getIdImmagine();//c2client = mazzo.estraiCarta().getId();
                c3client = mazzo.estraiCarta().getIdImmagine();//c3client = mazzo.estraiCarta().getId();
                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteClient").setValue(c1client + " " + c2client+ " " +c3client);

                c1server = mazzo.estraiCarta().getIdImmagine();//c1server = mazzo.estraiCarta().getId();
                c2server = mazzo.estraiCarta().getIdImmagine();//c2server = mazzo.estraiCarta().getId();
                c3server = mazzo.estraiCarta().getIdImmagine();//c3server = mazzo.estraiCarta().getId();
                FirebaseDatabase.getInstance().getReference("Partita/" + idPartita +"/carteServer").setValue(c1server + " " +c2server + " " + c3server);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    private void setCarteCentrali(){
        for(int i=0;i<4;i++){
            Carta carta = mazzo.estraiCarta();
            FirebaseDatabase.getInstance().getReference("Partita/" + idPartita + "/carteCentrali").child(String.valueOf(System.currentTimeMillis())).setValue(carta.getId());
            //FirebaseDatabase.getInstance().getReference("Partita/" + idPartita + "/carteCentrali").setValue(carta.getId() + " ");//tentativo perchè difficile riprendere il valore
            //SU FIREBASE IN CARTE CENTRALI VENGONO INSERITE PIU' DI 4 CARTE PER COLPA DI QUESTO METODO
            if(i%2==0){
                carteSopra.add(carta);
                adapterSopra.notifyItemInserted(carteSopra.size()-1);
            }else{
                carteSotto.add(carta);
                adapterSotto.notifyItemInserted(carteSotto.size()-1);
            }
        }
        adapterSopra.notifyDataSetChanged();
        adapterSotto.notifyDataSetChanged();
    }
}