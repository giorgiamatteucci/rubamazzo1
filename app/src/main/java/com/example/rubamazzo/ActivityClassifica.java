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
import java.util.ListIterator;

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

        btnIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(ActivityClassifica.this, MenuActivity.class);
                startActivity(menu);
                finish();
            }
        });
    }

    private void getGiocatori(){
        giocatori.clear();
        /*
            TODO  Vengono stampati nell'ordine opposto
         */
       FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                ArrayList<Giocatore> allGiocatori = new ArrayList<>();
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    HashMap hasmap = (HashMap) dataSnapshot.getValue();
                    Giocatore giocatore = Utils.getGiocatoreFromHashMap(hasmap);
                    Log.d("TAGCLASSIFICA", "ActivityClassifica ---- " + giocatore.toStringCustom());
                    allGiocatori.add(giocatore);
                }
                Log.d("TAGCLASSIFICA", "giocatori.size(): "+String.valueOf(allGiocatori.size()));
                giocatori.addAll(Utils.getTopFive(allGiocatori));
                Log.d("TOPFIVE", ""+giocatori.size());
                rigaAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}