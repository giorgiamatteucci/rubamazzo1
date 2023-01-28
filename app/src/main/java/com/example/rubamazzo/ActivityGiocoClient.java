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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityGiocoClient extends AppCompatActivity {

    TextView tvTurno;
    Button btnDaiCarte;
    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;
    DatabaseReference dbRefPartita, dbRefGiocatore;
    Mazzo mazzo = Mazzo.getIstance();
    String idPartita;
    boolean mioTurno;
    String[] carteClient,carteCentrali;
    int nCarteMazzoClient, nCarteMazzoServer;
    int npartite, nvittorie;

    Map hashmap = new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        tvTurno = findViewById(R.id.tvTurno);
        btnDaiCarte = findViewById(R.id.btnDaiCarte);
        btnDaiCarte.setVisibility(View.GONE);

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
        dbRefGiocatore = FirebaseDatabase.getInstance().getReference("Giocatore").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ImageView.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ONCLICK", "----------------------------------");
                if(mioTurno){
                    if(carteClient[0].equals("VUOTO")&&carteClient[1].equals("VUOTO")&&carteClient[2].equals("VUOTO")){
                        Toast.makeText(ActivityGiocoClient.this, "non hai carte", Toast.LENGTH_SHORT).show();
                    } else {
                        Carta carta = mazzo.getCartaById((String) hashmap.get(v.getId()));

                        boolean corrispondenza = false;
                        String idCartaMazzoServer = (String) hashmap.get(ivMazzoServer.getId());
                        if (!idCartaMazzoServer.equals("") && mazzo.getCartaById(idCartaMazzoServer).getValore() == carta.getValore()) {
                            corrispondenza = true;
                            dbRefPartita.child("carteServer").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                            dbRefPartita.child("nCarteMazzoC").setValue(nCarteMazzoServer + nCarteMazzoClient + 1);
                            nCarteMazzoServer = 0;
                            dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer);
                            dbRefPartita.child("cartaMazzoS").setValue("");
                            dbRefPartita.child("cartaMazzoC").setValue(idCartaMazzoServer);
                            dbRefPartita.child("turno").setValue("server");
                        }

                        if (!corrispondenza) {
                            for (Carta c : carteSopra) {
                                if (carta.getValore() == c.getValore()) {
                                    Log.d("TAG-REFRESH", "la carta selezionata è nel rvSopra");
                                    corrispondenza = true;
                                    dbRefPartita.child("carteCentrali").setValue(Utils.removeCartaDalCentro(carteCentrali, c.getId()));
                                    dbRefPartita.child("carteClient").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                                    dbRefPartita.child("nCarteMazzoC").setValue(nCarteMazzoClient + 2);
                                    dbRefPartita.child("cartaMazzoC").setValue(c.getId());
                                    dbRefPartita.child("turno").setValue("server");
                                    break;
                                }
                            }
                        }

                        if (!corrispondenza) {
                            for (Carta c : carteSotto) {
                                if (carta.getValore() == c.getValore()) {
                                    Log.d("TAG-REFRESH", "la carta selezionata è nel rvSotto");
                                    corrispondenza = true;
                                    dbRefPartita.child("carteCentrali").setValue(Utils.removeCartaDalCentro(carteCentrali, c.getId()));
                                    dbRefPartita.child("carteClient").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                                    dbRefPartita.child("nCarteMazzoC").setValue(nCarteMazzoClient + 2);
                                    dbRefPartita.child("cartaMazzoC").setValue(c.getId());
                                    dbRefPartita.child("turno").setValue("server");
                                    break;
                                }
                            }
                        }

                        if (!corrispondenza) {
                            Log.d("TAG-REFRESH", "la carta selezionata la carta selezionata non è ne sotto ne sopra");
                            corrispondenza = true;
                            dbRefPartita.child("carteClient").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                            dbRefPartita.child("carteCentrali").setValue(Utils.addCarteCentrali(carteCentrali, carta.getId()));
                            dbRefPartita.child("turno").setValue("server");
                        }
                    }
                }else{
                    Toast.makeText(ActivityGiocoClient.this, "aspetta il tuo turno", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ivC1Client.setOnClickListener(onClick);
        ivC2Client.setOnClickListener(onClick);
        ivC3Client.setOnClickListener(onClick);
        ivMazzoClient.setImageResource(R.drawable.seleziona_carta);

        ivC1Server.setImageResource(R.drawable.retro);
        ivC2Server.setImageResource(R.drawable.retro);
        ivC3Server.setImageResource(R.drawable.retro);

        dbRefPartita.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                carteClient = String.valueOf(snapshot.child("carteClient").getValue()).split(" ");
                carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue()).split(" ");

                ivC1Client.setImageResource(mazzo.getCartaById(carteClient[0]).getIdImmagine());
                ivC2Client.setImageResource(mazzo.getCartaById(carteClient[1]).getIdImmagine());
                ivC3Client.setImageResource(mazzo.getCartaById(carteClient[2]).getIdImmagine());

                for(int i=0;i<4;i++){
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {   }
        });

        scaricaStatoPartita();
        //TODO tvTurno non ancora funzionante
        if(mioTurno) {
            tvTurno.setVisibility(View.VISIBLE);
        } else {
            tvTurno.setVisibility(View.INVISIBLE);
        }

    }

    private void getInfoGiocatore(){//TODO non funziona
        dbRefGiocatore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");
                npartite = snapshot.child("npartite").getValue(Integer.class);
                nvittorie = snapshot.child("nvittorie").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void scaricaStatoPartita(){

        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");

                carteClient = String.valueOf(snapshot.child("carteClient").getValue(String.class)).split(" ");
                carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue(String.class)).split(" ");
                String[] carteServer = String.valueOf(snapshot.child("carteServer").getValue(String.class)).split(" ");

                ivC1Client.setImageResource(carteClient[0].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[0]).getIdImmagine());
                ivC2Client.setImageResource(carteClient[1].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[1]).getIdImmagine());
                ivC3Client.setImageResource(carteClient[2].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[2]).getIdImmagine());
                Log.d("TAGPROVA","carteClient[0]: " + carteClient[0]);
                hashmap.put(ivC1Client.getId(),carteClient[0]);
                hashmap.put(ivC2Client.getId(),carteClient[1]);
                hashmap.put(ivC3Client.getId(),carteClient[2]);

                ivC1Server.setImageResource(carteServer[0].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC2Server.setImageResource(carteServer[1].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC3Server.setImageResource(carteServer[2].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);

                Log.d("TAG-REFRESH"," step 1 ok");

                carteSotto.clear();
                carteSopra.clear();

                Log.d("TAG-REFRESH"," step 2 ok");

                for(int i=0;i<carteCentrali.length;i++) {
                    if(carteCentrali[i].equals(""))
                        break;
                    if(i%2==0){
                        carteSopra.add(mazzo.getCartaById(carteCentrali[i]));
                        adapterSopra.notifyItemInserted(carteSopra.size()-1);
                    }else{
                        carteSotto.add(mazzo.getCartaById(carteCentrali[i]));
                        adapterSotto.notifyItemInserted(carteSotto.size()-1);
                    }
                }

                Log.d("TAG-REFRESH"," step 3 ok");

                nCarteMazzoClient = snapshot.child("nCarteMazzoC").getValue(Long.class).intValue();
                nCarteMazzoServer = snapshot.child("nCarteMazzoS").getValue(Long.class).intValue();

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

                hashmap.put(ivMazzoClient.getId(),idCartaMazzoClient);
                hashmap.put(ivMazzoServer.getId(),idCartaMazzoServer);

                Log.d("TAG-REFRESH"," step 4 ok");

                mioTurno = snapshot.child("turno").getValue().equals("client");

                Log.d("TAG-REFRESH"," step 5 ok");

                adapterSopra.notifyDataSetChanged();
                adapterSotto.notifyDataSetChanged();

                //controllo se la partita è finita
                if(snapshot.child("finita").getValue() != null){
                    getInfoGiocatore();
                    if(Utils.getVincitore(nCarteMazzoClient, nCarteMazzoServer).equals("client")){
                        dbRefGiocatore.child("nvittorie").setValue(nvittorie+1);
                        Toast.makeText(ActivityGiocoClient.this, "HAI VINTO!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ActivityGiocoClient.this, "HAI PERSO!", Toast.LENGTH_SHORT).show();
                    }
                    dbRefGiocatore.child("npartite").setValue(npartite+1);
                    Intent i = new Intent(ActivityGiocoClient.this, MenuActivity.class);
                    startActivity(i);
                    finish();
                }
                Log.d("TAG-REFRESH"," step 6 ok");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}