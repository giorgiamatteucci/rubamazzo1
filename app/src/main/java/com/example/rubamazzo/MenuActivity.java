package com.example.rubamazzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    Button btnRegole, btnGioca, btnCrea, btnClassifica, btnLogout;
    TextView tvUser;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rubamazzo-735b7-default-rtdb.firebaseio.com/Giocatore/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUser = findViewById(R.id.tvUser);
        //FirebaseDatabase.getInstance().getReference("Giocatore").addValueEventListener(new ValueEventListener() { //funzionava anche così
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Giocatore giocatore = Utils.getGiocatoreFromHashMap((HashMap) dataSnapshot.getValue());
                    if(giocatore.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        tvUser.setText(giocatore.getUsername());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
        btnRegole = findViewById(R.id.btnRegole);
        btnGioca = findViewById(R.id.btnGioca);
        btnCrea = findViewById(R.id.btnCrea);
        btnClassifica = findViewById(R.id.btnClassifica);
        btnLogout = findViewById(R.id.btnLogout);

        btnRegole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, RegoleActivity.class));
                finish();
            }
        });

        btnGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //andrà ad attendere di essere aggiunto ad una partita già creata

                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di essere aggiunto ad una partita");
                startActivity(i);
                finish();
            }
        });

        btnCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //verrà creata una partita e verrà messo in attesa che un altro giocatore venga aggiunto ad essa
                Intent i = new Intent(MenuActivity.this, AttesaActivity.class);
                i.putExtra("testo","in attesa di uno sfidante");
                startActivity(i);
                finish();
            }
        });

        btnClassifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] carteCentrali = "1".split(" ");
                Log.d("fanculo montori",""+(carteCentrali.length>0));
                Log.d("fanculo montori",""+carteCentrali.length);
                /*Intent i = new Intent(getApplicationContext(), ActivityClassifica.class);
                startActivity(i);
                finish();*/
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}