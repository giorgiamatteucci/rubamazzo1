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

public class ActivityGiocoClient extends AppCompatActivity {

    TextView tvTurno;
    Button btnDaiCarte;
    ImageView ivC1Server, ivC2Server, ivC3Server, ivC1Client, ivC2Client, ivC3Client, ivMazzoServer, ivMazzoClient;
    RecyclerView rvSopra, rvSotto;
    RecyclerView.LayoutManager layoutManager;
    CartaAdapter adapterSopra, adapterSotto;
    ArrayList<Carta> carteSotto, carteSopra;
    DatabaseReference dbRefPartita;
    Mazzo mazzo = Mazzo.getIstance();
    String idPartita;
    boolean mioTurno;
    String[] carteClient,carteCentrali;
    int nCarteMazzoClient, nCarteMazzoServer;

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

        ImageView.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCartaCliccata = (String) hashmap.get(v.getId());
                if (mioTurno && !idCartaCliccata.equals(""+R.drawable.seleziona_carta)) {
                    if(carteClient[0].equals("VUOTO")&&carteClient[1].equals("VUOTO")&&carteClient[2].equals("VUOTO")){
                        Toast.makeText(ActivityGiocoClient.this, "non hai carte", Toast.LENGTH_SHORT).show();
                    } else {
                        Carta carta = mazzo.getCartaById(idCartaCliccata);

                        boolean corrispondenza = false;
                        String idCartaMazzoServer = (String) hashmap.get(ivMazzoServer.getId());
                        //Il client ruba il mazzo al server
                        if (!idCartaMazzoServer.equals("") && mazzo.getCartaById(idCartaMazzoServer).getValore() == carta.getValore()) {
                            corrispondenza = true;
                            dbRefPartita.child("carteClient").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                            dbRefPartita.child("nCarteMazzoC").setValue(nCarteMazzoServer + nCarteMazzoClient + 1);
                            nCarteMazzoServer = 0;
                            dbRefPartita.child("nCarteMazzoS").setValue(nCarteMazzoServer);
                            dbRefPartita.child("cartaMazzoC").setValue(idCartaMazzoServer);
                            dbRefPartita.child("cartaMazzoS").setValue("");
                            dbRefPartita.child("turno").setValue("server");
                        }
                        //La carta selezionata dal client ?? nella rvSopra
                        if (!corrispondenza) {
                            for (Carta c : carteSopra) {
                                if (carta.getValore() == c.getValore()) {
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
                        //La carta selezionata dal client ?? nella rvSotto
                        if (!corrispondenza) {
                            for (Carta c : carteSotto) {
                                if (carta.getValore() == c.getValore()) {
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
                        //La carta selezionata dal client non ?? nel mazzo dell'avversario e neanche nella RecicleView, quindi viene messa al centro
                        if (!corrispondenza) {
                            corrispondenza = true;
                            dbRefPartita.child("carteClient").setValue(Utils.removeCartaGiocatore(carteClient, carta.getId()));
                            dbRefPartita.child("carteCentrali").setValue(Utils.addCarteCentrali(carteCentrali, carta.getId()));
                            dbRefPartita.child("turno").setValue("server");
                        }
                    }
                } else if(!mioTurno){
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


    }

    private void scaricaStatoPartita(){

        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carteClient = String.valueOf(snapshot.child("carteClient").getValue(String.class)).split(" ");
                carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue(String.class)).split(" ");
                String[] carteServer = String.valueOf(snapshot.child("carteServer").getValue(String.class)).split(" ");

                ivC1Client.setImageResource(carteClient[0].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[0]).getIdImmagine());
                ivC2Client.setImageResource(carteClient[1].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[1]).getIdImmagine());
                ivC3Client.setImageResource(carteClient[2].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[2]).getIdImmagine());

                hashmap.put(ivC1Client.getId(),carteClient[0]);
                hashmap.put(ivC2Client.getId(),carteClient[1]);
                hashmap.put(ivC3Client.getId(),carteClient[2]);

                ivC1Server.setImageResource(carteServer[0].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC2Server.setImageResource(carteServer[1].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC3Server.setImageResource(carteServer[2].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);

                carteSotto.clear();
                carteSopra.clear();

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

                mioTurno = snapshot.child("turno").getValue().equals("client");

                adapterSopra.notifyDataSetChanged();
                adapterSotto.notifyDataSetChanged();

                //controllo se la partita ?? finita
                if(snapshot.child("finita").getValue() != null){
                    String risultato;
                    if(Utils.getVincitore(nCarteMazzoClient, nCarteMazzoServer).equals("client")){
                        risultato = "HAI VINTO!";
                    }else{
                        risultato = "HAI PERSO!";
                    }
                    FineDialog d = new FineDialog(ActivityGiocoClient.this,risultato,"Tu avevi " + nCarteMazzoClient + " carte","Il tuo avversario "+ nCarteMazzoServer + " carte");
                    d.setTitle("restart");
                    d.setCancelable(true);
                    d.setContentView(R.layout.dialog);
                    d.show();
                    d.getBtnEsci().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent menu = new Intent(ActivityGiocoClient.this, MenuActivity.class);
                            startActivity(menu);
                            finish();
                        }
                    });
                }

                if(mioTurno) {
                    tvTurno.setVisibility(View.VISIBLE);
                } else {
                    tvTurno.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}