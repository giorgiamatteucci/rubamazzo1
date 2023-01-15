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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityClassifica extends AppCompatActivity {

    Button btnIndietro;
    RecyclerView rvClassifica;
    RecyclerView.LayoutManager layoutManager;
    RigaAdapter rigaAdapter;
    ArrayList<Giocatore> giocatori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);

        btnIndietro = findViewById(R.id.btnIndietroClassifica);
        rvClassifica = findViewById(R.id.rvClassifica);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvClassifica.setLayoutManager(layoutManager);
        giocatori = new ArrayList<>();
        prova();
        rigaAdapter = new RigaAdapter(giocatori);
        rvClassifica.setAdapter(rigaAdapter);

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

    void prova(){

        for(int i=0;i<10;i++){
            Giocatore giocatore = new Giocatore("enricoluc@gmail.com","",10-i,10);
            giocatori.add(giocatore);

            // Read from the database PROVA
            /*FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });*/
        }

    }
}