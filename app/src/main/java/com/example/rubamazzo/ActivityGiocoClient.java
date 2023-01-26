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
import android.widget.Toast;

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
                if(mioTurno){
                    //TODO logica gioco
                    Log.d("TAG-REFRESH","hashmap.get(v.getId()): " + hashmap.get(v.getId()));
                    Carta carta = mazzo.getCartaById((String) hashmap.get(v.getId()));
                    Log.d("TAG-REFRESH","carta: " + carta);

                    boolean corrispondenza = false;
                    /*if(mazzo.getCartaById(ivMazzoServer.getImageAlpha()).getValore() == carta.getValore()){//TODO NullPointerException: Attempt to invoke virtual method 'int com.example.rubamazzo.Carta.getValore()' on a null object reference
                        ivMazzoServer.setImageResource(R.drawable.seleziona_carta);
                        // TODO aggiornamento numero carte per mazzo
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.seleziona_carta);
                        corrispondenza = true;
                        dbRefPartita.child("carteClient").setValue(getUpdateCarteClient(carta.getId()));
                        dbRefPartita.child("turno").setValue("server");
                    }*/

                    if(!corrispondenza) {
                        for (Carta c : carteSopra) {
                            if (carta.getValore() == c.getValore()) {
                                Log.d("TAG-REFRESH","la carta selezionata è nel rvSopra");
                                dbRefPartita.child("cartaMazzoC").setValue(c.getId());
                                //incrDi2NCarteMazzoC();
                                dbRefPartita.child("carteCentrali").setValue(getUpdateCarteCentrali(c.getId()));
                                carteSopra.remove(c);
                                ImageView imageView = (ImageView) v;
                                imageView.setImageResource(R.drawable.seleziona_carta);
                                corrispondenza = true;
                                adapterSopra.notifyDataSetChanged();
                                dbRefPartita.child("carteClient").setValue(getUpdateCarteClient(carta.getId()));
                                dbRefPartita.child("turno").setValue("server");
                                break;
                            }
                        }
                    }

                    if(!corrispondenza) {
                        for (Carta c : carteSotto) {
                            if (carta.getValore() == c.getValore()) {
                                Log.d("TAG-REFRESH","la carta selezionata è nel rvSotto");
                                dbRefPartita.child("cartaMazzoC").setValue(c.getId());
                                //incrDi2NCarteMazzoC();
                                dbRefPartita.child("carteCentrali").setValue(getUpdateCarteCentrali(c.getId()));//non funziona
                                carteSotto.remove(c);//non funziona
                                ImageView imageView = (ImageView) v;
                                imageView.setImageResource(R.drawable.seleziona_carta);
                                corrispondenza = true;
                                adapterSotto.notifyDataSetChanged();
                                dbRefPartita.child("carteClient").setValue(getUpdateCarteClient(carta.getId()));
                                dbRefPartita.child("turno").setValue("server");
                                break;
                            }
                        }
                    }

                    if(!corrispondenza) {
                        Log.d("TAG-REFRESH","la carta selezionata la carta selezionata non è ne sotto ne sopra");
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.seleziona_carta);
                        corrispondenza = true;
                        adapterSotto.notifyDataSetChanged();
                        dbRefPartita.child("carteClient").setValue(getUpdateCarteClient(carta.getId()));
                        dbRefPartita.child("turno").setValue("server");

                        //TODO da aggiungere sia alle carte centrali sia nel db che graficamente
                        dbRefPartita.child("carteCentrali").setValue(addCarteCentrali(carta.getId()));
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
    }

    private String getUpdateCarteClient(String id){
        String output ="";
        for(int i=0;i<carteClient.length;i++){
            if(!carteClient[i].equals(id)){
                output+= carteClient[i]+" ";
            }else{
                output+= "VUOTO ";
            }
        }
        return output;
    }

    private String getUpdateCarteCentrali(String id){
        String output ="";
        for(int i=0;i<carteCentrali.length;i++){
            if(!carteCentrali[i].equals(id)){
                output+= carteCentrali[i]+" ";
            }else{
                output+= "";
            }
        }
        return output;
    }

    private String addCarteCentrali(String id){
        String output ="";
        for(int i=0;i<carteCentrali.length;i++){
            if(!carteCentrali[i].equals(id)){
                output+= carteCentrali[i]+" ";
            }else{
                output+= id + " ";
            }
        }
        return output;
    }

    private void incrDi2NCarteMazzoC(){ //forse lo deve fare il server
        dbRefPartita.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int val = (int) snapshot.child("nCarteMazzoC").getValue(); //TODO ClassCastException: java.lang.Long cannot be cast to java.lang.Integer
                //dbRefPartita.child("nCarteMazzoC").setValue(val+2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {   }
        });
    }

    private void scaricaStatoPartita(){

        dbRefPartita.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("TAG-REFRESH","sono dentro aggiornaStatoPartita()");
                carteClient = String.valueOf(snapshot.child("carteClient").getValue()).split(" ");
                carteCentrali = String.valueOf(snapshot.child("carteCentrali").getValue()).split(" ");
                String[] carteServer = String.valueOf(snapshot.child("carteServer").getValue()).split(" ");

                ivC1Client.setImageResource(carteClient[0].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[0]).getIdImmagine());
                ivC2Client.setImageResource(carteClient[1].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[1]).getIdImmagine());
                ivC3Client.setImageResource(carteClient[2].equals("VUOTO") ? R.drawable.seleziona_carta : mazzo.getCartaById(carteClient[2]).getIdImmagine());
                Log.d("TAG-REFRESH","hashmap iniziale: " + hashmap);
                hashmap.put(ivC1Client.getId(),carteClient[0]);
                hashmap.put(ivC2Client.getId(),carteClient[1]);
                hashmap.put(ivC3Client.getId(),carteClient[2]);
                Log.d("TAG-REFRESH","hashmap finale: " + hashmap);
                ivC1Server.setImageResource(carteServer[0].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC2Server.setImageResource(carteServer[1].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);
                ivC3Server.setImageResource(carteServer[2].equals("VUOTO") ? R.drawable.seleziona_carta : R.drawable.retro);

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

                Log.d("TAG-REFRESH"," step 4 ok");

                mioTurno = snapshot.child("turno").getValue().equals("client");

                adapterSopra.notifyDataSetChanged();
                adapterSotto.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}