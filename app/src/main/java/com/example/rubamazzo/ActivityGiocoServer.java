package com.example.rubamazzo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ActivityGiocoServer extends AppCompatActivity {

    TextView tvTurno;
    Button btnDaiCarte;
    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;

    String idPartita, idClient, idServer;
    DatabaseReference dbRefPartita, dbRefGiocServer, dbRefGiocClient;
    Mazzo mazzo;
    int nCarteMazzoClient, nCarteMazzoServer;
    boolean mioTurno;
    int nMosse;
    String[] carteClient, carteServer,carteCentrali;
    Giocatore giocatoreClient, giocatoreServer;
    int npartiteServer, nvittorieServer, npartiteClient, nvittorieClient;

    Map hashmap = new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        tvTurno = findViewById(R.id.tvTurno);
        btnDaiCarte = findViewById(R.id.btnDaiCarte);

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
        dbRefPartita = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Partita/"+idPartita);
        dbRefGiocServer = FirebaseDatabase.getInstance().getReference("Giocatore").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbRefGiocClient = FirebaseDatabase.getInstance().getReference("Giocatore").child(getIntent().getStringExtra("idClient"));


        mazzo = Mazzo.getIstance();
        ivC1Client.setImageResource(R.drawable.retro);
        ivC2Client.setImageResource(R.drawable.retro);
        ivC3Client.setImageResource(R.drawable.retro);

        nCarteMazzoClient=0;
        nCarteMazzoServer=0;

        nMosse=3;

        dbRefGiocServer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giocatoreServer = Utils.getGiocatoreFromHashMap((HashMap) snapshot.getValue());
                npartiteServer = snapshot.child("npartite").getValue(Integer.class);
                nvittorieServer = snapshot.child("nvittorie").getValue(Integer.class);
                giocatoreServer.setNPartite(npartiteServer);
                giocatoreServer.setNVittorie(nvittorieServer);
                Log.d("TAGFINE","ActivityGiocoServer ---- npartite: "+ npartiteServer+", nvittorie: "+ nvittorieServer);
                Log.d("TAGFINE","ActivityGiocoServer ---- giocatore: "+ giocatoreServer.toStringCustom());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        dbRefGiocClient.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giocatoreClient = Utils.getGiocatoreFromHashMap((HashMap) snapshot.getValue());
                npartiteClient = snapshot.child("npartite").getValue(Integer.class);
                nvittorieClient = snapshot.child("nvittorie").getValue(Integer.class);
                giocatoreClient.setNPartite(npartiteClient);
                giocatoreClient.setNVittorie(nvittorieClient);
                Log.d("TAGFINE","ActivityGiocoServer ---- npartite: "+ npartiteClient+", nvittorie: "+ nvittorieClient);
                Log.d("TAGFINE","ActivityGiocoServer ---- giocatore: "+ giocatoreClient.toStringCustom());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        aggiornaStatoPartita();


        ivMazzoServer.setImageResource(R.drawable.seleziona_carta);

        btnDaiCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inserire la parte di dare le carte e fine della partita
                if(nMosse>0) {
                    Toast.makeText(ActivityGiocoServer.this, "i giocatori hanno ancora le carte", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ONCLICK", " sono nell'else. mazzo.isEmpty(): "+mazzo.isEmpty());
                    //Se il mazzo.isEmpty() la partita è finita Altrimenti assegna tre carte sia al server che al client
                    if(mazzo.isEmpty()){
                        /*//TODO tentativo di aasegnare al server le carte che sono rimaste al centro --------------------------
                        int j=0;
                        for (Carta c : carteSopra) {
                            j++;
                        }
                        dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer+j);
                        j=0;
                        for (Carta c : carteSotto) {
                            j++;
                        }
                        dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer+j);
                        //---------------------------------------------------------------------------------------------*/

                        //controllare se chi ha vinto nel db e stampare un toast per poi tornare alla MenuActivity
                        if(Utils.getVincitore(nCarteMazzoClient, nCarteMazzoServer).equals("server")){
                            dbRefGiocServer.child("nvittorie").setValue(nvittorieServer+1);
                            Toast.makeText(ActivityGiocoServer.this, "HAI VINTO!", Toast.LENGTH_SHORT).show();
                        }else{
                            dbRefGiocClient.child("nvittorie").setValue(nvittorieClient+1);
                            Toast.makeText(ActivityGiocoServer.this, "HAI PERSO!", Toast.LENGTH_SHORT).show();
                        }
                        dbRefGiocServer.child("npartite").setValue(npartiteServer+1);
                        dbRefGiocClient.child("npartite").setValue(npartiteClient+1);
                        dbRefPartita.child("finita").setValue("true");

                        Intent i = new Intent(ActivityGiocoServer.this, MenuActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        estraiDalMazzo3CarteGiocatori();
                        nMosse=3;
                    }
                }
            }
        });

        ImageView.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ONCLICK", "----------------------------------");

                String idCartaCliccata = (String) hashmap.get(v.getId());

                if (mioTurno && !idCartaCliccata.equals(""+R.drawable.seleziona_carta)) {
                    Carta carta = mazzo.getCartaById(idCartaCliccata);

                    boolean corrispondenza = false;

                    String idCartaMazzoClient = (String) hashmap.get(ivMazzoClient.getId());
                    //Il server ruba il mazzo al client
                    if (!idCartaMazzoClient.equals("") && mazzo.getCartaById(idCartaMazzoClient).getValore() == carta.getValore()) {
                        corrispondenza = true;
                        dbRefPartita.child("carteServer").setValue(Utils.removeCartaGiocatore(carteServer,carta.getId()));
                        dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer+nCarteMazzoClient+1);
                        nCarteMazzoClient=0;
                        dbRefPartita.child("nCarteMazzoC").setValue(nCarteMazzoClient);
                        dbRefPartita.child("cartaMazzoS").setValue(idCartaMazzoClient);
                        dbRefPartita.child("cartaMazzoC").setValue("");
                        dbRefPartita.child("turno").setValue("client");
                        nMosse--;
                    }
                    //La carta selezionata dal server è nella rvSopra
                    if (!corrispondenza) {
                        for (Carta c : carteSopra) {
                            if (carta.getValore() == c.getValore()) {
                                Log.d("TAG-REFRESH", "la carta selezionata è nel rvSopra");
                                corrispondenza = true;
                                dbRefPartita.child("carteCentrali").setValue(Utils.removeCartaDalCentro(carteCentrali,c.getId()));
                                dbRefPartita.child("carteServer").setValue(Utils.removeCartaGiocatore(carteServer,carta.getId()));
                                dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer+2);
                                dbRefPartita.child("cartaMazzoS").setValue(c.getId());
                                dbRefPartita.child("turno").setValue("client");
                                nMosse--;
                                break;
                            }
                        }
                    }
                    //La carta selezionata dal server è nella rvSotto
                    if (!corrispondenza) {
                        for (Carta c : carteSotto) {
                            if (carta.getValore() == c.getValore()) {
                                Log.d("TAG-REFRESH", "la carta selezionata è nel rvSotto");
                                corrispondenza = true;
                                dbRefPartita.child("carteCentrali").setValue(Utils.removeCartaDalCentro(carteCentrali,c.getId()));
                                dbRefPartita.child("carteServer").setValue(Utils.removeCartaGiocatore(carteServer,carta.getId()));
                                dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer+2);
                                dbRefPartita.child("cartaMazzoS").setValue(c.getId());
                                dbRefPartita.child("turno").setValue("client");
                                nMosse--;
                                break;
                            }
                        }
                    }
                    //La carta selezionata dal server non è nel mazzo dell'avversario e neanche nella RecicleView, quindi viene messa al centro
                    if (!corrispondenza) {
                        Log.d("TAG-REFRESH", "la carta selezionata la carta selezionata non è ne sotto ne sopra");
                        corrispondenza = true;
                        dbRefPartita.child("carteServer").setValue(Utils.removeCartaGiocatore(carteServer,carta.getId()));
                        dbRefPartita.child("carteCentrali").setValue(Utils.addCarteCentrali(carteCentrali,carta.getId()));
                        dbRefPartita.child("turno").setValue("client");
                        nMosse--;
                    }

                } else if(!mioTurno){
                    Toast.makeText(ActivityGiocoServer.this, "aspetta il tuo turno", Toast.LENGTH_SHORT).show();
                }

                Log.d("ONCLICK", "----------------------------------");
            }
        };

        ivC1Server.setOnClickListener(onClick);
        ivC2Server.setOnClickListener(onClick);
        ivC3Server.setOnClickListener(onClick);
    }

    private void aggiornaStatoPartita(){

        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");
                idClient = snapshot.child("idClient").getValue(String.class);
                idServer = snapshot.child("idServer").getValue(String.class);

                Log.d("TAG-REFRESH"," step 1 ok");
                carteServer = String.valueOf(snapshot.child("carteServer").getValue(String.class)).split(" ");
                Log.d("FIX2"," carteCentrali: -"+String.valueOf(snapshot.child("carteCentrali").getValue(String.class))+"-");
                carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue(String.class)).split(" ");
                carteClient = String.valueOf(snapshot.child("carteClient").getValue(String.class)).split(" ");

                ivC1Client.setImageResource(carteClient[0].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC2Client.setImageResource(carteClient[1].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC3Client.setImageResource(carteClient[2].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);

                ivC1Server.setImageResource(carteServer[0].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[0]).getIdImmagine());
                ivC2Server.setImageResource(carteServer[1].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[1]).getIdImmagine());
                ivC3Server.setImageResource(carteServer[2].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteServer[2]).getIdImmagine());

                hashmap.put(ivC1Server.getId(),carteServer[0]);
                hashmap.put(ivC2Server.getId(),carteServer[1]);
                hashmap.put(ivC3Server.getId(),carteServer[2]);

                carteSotto.clear();
                carteSopra.clear();

                Log.d("TAG-REFRESH"," step 2 ok");

                for(int i=0;i<carteCentrali.length;i++) {
                    Log.d("FIX2","carteCentrali["+i+"]: "+carteCentrali[i]);
                    if(carteCentrali[i].equals(""))
                        break;//TODO potrebbe dare problemi quando veramente le carteCentrali sono vuote (si aggiorna sul db ma non graficamente)
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

                hashmap.put(ivMazzoClient.getId(),idCartaMazzoClient);
                hashmap.put(ivMazzoServer.getId(),idCartaMazzoServer);

                Log.d("TAG-REFRESH"," step 4 ok");

                mioTurno = snapshot.child("turno").getValue().equals("server");

                Log.d("TAG-REFRESH"," step 5 ok");

                if(mioTurno) {
                    tvTurno.setVisibility(View.VISIBLE);
                } else {
                    tvTurno.setVisibility(View.INVISIBLE);
                }

                adapterSopra.notifyDataSetChanged();
                adapterSotto.notifyDataSetChanged();

                Log.d("TAG-REFRESH"," step 6 ok");

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