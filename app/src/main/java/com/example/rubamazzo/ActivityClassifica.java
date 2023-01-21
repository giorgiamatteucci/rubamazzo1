package com.example.rubamazzo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityClassifica extends AppCompatActivity {

    Button btnIndietro;
    RecyclerView rvClassifica;
    RecyclerView.LayoutManager layoutManager;
    RigaAdapter rigaAdapter;
    ArrayList<Giocatore> giocatori;
    TextView myUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);

        btnIndietro = findViewById(R.id.btnIndietroClassifica);
        rvClassifica = findViewById(R.id.rvClassifica);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvClassifica.setLayoutManager(layoutManager);
        giocatori = new ArrayList<>();
        rigaAdapter = new RigaAdapter(giocatori);
        rvClassifica.setAdapter(rigaAdapter);
        myUsername = findViewById(R.id.tvMyUser);
        getGiocatori();

        provaPerComunicazione();

        btnIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(ActivityClassifica.this, MenuActivity.class);
                startActivity(menu);
                finish();
            }
        });
        /*
            TODO:  QUERY PER RECUPERARE GLLI UTENTI
                    - se si può parametrizzare la query prendi direttamente i top 5/10
                    - altrimenti prendi tutti gli utenti e poi li filtriamo qui nella classe java
         */

    }

    private void provaPerComunicazione() {

        /*
                S.E.
                in questa prova ho verificato che avendo il codice alfa numerico con il quale firebase identifica
                gli elementi è possibili recuperarli univocamente senza dover iterare si tutti i dati

                quindi

                nella collezione di partite io non ridonderei tutti i dati del giocatore ma solo questa chiave univoca
                (non capisco l'utilità del macrp contenitore connessioni)

                Log ottenuti dall'esecuzione di questo metodo
                2023-01-20 22:13:32.466 17222-17222/com.example.rubamazzo D/TAG01: miracolooooooooo
                2023-01-20 22:13:32.466 17222-17222/com.example.rubamazzo D/TAG01: DataSnapshot { key = 1ZvMssdfWZdqNwOVAGiIpGkXZ272, value = {password=giorgia, npartite=0, nvittorie=0, email=giorgia@gmail.com, username=gioMatt} }
         */

        String idPlayer ="1ZvMssdfWZdqNwOVAGiIpGkXZ272";
        FirebaseDatabase.getInstance().getReference("Giocatore/"+idPlayer).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("TAG01","miracolooooooooo");
                Log.d("TAG01",snapshot.toString());
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void getGiocatori(){
           FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                        //Log.d("TAG01"," 1 - "+ dataSnapshot.getValue().toString());
                        HashMap hasmap = (HashMap) dataSnapshot.getValue();
                        //Log.d("TAG01", " 2 - "+hasmap.toString());
                        Giocatore giocatore = Utils.getGiocatoreFromHashMap(hasmap);
                        //Log.d("TAG01"," 3 - "+ giocatore.toString());
                        giocatori.add(giocatore);
                    }
                    rigaAdapter.notifyDataSetChanged();

                    // This method is called once with the initial value and again whenever data at this location is updated.
                    //String value = snapshot.getValue(String.class);
                    //Log.d(TAG, "Value is: " + value);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.d(TAG, "Failed to read value.", error.toException());
                }
            });
    }
}