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
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifica);

        btnIndietro = findViewById(R.id.btnIndietroClassifica);
        rvClassifica = findViewById(R.id.rvClassifica);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvClassifica.setLayoutManager(layoutManager);
        giocatori = new ArrayList<>();
        //-------------------------
        getGiocatori();//prova();
        //-------------------------
        Log.d("TAG01"," se io sono prima di 'onDataChange' il metodo è asincrono");
        rigaAdapter = new RigaAdapter(giocatori);
        rvClassifica.setAdapter(rigaAdapter);
        myUsername = findViewById(R.id.tvMyUser);

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

    private void getGiocatori(){
           FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.d("TAG01","onDataChange");
                    for(DataSnapshot dataSnapshot :snapshot.getChildren()){

                        Log.d("TAG01"," 1 - "+ dataSnapshot.getValue().toString());
                        HashMap hasmap = (HashMap) dataSnapshot.getValue();
                        Log.d("TAG01", " 2 - "+hasmap.toString());
                        Giocatore giocatore = Utils.getGiocatoreFromHashMap(hasmap);
                        Log.d("TAG01"," 3 - "+ giocatore.toString());
                        giocatori.add(giocatore);

                        i++;
                    }
                    Log.d("TAG01"," 4 - "+giocatori.size());
                    //rigaAdapter.notifyDataSetChanged();//FATAL EXCEPTION java.lang.StringIndexOutOfBoundsException: length=3; index=4
                    //rvClassifica.scrollToPosition(giocatori.size()-i); //FATAL EXCEPTION java.lang.StringIndexOutOfBoundsException: length=3; index=4

                    //datiScaricati = true;
                    //rigaAdapter.notifyAll();

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
    /*private void prova(){
        for(int i=0;i<10;i++){
            Giocatore giocatore = new Giocatore("enricoluc@gmail.com","",10-i,10);
            giocatori.add(giocatore);
        }
    }*/
}