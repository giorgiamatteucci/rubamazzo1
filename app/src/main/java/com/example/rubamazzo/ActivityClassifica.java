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

        /*if(FirebaseDatabase.getInstance().getReference("Giocatore").getDatabase() == null){
            Log.d("TAG01","database vuoto");
        }else{
            Log.d("TAG01","database pieno");
            DatabaseReference a = FirebaseDatabase.getInstance().getReference("Giocatore");
            DatabaseReference b = a.child("y9qG8DeNyMbEFkmvKqG7IUyauBp2");

        }*/

/*
        Log.d("TAG01",FirebaseDatabase.getInstance().getReference("Giocatore").toString());
        Task<DataSnapshot> starnafaz =  FirebaseDatabase.getInstance().getReference("Giocatore").get();
        Log.d("TAG01",""+starnafaz.isSuccessful());
        Log.d("TAG01",""+starnafaz.isComplete());

        do{
            Log.d("TAG01","Aspetta dio cane "+starnafaz.isComplete()+" "+starnafaz.isSuccessful());
        }while(starnafaz.isComplete() == false || starnafaz.isSuccessful() == false);

        Log.d("TAG01",FirebaseDatabase.getInstance().getReference("Giocatore").get().getResult().toString());
        DataSnapshot snapshot = FirebaseDatabase.getInstance().getReference("Giocatore").get().getResult();
        Log.d("TAG01",snapshot.toString());
        Log.d("TAG01",snapshot.getChildren().toString());

        for(DataSnapshot dataSnapshot :snapshot.getChildren()){

            Log.d("TAG01"," 1 - "+ dataSnapshot.getValue().toString());
            HashMap hasmap = (HashMap) dataSnapshot.getValue();
            Log.d("TAG01", " 2 - "+hasmap.toString());
            Giocatore giocatore = Utils.getGiocatoreFromHashMap(hasmap);
            Log.d("TAG01"," 3 - "+ giocatore.toString());
            giocatori.add(giocatore);
            Log.d("TAG01"," 4 - "+giocatori.size());
        }*/

        // Read from the database PROVA
           FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //NON FUNZIONA, si spacca! (tentativo di restituire l'elenco dei Giocatori)

                    Log.d("TAG01","onDataChange");



                    for(DataSnapshot dataSnapshot :snapshot.getChildren()){

                        Log.d("TAG01"," 1 - "+ dataSnapshot.getValue().toString());
                        HashMap hasmap = (HashMap) dataSnapshot.getValue();
                        Log.d("TAG01", " 2 - "+hasmap.toString());
                        Giocatore giocatore = Utils.getGiocatoreFromHashMap(hasmap);
                        Log.d("TAG01"," 3 - "+ giocatore.toString());
                        giocatori.add(giocatore);
                        Log.d("TAG01"," 4 - "+giocatori.size());
                    }

                    //datiScaricati = true;
                    //rigaAdapter.notifyAll();


                    //for(Giocatore giocatore : giocatori){
                      //  if(giocatore.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        //    myUsername.setText(giocatore.getUsername());
                          //  return;
                        //}
                   // }
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
    private void prova(){

        for(int i=0;i<10;i++){
            Giocatore giocatore = new Giocatore("enricoluc@gmail.com","",10-i,10);
            giocatori.add(giocatore);
        }

    }
}